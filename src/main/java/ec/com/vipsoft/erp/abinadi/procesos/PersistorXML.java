package ec.com.vipsoft.erp.abinadi.procesos;



import java.util.logging.Logger;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;
public class PersistorXML implements JavaDelegate {


	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		Logger.getLogger(PersistorXML.class.getName()).info("entrando a metodo execute de PersistorXML");
		//arg0.setVariable("facturaXML", facturaXML);
		//arg0.setVariable("secuenciaDocumento", facturaXML.getInfoTributaria().getSecuencial());
//		JAXBContext contextoFActura110=JAXBContext.newInstance(Factura.class);
//		Marshaller m=contextoFActura110.createMarshaller();
//		StringWriter sw=new StringWriter();
//		Factura factura=(Factura) arg0.getVariable("documentoFirmado");
//		m.marshal(factura, sw);
		ComprobanteElectronico comprobnate=new ComprobanteElectronico();
		

		
		comprobnate.setEnPruebas((boolean)arg0.getVariable("enPruebas"));
		comprobnate.setEnviado(false);
		comprobnate.setAutorizado(false);
		
		String enContigencia=(String) arg0.getVariable("enContingencia");		
		
		
	
		
		
		Logger.getLogger(PersistorXML.class.getName()).info("saliendo a metodo execute de PersistorXML");

	}

}
