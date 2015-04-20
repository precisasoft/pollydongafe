package ec.com.vipsoft.ce.backend.service;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import ec.com.vipsoft.ce.services.recepcionComprobantesNeutros.EnviadorSRIEJB;
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.Autorizacion;
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.ConsultaAutorizacion;
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.RespuestaAutorizacionComprobante;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteAutorizado;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;

@Stateless
public class VerificadorRespuestaSRI {

	public static Integer MAXREINTENTOS=50;
	@PersistenceContext
	private EntityManager em;
	@Inject
	private ConsultaAutorizacion consultorAutorizacion;
	@EJB
	private EnviadorSRIEJB enviadorSRI;
	@Inject
	private UtilClaveAcceso utilClaveAcceso;
	@EJB
	private VerificadorIndisponibilidad verificadorIndisponibilidad;
	@EJB
	private VerificadorRepuestaIndividualRemote verificadorRespuestaIndividual;

	
	@Schedule(dayOfMonth="*",hour="*",minute="*",second="0,20,40",year="*",month="*")	
	public void verificarAutorizacionesPendientes(){
		if(!verificadorIndisponibilidad.estamosEnContingencia()){
			
			Query q=em.createQuery("select c from ComprobanteElectronico c where c.enviado=?1 and c.autorizacionConsultadoAlSRI=?2 and c.fechaEnvio<=?3  and c.fechaEnvio>=?4  order by c.id desc");		
			q.setParameter(1, Boolean.TRUE);
			q.setParameter(2, Boolean.FALSE);
			Calendar ahora=GregorianCalendar.getInstance();
			ahora.add(Calendar.SECOND, -5);			
			q.setParameter(3, ahora.getTime());
			ahora.add(Calendar.HOUR,-24);
			q.setParameter(4, ahora.getTime());
			//q.setParameter(5, Boolean.FALSE);

			List<ComprobanteElectronico>lista=q.getResultList();		
			if(!lista.isEmpty()){
				for(ComprobanteElectronico c:lista){
					String claveAcceso=c.getClaveAcceso();
					Long idComprobante=c.getId();
					try {
						Autorizacion autorizacion=null;
						JAXBContext contexto=null;
						Marshaller marshaller=null;		
						contexto=JAXBContext.newInstance(Autorizacion.class);
						marshaller=contexto.createMarshaller();		
						RespuestaAutorizacionComprobante respuesta = consultorAutorizacion.consultarAutorizacion(claveAcceso);
						if(!respuesta.getAutorizaciones().isEmpty()){
							autorizacion=respuesta.getAutorizaciones().get(0);
							if(autorizacion!=null){
							//retorno=autorizacion.getNumeroAutorizacion();
								ComprobanteElectronico comprobante=em.find(ComprobanteElectronico.class, idComprobante);
								comprobante.setAutorizacionConsultadoAlSRI(true);
								if(autorizacion.getEstado().equalsIgnoreCase("autorizado")){
									comprobante.setAutorizado(true);
									comprobante.setNumeroAutorizacion(autorizacion.getNumeroAutorizacion());
									comprobante.setFechaAutorizacion(autorizacion.getFechaAutorizacion());
									ComprobanteAutorizado ca=new ComprobanteAutorizado();
									StringWriter sw=new StringWriter();
									marshaller.marshal(autorizacion, sw);
									ca.setEnXML(sw.toString().getBytes());
									comprobante.setComprobanteAutorizado(ca);					
								}else{				
									comprobante.setAutorizado(false);
									comprobante.setAutorizacionConsultadoAlSRI(true);
									if(autorizacion.getMensaje().getIdentificador()!=null){										
										comprobante.setCodigoError(autorizacion.getMensaje().getIdentificador());
										comprobante.setMensajeError(autorizacion.getMensaje().getMensaje());
									}		
								}	
							//	em.refresh(comprobante);
							}
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
				}
				
				
			}	
		}

			

	}
	
	
	
}
