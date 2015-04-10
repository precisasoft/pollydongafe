package ec.com.vipsoft.ce.utils;


public class DefaultUtilClaveAcceso implements UtilClaveAcceso {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4451554984568988445L;

	/* (non-Javadoc)
	 * @see ec.com.vipsoft.ce.utils.UtilClaveAcceso#esEnPruebas(java.lang.String)
	 */
	@Override
	public boolean esEnPruebas(String claveAcceso){
		boolean retorno=false;
		if(claveAcceso.substring(23,24).equalsIgnoreCase("1")){
			retorno=true;
		}
		return retorno;		
	}

	@Override
	public String obtenerCodigoEstablecimiento(String claveAcceso) {
		
		return claveAcceso.substring(24,27);
	}

	@Override
	public String obtenerCodigoPuntoEmision(String claveAcceso) {
		
		return claveAcceso.substring(27,30);
	}

	@Override
	public String obtemerRucEmisor(String claveAcceso) {
		// TODO Auto-generated method stub
		return claveAcceso.substring(10,23);
	}

	@Override
	public boolean esEnContingencia(String claveAcceso) {
		if(claveAcceso.substring(47,48).equalsIgnoreCase("1")){
			return false;
		}else{
			return true;	
		}
		
	}

	@Override
	public String obtenerSecuanciaDocumento(String claveACceso) {
		
		return claveACceso.substring(30,39);
	}

	@Override
	public String obtenerAmbiente(String claveAcceso) {		
		return claveAcceso.substring(23, 24);
	}

	@Override
	public String obtenerTipoDocumento(String claveAcceso) {
		return claveAcceso.substring(8,10);
	}
}
