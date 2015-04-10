package ec.com.vipsoft.ce.comprobantesNeutros;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class GuiaRemisionBinding extends BaseComprobanteElectronicoBinding {
	
	@NotNull(message="este campo no debe ser null")
	@Pattern(regexp = "[0-9]{3}-[0-9]{3}-[0-9]{9}")
	protected String numeroFactura;
	@NotNull(message="este campo no debe ser null")
	@Size(max=20)
	protected String identifcacionTransportista;
	@NotNull(message="este campo no debe ser null")
	@Size(max=100)
	protected String razonSocialTransportista;
	private Date fechaInicio;
	private Date fechaFin;
	protected String placa;
	private String direccionPartida;
	
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	

	public String getDireccionPartida() {
		return direccionPartida;
	}
	public void setDireccionPartida(String direccionPartida) {
		this.direccionPartida = direccionPartida;
	}


	@Valid
	protected List<Destinatario>destinatarios;
	
	public List<Destinatario> getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(List<Destinatario> destinatarios) {
		this.destinatarios = destinatarios;
	}
	public String getNumeroFactura() {
		return numeroFactura.replace("\r", "").replace("\n", "");
	}
	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}
	public String getIdentifcacionTransportista() {
		return identifcacionTransportista.replace("\r", "").replace("\n", "");
	}
	public void setIdentifcacionTransportista(String identifcacionTransportista) {
		this.identifcacionTransportista = identifcacionTransportista;
	}
	public String getRazonSocialTransportista() {
		return razonSocialTransportista.replace("\r", "").replace("\n", "");
	}
	public void setRazonSocialTransportista(String razonSocialTransportista) {
		this.razonSocialTransportista = razonSocialTransportista;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getFechaInicioTexto() {		
		return sdf.format(getFechaInicio());
	}
	
	public String getFechaFinTexto(){
		return sdf.format(getFechaFin());
	}
	
}
