package ec.com.vipsoft.ce.reportes;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReporteFacturado implements Serializable,Comparable<ReporteFacturado>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2222696218559619946L;
	private String fecha;
	private String establecimiento;
	private String puntoEmision;
	private String secuencia;
	private String claveAcceso;
	private String autorizacion;
	private String identificacionBeneficiario;
	private BigDecimal subtotal0;
	private BigDecimal subtotal12;
	private BigDecimal iva;
	private BigDecimal total;
	private String nota;
	private String autorizado;
	public void establecerAutorizado(){
		autorizado="\u2713";
	}
	public void establecerRechazado(){
		autorizado="\u274C";
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
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
	public String getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}
	public String getClaveAcceso() {
		return claveAcceso;
	}
	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}
	public String getAutorizacion() {
		return autorizacion;
	}
	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}
	public String getIdentificacionBeneficiario() {
		return identificacionBeneficiario;
	}
	public void setIdentificacionBeneficiario(String identificacionBeneficiario) {
		this.identificacionBeneficiario = identificacionBeneficiario;
	}
	public BigDecimal getSubtotal0() {
		return subtotal0;
	}
	public void setSubtotal0(BigDecimal subtotal0) {
		this.subtotal0 = subtotal0;
	}
	public BigDecimal getSubtotal12() {
		return subtotal12;
	}
	public void setSubtotal12(BigDecimal subtotal12) {
		this.subtotal12 = subtotal12;
	}
	public BigDecimal getIva() {
		return iva;
	}
	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public String getAutorizado() {
		return autorizado;
	}
	public void setAutorizado(String autorizado) {
		this.autorizado = autorizado;
	}
	@Override
	public int compareTo(ReporteFacturado o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
