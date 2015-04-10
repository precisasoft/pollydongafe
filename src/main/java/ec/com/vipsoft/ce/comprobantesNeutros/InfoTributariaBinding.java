package ec.com.vipsoft.ce.comprobantesNeutros;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class InfoTributariaBinding implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5921707217376789242L;
	@Pattern(regexp="[0-9]{13}")
	private String rucEmisor;
	private String razonSocialEmisor;
	private String nombreComercial;
	@Pattern(regexp="[0-9]{3,3}")
	private String establecimiento;
	@Pattern(regexp="[0-9]{3,3}")
	private String puntoEmision;
	@Size(max=300)
	private String direccionMatriz;
	@Pattern(regexp="[0-9]{9,9}")
	protected String secuenciaDocumento;
	
	
	public String getSecuenciaDocumento() {
		return secuenciaDocumento;
	}
	public void setSecuenciaDocumento(String secuenciaDocumento) {
		this.secuenciaDocumento = secuenciaDocumento;
	}
	public String getDireccionMatriz() {
		return direccionMatriz;
	}
	public void setDireccionMatriz(String direccionMatriz) {
		this.direccionMatriz = direccionMatriz;
	}
	public String getRucEmisor() {
		return rucEmisor;
	}
	public void setRucEmisor(String rucEmisor) {
		this.rucEmisor = rucEmisor;
	}
	public String getRazonSocialEmisor() {
		return razonSocialEmisor;
	}
	public void setRazonSocialEmisor(String razonSocialEmisor) {
		this.razonSocialEmisor = razonSocialEmisor;
	}
	public String getNombreComercial() {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	public String getEstablecimiento() {
		return establecimiento;
	}
	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}
	public String getPuntoEmision() {
		return puntoEmision;
	}
	public void setPuntoEmision(String puntoEmision) {
		this.puntoEmision = puntoEmision;
	}
	
}
