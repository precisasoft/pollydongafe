package ec.com.vipsoft.ce.backend.service;

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
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.ConsultaAutorizacion;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
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
	private VerificadorRespuestaIndividual verificadorRespuestaIndividual;

	
	@Schedule(dayOfMonth="*",hour="*",minute="*",second="0,20,40",year="*",month="*")
	public void verificarAutorizacionesPendientes(){
		if(!verificadorIndisponibilidad.estamosEnContingencia()){
			JAXBContext contexto=null;
			Marshaller marshaller=null;
			Query q=em.createQuery("select c from ComprobanteElectronico c where c.enviado=?1 and c.autorizacionConsultadoAlSRI=?2 and c.fechaEnvio<=?3");		
			q.setParameter(1, Boolean.TRUE);
			q.setParameter(2, Boolean.FALSE);
			Calendar ahora=GregorianCalendar.getInstance();
			ahora.add(Calendar.SECOND, -3);
			q.setParameter(3, ahora.getTime());					

			List<ComprobanteElectronico>lista=q.getResultList();		
			if(!lista.isEmpty()){
				for(ComprobanteElectronico c:lista){
					String autorizacion=verificadorRespuestaIndividual.verificarAutorizacionComprobante(c.getClaveAcceso());			
				}
				
				
			}	
		}

			

	}
}
