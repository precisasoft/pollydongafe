package ec.com.vipsoft.ce.backend.service;

import java.io.StringWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.soap.SOAPException;

import ec.com.vipsoft.ce.sri.autorizacion.wsclient.Autorizacion;
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.ConsultaAutorizacion;
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.RespuestaAutorizacionComprobante;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.cryptografia.CryptoUtil;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteAutorizado;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico.TipoComprobante;

/**
 * La idea de este comprobante es poder importar a mis registros ordenes que han sido aprobadas.
 * @author chrisvv
 *
 */

@Stateless
public class ImportadorComprobanteElectronico {

	@Inject
	private UtilClaveAcceso utilClaveAcceso;
	@Inject
	private ConsultaAutorizacion consultaAutorizacion;
	@PersistenceContext
	private EntityManager em;
	@Inject
	private CryptoUtil cryptoutil;
	/**
	 * Debe retornar el numero de autorizacion y registrar en tabla comprobanteeelctronico el respectivo comprobante.
	 * @param rucEmpresa
	 * @param claveAcceso
	 * @return
	 */
	public String importarComprobantePorClaveAcceso(String rucEmpresa,String claveAcceso){
		String retorno=null;
		if(utilClaveAcceso.esValida(claveAcceso)){			
			try {
				RespuestaAutorizacionComprobante respuesta = consultaAutorizacion.consultarAutorizacion(claveAcceso);
				if(!respuesta.getAutorizaciones().isEmpty()){
					
					Autorizacion autorizacion=respuesta.getAutorizaciones().get(0);
					if(autorizacion!=null){																		
						if(autorizacion.getEstado().equalsIgnoreCase("autorizado")){
							
							Entidad entidad=null;
							String rucEncriptado=cryptoutil.encrypt(utilClaveAcceso.obtemerRucEmisor(claveAcceso));
							if(rucEmpresa.equalsIgnoreCase(rucEncriptado)){
								Query q=em.createQuery("select e from Entidad e where e.ruc=?1");
								q.setParameter(1, rucEmpresa);
								List<Entidad>listaEntidad=q.getResultList();
								if(!listaEntidad.isEmpty()){
									entidad=listaEntidad.get(0);
								}
							}
							JAXBContext contexto=null;
							Marshaller marshaller=null;		
							contexto=JAXBContext.newInstance(Autorizacion.class);
							marshaller=contexto.createMarshaller();	
							
							ComprobanteElectronico comprobante=new ComprobanteElectronico();
							comprobante.setAutorizado(true);
							comprobante.setAutorizacionConsultadoAlSRI(true);
							if(utilClaveAcceso.esEnPruebas(claveAcceso)){
								comprobante.setEnPruebas(true);
							}else{
								comprobante.setEnPruebas(false);
							}
							comprobante.setEnviado(true);
							comprobante.setFechaEnvio(autorizacion.getFechaAutorizacion());
							comprobante.setEstablecimiento(utilClaveAcceso.obtenerCodigoEstablecimiento(claveAcceso));
							comprobante.setPuntoEMision(utilClaveAcceso.obtenerCodigoPuntoEmision(claveAcceso));
							comprobante.setSecuencia(utilClaveAcceso.obtenerSecuanciaDocumento(claveAcceso));
							comprobante.setNumeroAutorizacion(autorizacion.getNumeroAutorizacion());
							comprobante.setFechaAutorizacion(autorizacion.getFechaAutorizacion());
							comprobante.setClaveAcceso(claveAcceso);
							if(entidad!=null){
								comprobante.setEntidadEmisora(entidad);
							}
							ComprobanteAutorizado ca=new ComprobanteAutorizado();
							StringWriter sw=new StringWriter();
							marshaller.marshal(autorizacion, sw);
							ca.setEnXML(sw.toString().getBytes());
							comprobante.setComprobanteAutorizado(ca);
							String tipo=utilClaveAcceso.obtenerTipoDocumento(claveAcceso);
							if(tipo.equalsIgnoreCase("01")){
								comprobante.setTipo(TipoComprobante.factura );	
							}
							if(tipo.equalsIgnoreCase("04")){
								comprobante.setTipo(TipoComprobante.notaCredito );	
							}
							if(tipo.equalsIgnoreCase("06")){
								comprobante.setTipo(TipoComprobante.guiaRemision );	
							}
							if(tipo.equalsIgnoreCase("05")){
								comprobante.setTipo(TipoComprobante.notaDebito );	
							}
							if(tipo.equalsIgnoreCase("07")){
								comprobante.setTipo(TipoComprobante.retencion );	
							}
							
							//em.persist(ca);
							em.persist(comprobante);
							retorno=autorizacion.getNumeroAutorizacion();
						}
																	
					//	em.refresh(comprobante);
					}
				}
			} catch (SOAPException | JAXBException e) {			
				e.printStackTrace();
			} 
		}else{
			retorno="la clave de acceso no es validad";
		}
		return retorno;
	}
}
