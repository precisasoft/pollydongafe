package ec.com.vipsoft.ce.backend.service;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import ec.com.vipsoft.ce.comprobantesNeutros.FacturaBinding;
import ec.com.vipsoft.ce.services.recepcionComprobantesNeutros.ReceptorFacturaNeutra;

/**
 * Message-Driven Bean implementation class for: LectorFactura
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "FacturaQueue"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "FacturaQueue")
public class LectorFacturaMDB implements MessageListener {

	
	@EJB
	private ReceptorFacturaNeutra receptorFactura;
	static Logger loger=Logger.getLogger(LectorFacturaMDB.class.getCanonicalName());
    /**
     * Default constructor. 
     */
    public LectorFacturaMDB() {
        // TODO Auto-generated constructor stub
    }
	
	
    public void onMessage(Message message) {
      ObjectMessage objectMessage=(ObjectMessage)message;
      try {
		FacturaBinding facturaBinding =  (FacturaBinding) objectMessage.getObject();
		loger.info(receptorFactura.recibirFactura(facturaBinding));
	} catch (JMSException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        
    }

}
