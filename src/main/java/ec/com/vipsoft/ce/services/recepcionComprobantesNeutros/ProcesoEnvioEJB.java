package ec.com.vipsoft.ce.services.recepcionComprobantesNeutros;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import ec.com.vipsoft.ce.backend.service.AdministradorAutorizacionContingencia;
import ec.com.vipsoft.ce.backend.service.AdministradorRegistrosREDIS;
import ec.com.vipsoft.ce.backend.service.VerificadorIndisponibilidad;
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.Autorizacion;
import ec.com.vipsoft.ce.ui.RegistradorUsuarioBean;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.cryptografia.CryptoUtil;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteAutorizado;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico.TipoComprobante;
import ec.com.vipsoft.erp.abinadi.dominio.DocumentoFirmado;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.sri.factura._v1_1_0.Factura;

@Stateless
public class ProcesoEnvioEJB {
	
	@PersistenceContext
	private EntityManager em;
//	@EJB
//	private FirmadorDocumentoEJB firmador;
	@EJB
	private VerificadorIndisponibilidad verificadorIndisponibilidad;
	@EJB
	private AdministradorAutorizacionContingencia administradorAutorizacionContingencia;
	@EJB
	private EnviadorSRIEJB enviador;
	@Inject
	private UtilClaveAcceso utilClaveAcceso;
	@EJB
	private RegistradorUsuarioBean registradorUsuario;
	@Inject
	private CryptoUtil cryptoUtil;
	@EJB
	private AdministradorRegistrosREDIS registradorDocumentoSinFirmar;
	public void lanzarProcesoEnvio(Map<String,Object> parametros){
		
		String rucEntidad=(String) parametros.get("rucEmisor");
		String claveAcceso=(String) parametros.get("claveAcceso");
		String documentoFirmado=(String) parametros.get("documentoFirmado");
		Query qentidad=em.createQuery("select e from Entidad e where e.ruc=?1");
		qentidad.setParameter(1, cryptoUtil.encrypt(rucEntidad));
		List<Entidad>listadoEntidad=qentidad.getResultList();
		
		if(!listadoEntidad.isEmpty()){
			Entidad entidad=listadoEntidad.get(0);
			//verificar si se ha manadado o autorizado
			boolean enviarlo=true;
			Query qautorizado=em.createQuery("select c from ComprobanteElectronico c where c.entidadEmisora=?1 and c.claveAcceso=?2");
			qautorizado.setParameter(1, entidad);
			qautorizado.setParameter(2, claveAcceso);
			List<ComprobanteElectronico>listadoComprobantes=qautorizado.getResultList();
			if(!listadoComprobantes.isEmpty()){
				ComprobanteElectronico comprobanteEncontrado=em.find(ComprobanteElectronico.class,listadoComprobantes.get(0));
				String numeroAutorizacion=comprobanteEncontrado.getNumeroAutorizacion();
				if(numeroAutorizacion!=null){
					enviarlo=false;
				}else{
					GregorianCalendar ahora=new GregorianCalendar();
					ahora.add(Calendar.MINUTE, -30);
					Date fechaReceptada=comprobanteEncontrado.getFechaEnvio();
					if(fechaReceptada.after(ahora.getTime())){
						enviarlo=false;
					}
				}
			}
			if(enviarlo){
				Query qnsecuencia=em.createQuery("select c from ComprobanteElectronico c where c.entidadEmisora=?1 and c.establecimiento=?2 and c.puntoEMision=?3 and c.secuencia=?4 and c.tipo=?5 and c.autorizado=?6 and c.enPruebas=?7" );
				qnsecuencia.setParameter(1, entidad);
				qnsecuencia.setParameter(2,utilClaveAcceso.obtenerCodigoEstablecimiento(claveAcceso) );
				qnsecuencia.setParameter(3, utilClaveAcceso.obtenerCodigoPuntoEmision(claveAcceso));
				qnsecuencia.setParameter(4, utilClaveAcceso.obtenerSecuanciaDocumento(claveAcceso));
				String tipo=utilClaveAcceso.obtenerTipoDocumento(claveAcceso);
				if(tipo.equalsIgnoreCase("01")){
					qnsecuencia.setParameter(5,TipoComprobante.factura );	
				}
				if(tipo.equalsIgnoreCase("04")){
					qnsecuencia.setParameter(5,TipoComprobante.notaCredito );	
				}
				if(tipo.equalsIgnoreCase("06")){
					qnsecuencia.setParameter(5,TipoComprobante.guiaRemision );	
				}
				if(tipo.equalsIgnoreCase("05")){
					qnsecuencia.setParameter(5,TipoComprobante.notaDebito );	
				}
				if(tipo.equalsIgnoreCase("07")){
					qnsecuencia.setParameter(5,TipoComprobante.retencion );	
				}
				
				qnsecuencia.setParameter(6, Boolean.TRUE);
				qnsecuencia.setParameter(7, Boolean.FALSE);
				List<ComprobanteElectronico>qporSecuencia=qnsecuencia.getResultList();
				if(!qporSecuencia.isEmpty()){
					ComprobanteElectronico comprobanteEncontrado=em.find(ComprobanteElectronico.class, qporSecuencia.get(0).getId());
					String numeroAutoriString=comprobanteEncontrado.getNumeroAutorizacion();
					if(numeroAutoriString!=null){
						enviarlo=false;
					}else{
						GregorianCalendar ahora=new GregorianCalendar();
						ahora.add(Calendar.MINUTE, -30);
						Date fechaReceptada=comprobanteEncontrado.getFechaEnvio();
						if((fechaReceptada.after(ahora.getTime()))&&(comprobanteEncontrado.getCodigoError()==null)){
							enviarlo=false;
						}
					}
				}	
			}
			
			if(enviarlo){
				try {
					ComprobanteElectronico comprobante = new ComprobanteElectronico();
					comprobante.setEntidadEmisora(entidad);
					comprobante.setAutorizado(false);
					comprobante.setEnPruebas(utilClaveAcceso.esEnPruebas(claveAcceso));
					comprobante.setClaveAcceso((String)parametros.get("claveAcceso"));
					comprobante.setPuntoEMision((String)parametros.get("codigoPuntoVenta"));		
					comprobante.setEstablecimiento((String)parametros.get("establecimiento"));
					comprobante.setSecuencia((String) parametros.get("secuenciaDocumento"));
					comprobante.setIdentificacionBeneficiario((String) parametros.get("idCliente"));
					comprobante.setTipo((TipoComprobante) parametros.get("tipoComprobante"));
					BigDecimal monto=(BigDecimal)parametros.get("monto");
					if(monto!=null){
						comprobante.setMonto(monto);	
					}else{
						comprobante.setMonto(BigDecimal.ZERO);
					}
					
					
					
					if(parametros.get("correo")!=null){
						comprobante.setCorreoElectronico((String)parametros.get("correo"));	
					}
					
					DocumentoFirmado documentoFi = new DocumentoFirmado();			
					documentoFi.setConvertidoEnXML(documentoFirmado);
					documentoFi.setEntidad(entidad);
					comprobante.setDocumentoFirmado(documentoFi);

					
					if(verificadorIndisponibilidad.estamosEnContingencia()){					
						Autorizacion autorizacion=new Autorizacion();
						autorizacion.setAmbiente("2");
						autorizacion.setComprobante(documentoFirmado);					
						autorizacion.setEstado("AUTORIZADO");
						Date ahora=new Date();
						autorizacion.setFechaAutorizacion(ahora);
						comprobante.setAutorizado(true);
						comprobante.setFechaAutorizacion(ahora);
						String numeroAutoriacionContingencia=administradorAutorizacionContingencia.siguienteClaveContingenciaRucEmisor(utilClaveAcceso.obtemerRucEmisor(claveAcceso));
						comprobante.setNumeroAutorizacion(numeroAutoriacionContingencia);
						autorizacion.setNumeroAutorizacion(numeroAutoriacionContingencia);
						ComprobanteAutorizado comautorizado=new ComprobanteAutorizado();
						JAXBContext contexto=null;
						Marshaller marshaller=null;
						contexto=JAXBContext.newInstance(Autorizacion.class);
						marshaller=contexto.createMarshaller();
						StringWriter swriter=new StringWriter();
						marshaller.marshal(autorizacion, swriter);		
						comautorizado.setEnXML(swriter.toString().getBytes());								
						comprobante.setComprobanteAutorizado(comautorizado);									
					}	
					registradorUsuario.registrarUsuario((String)parametros.get("idCliente"), (String)parametros.get("idCliente"), "", "");
					em.persist(comprobante);

					//añado registro de documento sin firmar para poder usarlo en y modificar el tipo de emisión y la clave de acceso en caso de ser necesario usar contingencia.
					Object documentoSinFirmar=parametros.get("documentoOriginal");
					if(documentoSinFirmar instanceof Factura){
						Factura facturaOriginal=(Factura) documentoSinFirmar;
						registradorDocumentoSinFirmar.registrarFactura(facturaOriginal);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		

			
		}
				
		
	}
//	public void lanzarProcesoEnvio(Map<String,Object> parametros){
//		
//		String rucEntidad=(String) parametros.get("rucEmisor");
//		String claveAcceso=(String) parametros.get("claveAcceso");
//		String documentoFirmado=(String) parametros.get("documentoFirmado");
//		Query qentidad=em.createQuery("select e from Entidad e where e.ruc=?1");
//		qentidad.setParameter(1, rucEntidad);
//		List<Entidad>listadoEntidad=qentidad.getResultList();
//		
//		if(!listadoEntidad.isEmpty()){
//			Entidad entidad=listadoEntidad.get(0);
//			try {
//				ComprobanteElectronico comprobante = new ComprobanteElectronico();
//				comprobante.setEntidadEmisora(entidad);
//				comprobante.setAutorizado(false);
//				comprobante.setEnPruebas(utilClaveAcceso.esEnPruebas(claveAcceso));
//				comprobante.setClaveAcceso((String)parametros.get("claveAcceso"));
//				comprobante.setPuntoEMision((String)parametros.get("codigoPuntoVenta"));		
//				comprobante.setEstablecimiento((String)parametros.get("establecimiento"));
//				comprobante.setSecuencia((String) parametros.get("secuenciaDocumento"));
//				comprobante.setIdentificacionBeneficiario((String) parametros.get("idCliente"));
//				comprobante.setTipo((TipoComprobante) parametros.get("tipoComprobante"));
//				DocumentoFirmado documentoFi = new DocumentoFirmado();			
//				documentoFi.setConvertidoEnXML(documentoFirmado);						
//				comprobante.setDocumentoFirmado(documentoFi);
//				if(verificadorIndisponibilidad.estamosEnContingencia()){					
//					Autorizacion autorizacion=new Autorizacion();
//					autorizacion.setAmbiente("2");
//					autorizacion.setComprobante(documentoFirmado);					
//					autorizacion.setEstado("AUTORIZADO");
//					Date ahora=new Date();
//					autorizacion.setFechaAutorizacion(ahora);
//					comprobante.setAutorizado(true);
//					comprobante.setFechaAutorizacion(ahora);
//					String numeroAutoriacionContingencia=administradorAutorizacionContingencia.siguienteClaveContingencia(utilClaveAcceso.obtemerRucEmisor(claveAcceso));
//					comprobante.setNumeroAutorizacion(numeroAutoriacionContingencia);
//					autorizacion.setNumeroAutorizacion(numeroAutoriacionContingencia);
//					ComprobanteAutorizado comautorizado=new ComprobanteAutorizado();
//					JAXBContext contexto=null;
//					Marshaller marshaller=null;
//					contexto=JAXBContext.newInstance(Autorizacion.class);
//					marshaller=contexto.createMarshaller();
//					StringWriter swriter=new StringWriter();
//					marshaller.marshal(autorizacion, swriter);		
//					comautorizado.setEnXML(swriter.toString().getBytes());								
//					comprobante.setComprobanteAutorizado(comautorizado);									
//				}	
//				registradorUsuario.registrarUsuario((String)parametros.get("idCliente"), (String)parametros.get("idCliente"), "", "");
//				em.persist(comprobante);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			
//		}
//				
//		
//	}

	
	
//public void lanzarProcesoEnvio(Map<String,Object> parametros){
//		
//		String rucEntidad=(String) parametros.get("rucEmisor");
//		byte[] bytes=(byte[]) parametros.get("archivop12");
//		String contrasena=(String) parametros.get("contrasena");
//		String claveAcceso=(String) parametros.get("claveAcceso");
//		String documentoFirmado=(String) parametros.get("documentoFirmado");
//		Query qentidad=em.createQuery("select e from Entidad e where e.ruc=?1");
//		qentidad.setParameter(1, rucEntidad);
//		List<Entidad>listadoEntidad=qentidad.getResultList();
//		
//		if(!listadoEntidad.isEmpty()){
//			Entidad entidad=listadoEntidad.get(0);
//			try {
//				ComprobanteElectronico comprobante = new ComprobanteElectronico();
//				comprobante.setEntidadEmisora(entidad);
//				if(!verificadorIndisponibilidad.estamosEnContingencia()){
//					RespuestaRecepcionDocumento respuestaRecepcion = enviador.enviarComprobanteAlSRI(documentoFirmado, utilClaveAcceso.esEnPruebas(claveAcceso));
//					if(respuestaRecepcion.getEstado().equalsIgnoreCase("devuelta")){
//						comprobante.setCodigoError(respuestaRecepcion.getDetalle().get(0).getCodigo());
//						comprobante.setMensajeError(respuestaRecepcion.getDetalle().get(0).getMensaje());
//					}else{
//						comprobante.setFechaEnvio(new Date());			
//						comprobante.setEnviado(true);
//					}
//					comprobante.setAutorizado(false);
//				}else{
//					comprobante.setEnviado(false); //este caso es para reenviar cuando la contingencia se ha acabado.
//					Autorizacion autorizacion=new Autorizacion();
//					autorizacion.setAmbiente("2");
//					autorizacion.setComprobante(documentoFirmado);
//					
//					autorizacion.setEstado("AUTORIZADO");
//					Date ahora=new Date();
//					autorizacion.setFechaAutorizacion(ahora);
//					comprobante.setAutorizado(true);
//					comprobante.setFechaAutorizacion(ahora);
//					String numeroAutoriacionContingencia=administradorAutorizacionContingencia.siguienteClaveContingencia(utilClaveAcceso.obtemerRucEmisor(claveAcceso));
//					comprobante.setNumeroAutorizacion(numeroAutoriacionContingencia);
//					autorizacion.setNumeroAutorizacion(numeroAutoriacionContingencia);
//					ComprobanteAutorizado comautorizado=new ComprobanteAutorizado();
//					JAXBContext contexto=null;
//					Marshaller marshaller=null;
//					contexto=JAXBContext.newInstance(Autorizacion.class);
//					marshaller=contexto.createMarshaller();
//					StringWriter swriter=new StringWriter();
//					marshaller.marshal(autorizacion, swriter);		
//					comautorizado.setEnXML(swriter.toString().getBytes());								
//					comprobante.setComprobanteAutorizado(comautorizado);
//					
//				}
//				comprobante.setEnPruebas(utilClaveAcceso.esEnPruebas(claveAcceso));
//				comprobante.setClaveAcceso((String)parametros.get("claveAcceso"));
//				comprobante.setPuntoEMision((String)parametros.get("codigoPuntoVenta"));		
//				comprobante.setEstablecimiento((String)parametros.get("establecimiento"));
//				comprobante.setSecuencia((String) parametros.get("secuenciaDocumento"));
//				comprobante.setIdentificacionBeneficiario((String) parametros.get("idCliente"));
//				comprobante.setTipo((TipoComprobante) parametros.get("tipoComprobante"));					
//										
//				DocumentoFirmado documentoFi = new DocumentoFirmado();			
//				documentoFi.setConvertidoEnXML(documentoFirmado);						
//				comprobante.setDocumentoFirmado(documentoFi);	
//				em.persist(comprobante);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			
//		}
//				
//		
//	}

}
