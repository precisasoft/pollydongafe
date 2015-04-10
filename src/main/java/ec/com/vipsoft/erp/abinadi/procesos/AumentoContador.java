package ec.com.vipsoft.erp.abinadi.procesos;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class AumentoContador implements JavaDelegate {

	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		// TODO Auto-generated method stub
		Integer intentos=(Integer)arg0.getVariable("intentos");
		intentos++;
		arg0.removeVariable("intentos");
		arg0.setVariable("intentos", intentos);
		Integer maxIntentos=(Integer)arg0.getVariable("maxIntentos");
		if(intentos>=maxIntentos){
			arg0.setVariable("rendido", true);
		}else{
			arg0.setVariable("rendido", false);
		}
	}

}
