package ec.com.vipsoft.ce.services.recepcionComprobantesNeutros;

import javax.ejb.Remote;
import ec.com.vipsoft.ce.comprobantesNeutros.FacturaBinding;


@Remote
public interface ReceptorFacturaNeutraRemote {

	public abstract String recibirFactura(FacturaBinding factura);

}