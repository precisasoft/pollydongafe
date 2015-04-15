package ec.com.vipsoft.ce.sri.autorizacion.wsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mensaje", propOrder = {
    "identificador",
    "mensaje",
    "informacionAdicional",
    "tipo"
})
public class Mensaje {
	@XmlElement(required=false)
    protected String identificador;
	@XmlElement(required=false)
    protected String mensaje;
	@XmlElement(required=false)
    protected String informacionAdicional;
	@XmlElement(required=false)
    protected String tipo;
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getInformacionAdicional() {
		return informacionAdicional;
	}
	public void setInformacionAdicional(String informacionAdicional) {
		this.informacionAdicional = informacionAdicional;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
    
}