package ec.com.vipsoft.ce.comprobantesNeutros;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Destinatario implements Serializable{

	protected SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
	private static final long serialVersionUID = 4765397863916795888L;
	@NotNull(message="este campo no debe ser null")
	protected String identificacionDestinatario;
	@NotNull(message="este campo no debe ser null")
	protected String razonSocialDestinatario;
	@NotNull(message="este campo no debe ser null")
	protected String direccionDestinatario;
	@NotNull(message="este campo no debe ser null")
	protected String motivoTraslado;
	protected String codigoDocumento;
	@Pattern(regexp="[09]{3}-[0-9]{3}-[0-9]{9}",message="numero de documento invalido")
	protected String numeroDocumento;
	@Pattern(regexp="[0-9]{35,50}")
	protected String numeroAutorizacion;	
	protected Date fechaEmision;
	@Pattern(regexp="[0-9]{3}")
	protected String codigoEstablecimientoDestino;
	protected String ruta;
	
	
	public SimpleDateFormat getSdf() {
		return sdf;
	}
	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getCodigoEstablecimientoDestino() {
		return codigoEstablecimientoDestino;
	}
	public void setCodigoEstablecimientoDestino(String codigoEstablecimientoDestino) {
		this.codigoEstablecimientoDestino = codigoEstablecimientoDestino;
	}
	@Valid
	protected List<DetalleGuiaRemision>detalles;
	
	
	public List<DetalleGuiaRemision> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<DetalleGuiaRemision> detalles) {
		this.detalles = detalles;
	}
	public String getIdentificacionDestinatario() {
		return identificacionDestinatario.replace("\r", "").replace("\n", "");
	}
	public void setIdentificacionDestinatario(String identificacionDestinatario) {
		this.identificacionDestinatario = identificacionDestinatario;
	}
	public String getRazonSocialDestinatario() {
		return razonSocialDestinatario.replace("\r", "").replace("\n", "");
	}
	public void setRazonSocialDestinatario(String razonSocialDestinatario) {
		this.razonSocialDestinatario = razonSocialDestinatario;
	}
	public String getDireccionDestinatario() {
		return direccionDestinatario.replace("\r", "").replace("\n", "");
	}
	public void setDireccionDestinatario(String direccionDestinatario) {
		this.direccionDestinatario = direccionDestinatario;
	}
	public String getMotivoTraslado() {
		return motivoTraslado.replace("\r", "").replace("\n", "");
	}
	public void setMotivoTraslado(String motivoTraslado) {
		this.motivoTraslado = motivoTraslado;
	}
	public String getCodigoDocumento() {
		return codigoDocumento.replace("\r", "").replace("\n", "");
	}
	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}
	public String getNumeroDocumento() {
		return numeroDocumento.replace("\r", "").replace("\n", "");
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getNumeroAutorizacion() {
		return numeroAutorizacion.replace("\r", "").replace("\n", "");
	}
	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	protected String sinCRLF(String contenido){
		return contenido.replace("\r", "").replace("\n", "");		
	}
	public String getFechaEmisionTexto() {
		
		return sdf.format(getFechaEmision());
	}

}
