package ec.com.vipsoft.ce.sri.autorizacion.wsclient;


import java.util.ArrayList;
import java.util.List;

public class RespuestaAutorizacionComprobante {
	private String claveAccesoConsultada;
	private int numeroComprobantes;
	private List<Autorizacion> autorizaciones = new ArrayList<Autorizacion>();
	public String getClaveAccesoConsultada() {
		return claveAccesoConsultada;
	}
	public void setClaveAccesoConsultada(String claveAccesoConsultada) {
		this.claveAccesoConsultada = claveAccesoConsultada;
	}
	public int getNumeroComprobantes() {
		return numeroComprobantes;
	}
	public void setNumeroComprobantes(int numeroComprobantes) {
		this.numeroComprobantes = numeroComprobantes;
	}
	public List<Autorizacion> getAutorizaciones() {
		return autorizaciones;
	}
	public void setAutorizaciones(List<Autorizacion> autorizaciones) {
		this.autorizaciones = autorizaciones;
	}
	
}
