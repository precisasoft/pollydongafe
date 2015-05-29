package ec.com.vipsoft.ce.ui;

import java.io.Serializable;

public class ComprobanteRideXmlBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6882833204601880092L;
	private String fecha;
	private String autorizacion;
	private String  claveAcceso;
	private String numeroDocumento;
	private String nota;
	private String tipo;
	private String monto;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
		
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getAutorizacion() {
		return autorizacion;
	}
	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}
	public String getClaveAcceso() {
		return claveAcceso;
	}
	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}
	
}
