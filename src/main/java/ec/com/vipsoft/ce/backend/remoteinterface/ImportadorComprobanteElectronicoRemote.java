package ec.com.vipsoft.ce.backend.remoteinterface;

import javax.ejb.Remote;

@Remote
public interface ImportadorComprobanteElectronicoRemote {

	/**
	 * Debe retornar el numero de autorizacion y registrar en tabla comprobanteeelctronico el respectivo comprobante.
	 * @param rucEmpresa
	 * @param claveAcceso
	 * @return
	 */
	public abstract String importarComprobantePorClaveAcceso(String rucEmpresa,	String claveAcceso);

}