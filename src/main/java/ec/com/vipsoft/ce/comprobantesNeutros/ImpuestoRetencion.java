package ec.com.vipsoft.ce.comprobantesNeutros;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Pattern;


public class ImpuestoRetencion implements Comparable<ImpuestoRetencion>,Serializable{

	protected SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
	private static final long serialVersionUID = 1101567618332835734L;
	@Pattern(regexp="[1-2,6]{1,1}")
	protected String codigo;
	@Pattern(regexp="[1-3]{1,1}")
	protected String codigoRetention;
	protected BigDecimal baseImponible;
	@Pattern(regexp="[1-3]{1,3}")
	protected BigDecimal porcentajeRetencion;
	protected BigDecimal valorRetenido;
	protected TipoComprobante tipoComprobante;
	protected String numeroDocumento;
	protected Date fechaEmisionDocumentoSustento;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCodigoRetention() {
		return codigoRetention;
	}
	public void setCodigoRetention(String codigoRetention) {
		this.codigoRetention = codigoRetention;
	}
	public BigDecimal getBaseImponible() {
		return baseImponible;
	}
	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
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
	public TipoComprobante getTipoComprobante() {
		return tipoComprobante;
	}
	public void setTipoComprobante(TipoComprobante tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	public Date getFechaEmisionDocumentoSustento() {
		return fechaEmisionDocumentoSustento;
	}
	public void setFechaEmisionDocumentoSustento(Date fechaEmisionDocumentoSustento) {
		this.fechaEmisionDocumentoSustento = fechaEmisionDocumentoSustento;
	}
	public void actualizarCampoPorcentaje() {
		if(codigoRetention.equalsIgnoreCase("1")){
			porcentajeRetencion=new BigDecimal("30");
			if(baseImponible!=null){
				actualizarValorRetenido();
				
			}
		}if(codigoRetention.equalsIgnoreCase("2")){
			porcentajeRetencion=new BigDecimal("70");
			if(baseImponible!=null){
				actualizarValorRetenido();
				
			}
		}
		if(codigoRetention.equalsIgnoreCase("3")){
			porcentajeRetencion=new BigDecimal("100");
			if(baseImponible!=null){
				actualizarValorRetenido();
				
			}
		}
		if(codigoRetention.equalsIgnoreCase("4580")){
			porcentajeRetencion=new BigDecimal("5");
			if(baseImponible!=null){
				actualizarValorRetenido();
				
			}
		}
	}
	public void actualizarValorRetenido() {
		valorRetenido=baseImponible.multiply(porcentajeRetencion).divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);
		
	}
	@Override
	public int compareTo(ImpuestoRetencion o) {
		int retorno=numeroDocumento.compareToIgnoreCase(o.numeroDocumento);
		if(retorno==0){
			retorno=codigoRetention.compareToIgnoreCase(o.codigoRetention);
		
		}
		return retorno;
	}
	public String getFechaEmisionDocumentoSustentoTexTo() {
		// TODO Auto-generated method stub
		return sdf.format(fechaEmisionDocumentoSustento);
	}
	
	


}
