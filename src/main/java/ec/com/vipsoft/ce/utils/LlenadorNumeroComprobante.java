package ec.com.vipsoft.ce.utils;

import java.io.Serializable;

public interface LlenadorNumeroComprobante extends Serializable{

	public abstract String llenarNumeroDocumento(String numero);

}