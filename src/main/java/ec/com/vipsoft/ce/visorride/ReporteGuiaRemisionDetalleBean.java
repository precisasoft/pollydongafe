package ec.com.vipsoft.ce.visorride;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReporteGuiaRemisionDetalleBean implements Serializable,Comparable<ReporteGuiaRemisionDetalleBean>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -757621132251035040L;
	private BigDecimal cantidad;
	private String descripcion;
	private String codigoInterno;
	private String codigoAuxiliar;
	private String comprobante;
	private String numero;
	private String fechaEmision;
	private String identificacionDestinatario;
	private String dirDestinatario;
	private String motivoTraslado;
	private String codDocSustento;
	private String numDocSustento;
	private String numAutDocSustento;
	private String fechaEmisionDocSustento;
	private String razonSocialDestinatario;
	
	
	
	

	public String getComprobante() {
		return comprobante;
	}


	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getFechaEmision() {
		return fechaEmision;
	}


	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}


	public String getIdentificacionDestinatario() {
		return identificacionDestinatario;
	}


	public void setIdentificacionDestinatario(String identificacionDestinatario) {
		this.identificacionDestinatario = identificacionDestinatario;
	}


	public String getDirDestinatario() {
		return dirDestinatario;
	}


	public void setDirDestinatario(String dirDestinatario) {
		this.dirDestinatario = dirDestinatario;
	}


	public String getMotivoTraslado() {
		return motivoTraslado;
	}


	public void setMotivoTraslado(String motivoTraslado) {
		this.motivoTraslado = motivoTraslado;
	}


	public String getCodDocSustento() {
		return codDocSustento;
	}


	public void setCodDocSustento(String codDocSustento) {
		if(codDocSustento.equalsIgnoreCase("04")){
			this.codDocSustento="FACTURA";
		}
		
		this.codDocSustento = codDocSustento;
	}


	public String getNumDocSustento() {
		return numDocSustento;
	}


	public void setNumDocSustento(String numDocSustento) {
		this.numDocSustento = numDocSustento;
	}


	public String getNumAutDocSustento() {
		return numAutDocSustento;
	}


	public void setNumAutDocSustento(String numAutDocSustento) {
		this.numAutDocSustento = numAutDocSustento;
	}


	public String getFechaEmisionDocSustento() {
		return fechaEmisionDocSustento;
	}


	public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
		this.fechaEmisionDocSustento = fechaEmisionDocSustento;
	}


	public String getRazonSocialDestinatario() {
		return razonSocialDestinatario;
	}


	public void setRazonSocialDestinatario(String razonSocialDestinatario) {
		this.razonSocialDestinatario = razonSocialDestinatario;
	}


	public BigDecimal getCantidad() {
		return cantidad;
	}


	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getCodigoInterno() {
		return codigoInterno;
	}


	public void setCodigoInterno(String codigoInterno) {
		this.codigoInterno = codigoInterno;
	}


	public String getCodigoAuxiliar() {
		return codigoAuxiliar;
	}


	public void setCodigoAuxiliar(String codigoAuxiliar) {
		this.codigoAuxiliar = codigoAuxiliar;
	}


	@Override
	public int compareTo(ReporteGuiaRemisionDetalleBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
