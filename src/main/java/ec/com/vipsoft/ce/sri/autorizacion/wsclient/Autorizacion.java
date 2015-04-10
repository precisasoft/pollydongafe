package ec.com.vipsoft.ce.sri.autorizacion.wsclient;


import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "estado",
    "numeroAutorizacion",
    "fechaAutorizacion",
    "ambiente",
    "comprobante",
    "informacionAdicional"
})
@XmlRootElement(name = "autorizacion", namespace = "")
public class Autorizacion {
	@XmlElement(required=true)
	private String estado;
	@XmlElement(required=true)
	private String numeroAutorizacion;
	@XmlElement(required=true)
	private Date fechaAutorizacion;
	@XmlElement(required=true)
	private String ambiente;
	@XmlElement(required=true)
	private String comprobante;
	@XmlElement(required=false)
	private String informacionAdicional;
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNumeroAutorizacion() {
		return numeroAutorizacion;
	}
	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}
	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}
	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}
	public String getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}
	public String getComprobante() {
		return comprobante;
	}
	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}
	public String getInformacionAdicional() {
		return informacionAdicional;
	}
	public void setInformacionAdicional(String informacionAdicional) {
		this.informacionAdicional = informacionAdicional;
	}
	
}
