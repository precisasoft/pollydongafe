package ec.com.vipsoft.ce.backend.service;

import java.io.StringWriter;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jws.WebService;
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
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteAutorizado;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;

@Stateless
//@WebService
public class VerificadorRespuestaIndividual {

	@PersistenceContext
	private EntityManager em;
	
	
	@Inject
	private ConsultaAutorizacion consultorAutorizacion;
	public Autorizacion verificarAutorizacion(String claveAcceso) throws JAXBException{
		Autorizacion autorizacion=null;
		JAXBContext contexto=null;
		Marshaller marshaller=null;		
		contexto=JAXBContext.newInstance(Autorizacion.class);
		marshaller=contexto.createMarshaller();		
		try {
			RespuestaAutorizacionComprobante respuesta = consultorAutorizacion.consultarAutorizacion(claveAcceso);
			if(!respuesta.getAutorizaciones().isEmpty()){
				autorizacion=respuesta.getAutorizaciones().get(0);
			}
			
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return autorizacion;
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String verificarAutorizacionComprobante(String claveAcceso,Long idConProbante){
		
		String retorno=null;
		try {
					
			JAXBContext contexto=JAXBContext.newInstance(Autorizacion.class);
			Marshaller marshaller=contexto.createMarshaller();		
			Autorizacion autorizacion=null;
			
			autorizacion = verificarAutorizacion(claveAcceso);
			if(autorizacion!=null){
				retorno=autorizacion.getNumeroAutorizacion();
				ComprobanteElectronico comprobante=em.find(ComprobanteElectronico.class, idConProbante);
				comprobante.setAutorizacionConsultadoAlSRI(true);
				if(autorizacion.getEstado().equalsIgnoreCase("autorizado")){
					comprobante.setAutorizado(true);
					comprobante.setNumeroAutorizacion(retorno);
					comprobante.setFechaAutorizacion(autorizacion.getFechaAutorizacion());
					ComprobanteAutorizado ca=new ComprobanteAutorizado();
					StringWriter sw=new StringWriter();
					marshaller.marshal(autorizacion, sw);
					ca.setEnXML(sw.toString().getBytes());
					comprobante.setComprobanteAutorizado(ca);					
				}else{				
					comprobante.setAutorizado(false);
					if(autorizacion.getMensaje().getIdentificador()!=null){
						comprobante.setAutorizacionConsultadoAlSRI(true);
						comprobante.setCodigoError(autorizacion.getMensaje().getIdentificador());
						comprobante.setMensajeError(autorizacion.getMensaje().getMensaje());
					}		
				}	
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return retorno;
	}
	
//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//	public String verificarAutorizacionComprobante(String claveAcceso){
//		StringBuilder sb=new StringBuilder();
//		JAXBContext contexto=null;
//		Marshaller marshaller=null;
//		Query q=em.createQuery("select c from ComprobanteElectronico c where c.claveAcceso=?1");
//		q.setParameter(1, claveAcceso);
//		List<ComprobanteElectronico>listaComprobantes=q.getResultList();
//		
//		try {
//			contexto=JAXBContext.newInstance(Autorizacion.class);
//			marshaller=contexto.createMarshaller();		
//			for(ComprobanteElectronico c:listaComprobantes){
//				RespuestaAutorizacionComprobante respuesta = consultorAutorizacion.consultarAutorizacion(c.getClaveAcceso());
//				if(!respuesta.getAutorizaciones().isEmpty()){
//					for(Autorizacion a:respuesta.getAutorizaciones()){
//						ComprobanteElectronico elcomprobante=em.find(ComprobanteElectronico.class, c.getId());
//						elcomprobante.setAutorizacionConsultadoAlSRI(true);
//						if(a.getEstado().equalsIgnoreCase("AUTORIZADO")){
//							//aqui construir el pdf ... y xml ... 							
//							elcomprobante.setAutorizado(true);
//							elcomprobante.setNumeroAutorizacion(a.getNumeroAutorizacion());
//							sb.append(a.getNumeroAutorizacion());
//							ComprobanteAutorizado ca=new ComprobanteAutorizado();
//							StringWriter swriter=new StringWriter();
//							marshaller.marshal(respuesta.getAutorizaciones().get(0), swriter);		
//							ca.setEnXML(swriter.toString().getBytes());
//							elcomprobante.setComprobanteAutorizado(ca);
//							elcomprobante.setFechaAutorizacion(a.getFechaAutorizacion());
//							if((elcomprobante.getNumeroAutorizacion()==null)||(elcomprobante.getComprobanteAutorizado()==null)){
//								ComprobanteElectronico actualizador=em.find(ComprobanteElectronico.class, c.getId());
//								actualizador.setAutorizado(true);
//								actualizador.setNumeroAutorizacion(a.getNumeroAutorizacion());
//								actualizador.setComprobanteAutorizado(ca);
//							}
//							em.refresh(elcomprobante);
//							em.refresh(c);
//							c.setAutorizado(true);
//							
//							//notificador.equals(elcomprobante.getIdentificacionBeneficiario().)
//						}else{
//							elcomprobante.setAutorizado(false);
//							elcomprobante.setAutorizacionConsultadoAlSRI(true);
//						}
//					}
//				}else{
//					
//				}
//				
//			}
//		}catch(SOAPException | JAXBException e){
//			e.getMessage();
//			e.printStackTrace();
//		}
//
//		return sb.toString();
//	}
//	public String verificarAutorizacionComprobante(String claveAcceso){
//		StringBuilder sb=new StringBuilder();
//		JAXBContext contexto=null;
//		Marshaller marshaller=null;
//		Query q=em.createQuery("select c from ComprobanteElectronico c where c.claveAcceso=?1");
//		q.setParameter(1, claveAcceso);
//		List<ComprobanteElectronico>listaComprobantes=q.getResultList();
//		
//		try {
//			contexto=JAXBContext.newInstance(Autorizacion.class);
//			marshaller=contexto.createMarshaller();		
//			for(ComprobanteElectronico c:listaComprobantes){
//				RespuestaAutorizacionComprobante respuesta = consultorAutorizacion.consultarAutorizacion(c.getClaveAcceso());
//				if(!respuesta.getAutorizaciones().isEmpty()){
//					for(Autorizacion a:respuesta.getAutorizaciones()){
//						ComprobanteElectronico elcomprobante=em.find(ComprobanteElectronico.class, c.getId());
//						elcomprobante.setAutorizacionConsultadoAlSRI(true);
//						if(a.getEstado().equalsIgnoreCase("AUTORIZADO")){
//							//aqui construir el pdf ... y xml ... 							
//							elcomprobante.setAutorizado(true);
//							elcomprobante.setNumeroAutorizacion(a.getNumeroAutorizacion());
//							sb.append(a.getNumeroAutorizacion());
//							ComprobanteAutorizado ca=new ComprobanteAutorizado();
//							StringWriter swriter=new StringWriter();
//							marshaller.marshal(respuesta.getAutorizaciones().get(0), swriter);		
//							ca.setEnXML(swriter.toString().getBytes());
//							elcomprobante.setComprobanteAutorizado(ca);
//							elcomprobante.setFechaAutorizacion(a.getFechaAutorizacion());
//							c.setAutorizado(true);						
//							//notificador.equals(elcomprobante.getIdentificacionBeneficiario().)
//						}else{
//							elcomprobante.setAutorizado(false);
//							elcomprobante.setAutorizacionConsultadoAlSRI(true);
//						}
//					}
//				}else{
//					
//				}
//				
//			}
//		}catch(SOAPException | JAXBException e){
//			e.getMessage();
//			e.printStackTrace();
//		}
			
			
//				try {
//					
//					
//						
//					}else{
//						// si ha pasado media hora ...volver a enviarlo.
//						Query qcomprobantes = em.createQuery("select c from ComprobanteElectronico c where c.claveAcceso=?1");
//						qcomprobantes.setParameter(1,respuesta.getClaveAccesoConsultada());
//						List<ComprobanteElectronico> listaComprobantes = qcomprobantes.getResultList();
//						if (!listaComprobantes.isEmpty()) {
//							Calendar ahora2 = new GregorianCalendar();
//							ahora2.add(Calendar.MINUTE, -30);
//							ComprobanteElectronico lazaro = em.find(ComprobanteElectronico.class,listaComprobantes.get(0).getId());
//							if ((lazaro.getFechaEnvio().before(ahora2.getTime())&&(lazaro.getReintentos()<=MAXREINTENTOS))) {
//								DocumentoFirmado dfirmado = em.find(DocumentoFirmado.class,	lazaro.getDocumentoFirmado().getId());
//								RespuestaRecepcionDocumento enviarComprobanteAlSRI = enviadorSRI.enviarComprobanteAlSRI(dfirmado.getConvertidoEnXML(),utilClaveAcceso.esEnPruebas(lazaro.getClaveAcceso()));
//								lazaro.setFechaEnvio(new Date());
//								lazaro.setReintentos(lazaro.getReintentos()+1);
//							}
//
//						}
//					}
//
//
//		return sb.toString();
//	}
}
