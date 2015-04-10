package ec.com.vipsoft.ce.visorride;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReporteRetencionDetalleBean implements Serializable{
	
	private static final long serialVersionUID = 1831132852376541726L;
	private String comprobante;
	private String numero;
	private String fechaEmision;
	private String ejercicioFiscal;
	private BigDecimal baseImponible;
	private String impuesto;
	private BigDecimal porcentajeRetencion;
	private BigDecimal valorRetenido;
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
	public String getEjercicioFiscal() {
		return ejercicioFiscal;
	}
	public void setEjercicioFiscal(String ejercicioFiscal) {
		this.ejercicioFiscal = ejercicioFiscal;
	}
	public BigDecimal getBaseImponible() {
		return baseImponible;
	}
	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}
	public String getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(String impuesto) {
		this.impuesto = impuesto;
	}
	public BigDecimal getPorcentajeRetencion() {
		return porcentajeRetencion;
	}
	public void setPorcentajeRetencion(BigDecimal porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}
	public BigDecimal getValorRetenido() {
		return valorRetenido;
	}
	public void setValorRetenido(BigDecimal valorRetenido) {
		this.valorRetenido = valorRetenido;
	}
	

}
