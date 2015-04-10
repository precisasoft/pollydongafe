package ec.com.vipsoft.erp.abinadi.procesos;

import java.util.logging.Logger;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class VerificadorContingencia implements JavaDelegate {

	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		Logger.getLogger(VerificadorContingencia.class.getName()).info("verificando si estamos en contingencia");
		arg0.setVariable("enContingencia", "0");
		
	}

}
