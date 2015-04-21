package ec.com.vipsoft.ce.utils;

import java.io.Serializable;

public interface LlenadorNumeroComprobante extends Serializable{

	public abstract String llenarNumeroDocumento(String numero);
	public abstract String obtenerSucursal(String numero);
	public abstract String obtenerPuntoEmision(String numero);
	public abstract String obtenerSecuencia(String numero);
		
	

}