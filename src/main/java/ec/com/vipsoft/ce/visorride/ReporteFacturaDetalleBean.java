package ec.com.vipsoft.ce.visorride;


import java.io.Serializable;
import java.math.BigDecimal;

public class ReporteFacturaDetalleBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5445662513032468221L;
	private String codigoPrincipal;
	private String codigoAuxiliar;
	private BigDecimal cantidad;
	private String descripcion;
	private String detalleAdicional;
	private BigDecimal precioUnitario;
	private BigDecimal descuento;
	private BigDecimal precioTotal;
	public String getCodigoPrincipal() {
		return codigoPrincipal;
	}
	public void setCodigoPrincipal(String codigoPrincipal) {
		this.codigoPrincipal = codigoPrincipal;
	}
	public String getCodigoAuxiliar() {
		return codigoAuxiliar;
	}
	public void setCodigoAuxiliar(String codigoAuxiliar) {
		this.codigoAuxiliar = codigoAuxiliar;
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
	public String getDetalleAdicional() {
		return detalleAdicional;
	}
	public void setDetalleAdicional(String detalleAdicional) {
		this.detalleAdicional = detalleAdicional;
	}
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public BigDecimal getDescuento() {
		return descuento;
	}
	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}
	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}
	
	
}
