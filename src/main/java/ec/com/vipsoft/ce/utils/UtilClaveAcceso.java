package ec.com.vipsoft.ce.utils;

import java.io.Serializable;

public interface UtilClaveAcceso extends Serializable{

	public abstract boolean esEnPruebas(String claveAcceso);
	public abstract String obtenerCodigoEstablecimiento(String claveAcceso);
	public abstract String obtenerCodigoPuntoEmision(String claveAcceso);
	public abstract String obtemerRucEmisor(String claveAcceso);
	public abstract boolean esEnContingencia(String claveAcceso);
	public abstract String obtenerSecuanciaDocumento(String claveACceso);
	public abstract String obtenerAmbiente(String claveAcceso);
	public abstract String obtenerTipoDocumento(String claveAcceso);

}