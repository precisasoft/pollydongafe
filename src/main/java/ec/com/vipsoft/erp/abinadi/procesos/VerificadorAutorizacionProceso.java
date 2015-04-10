package ec.com.vipsoft.erp.abinadi.procesos;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;


public class VerificadorAutorizacionProceso implements JavaDelegate {

    @PersistenceContext
    private EntityManager em;
	@Override
	public void execute(DelegateExecution arg0) throws Exception {
	Logger.getLogger(VerificadorAutorizacionProceso.class.getCanonicalName()).info("ingresando a verificacionAutorizaiconProceso" );
	
	Calendar calendario=GregorianCalendar.getInstance();
	calendario.add(Calendar.MINUTE, -5);
	Date fechaARevisar=calendario.getTime();
	em.getTransaction().begin();
	Query q=em.createQuery("select c from ComprobanteElectronico c where c.fechaEnvio<=?1");
	q.setParameter(1, fechaARevisar);
	List<ComprobanteElectronico>lista=q.getResultList();
	for(ComprobanteElectronico c:lista){
		
	}
	
	em.getTransaction().commit();
	
	
	
	
	Logger.getLogger(VerificadorAutorizacionProceso.class.getCanonicalName()).info("Saliendo a verificacionAutorizaiconProceso" );
	}

}
