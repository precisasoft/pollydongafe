package ec.com.vipsoft.ce.services.recepcionComprobantesNeutros;

import javax.ejb.Remote;

import ec.com.vipsoft.ce.comprobantesNeutros.NotaCreditoBinding;
@Remote
public interface ReceptorNotaCreditoNeutraRemote {
	public abstract String procesarNotaCredito(NotaCreditoBinding notaCredito);

}