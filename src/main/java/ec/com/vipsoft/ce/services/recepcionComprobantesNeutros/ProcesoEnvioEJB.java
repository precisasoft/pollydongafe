package ec.com.vipsoft.ce.services.recepcionComprobantesNeutros;

import java.io.StringWriter;
import java.util.Date;
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
import ec.com.vipsoft.ce.backend.service.VerificadorIndisponibilidad;
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.Autorizacion;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteAutorizado;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico.TipoComprobante;
import ec.com.vipsoft.erp.abinadi.dominio.DocumentoFirmado;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;

@Stateless
public class ProcesoEnvioEJB {
	
	@PersistenceContext
	private EntityManager em;
	@EJB
	private FirmadorDocumentoEJB firmador;
	@EJB
	private VerificadorIndisponibilidad verificadorIndisponibilidad;
	@EJB
	private AdministradorAutorizacionContingencia administradorAutorizacionContingencia;
	@EJB
	private EnviadorSRIEJB enviador;
	@Inject
	private UtilClaveAcceso utilClaveAcceso;
	public void lanzarProcesoEnvio(Map<String,Object> parametros){
		
		String rucEntidad=(String) parametros.get("rucEmisor");
		String claveAcceso=(String) parametros.get("claveAcceso");
		String documentoFirmado=(String) parametros.get("documentoFirmado");
		Query qentidad=em.createQuery("select e from Entidad e where e.ruc=?1");
		qentidad.setParameter(1, rucEntidad);
		List<Entidad>listadoEntidad=qentidad.getResultList();
		
		if(!listadoEntidad.isEmpty()){
			Entidad entidad=listadoEntidad.get(0);
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
				DocumentoFirmado documentoFi = new DocumentoFirmado();			
				documentoFi.setConvertidoEnXML(documentoFirmado);						
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
					String numeroAutoriacionContingencia=administradorAutorizacionContingencia.siguienteClaveContingencia(utilClaveAcceso.obtemerRucEmisor(claveAcceso));
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
				em.persist(comprobante);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
				
		
	}

	
	
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
