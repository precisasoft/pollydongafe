package ec.com.vipsoft.erp.abinadi.procesos;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ContadorFueraLinea implements JavaDelegate{

	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		GregorianCalendar gcal=new GregorianCalendar();
		Integer minutesTimeOut=(Integer) arg0.getVariable("minutosTimeout");
		gcal.add(Calendar.MINUTE, -3);
		Date fechaComparacion=gcal.getTime();
		
		Date fechaFirmado=(Date) arg0.getVariable("horaFirma");
		if(fechaFirmado.before(fechaComparacion)){
			arg0.setVariable("usarContingencia", true);
		}else{
			arg0.setVariable("usarContingencia", false);
		}
		
	}

}
