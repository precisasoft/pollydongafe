package ec.com.vipsoft.erp.abinadi.dominio;

import java.io.Serializable;

public class ReporteComprobanteElectronico implements Serializable,Comparable<ReporteComprobanteElectronico>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -972303187702583592L;
	private boolean autorizado;
	private String claveAcceso;
	private String codigoPuntoVenta;
	private String codigoSucursal;
	private boolean enviado;
	private String motivoRechazo;
	private String numeroAutorizacion;
	private String secuencia;
	
	public String getClaveAcceso() {
		return claveAcceso;
	}
	public String getCodigoPuntoVenta() {
		return codigoPuntoVenta;
	}
	public String getCodigoSucursal() {
		return codigoSucursal;
	}
	public String getMotivoRechazo() {
		return motivoRechazo;
	}
	public String getNumeroAutorizacion() {
		return numeroAutorizacion;
	}
	public String getSecuencia() {
		return secuencia;
	}
	public boolean isAutorizado() {
		return autorizado;
	}
	public boolean isEnviado() {
		return enviado;
	}
	public void setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}
	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}
	public void setCodigoPuntoVenta(String codigoPuntoVenta) {
		this.codigoPuntoVenta = codigoPuntoVenta;
	}
	public void setCodigoSucursal(String codigoSucursal) {
		this.codigoSucursal = codigoSucursal;
	}
	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}
	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}
	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}
	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}
	@Override
	public int compareTo(ReporteComprobanteElectronico o) {
		int retorno=0;
		if((claveAcceso!=null)&&(o.claveAcceso==null)){
			retorno="A".compareTo("B");
		}
		if(retorno==0){
			if((claveAcceso!=null)&&(o.claveAcceso==null)){
				retorno="A".compareTo("B");
			}
		}
		if(retorno==0){
			retorno=codigoSucursal.compareTo(o.codigoSucursal);
			if(retorno==0){
				retorno=codigoPuntoVenta.compareTo(o.codigoPuntoVenta);
				if(retorno==0){
					retorno=secuencia.compareTo(o.secuencia);
				}
			}
		}
		
		return retorno;
	}
	
}
