package ec.com.vipsoft.ce.services.recepcionComprobantesNeutros;

import javax.ejb.Remote;

import ec.com.vipsoft.ce.comprobantesNeutros.ComprobanteRetencionBinding;
@Remote
public interface ReceptorComprobanteRetencionNeutraRemote {

	public abstract String receptarComprobanteRetencion(ComprobanteRetencionBinding retencion);

}