package ec.com.vipsoft.ce.comprobantesNeutros;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ComprobanteRetencionDetalleBinding implements Serializable,Comparable<ComprobanteRetencionDetalleBinding>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8642477900040065042L;
	private SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
	@Min(value=0 ,message="el valor no puede ser menor a 0")
	@NotNull(message="este campo no debe ser null")	
	private BigDecimal baseImponible;
	@NotNull(message="este campo no debe ser null")
	@Pattern(regexp="[0-9]{2}")
	private String codigoDocumento;
	@NotNull(message="este campo no debe ser null")
	private String codigoImpuesto;
	@NotNull(message="este campo no debe ser null")
	private Date fechaEmision;	
	@NotNull(message="este campo no debe ser null")
	@Pattern(regexp="[0-9]{3}-[0-9]{3}-[0-9]{9}")
	private String numeroDocumento;
	@Max(value=100,message="el porcentaje no puede ser > 100")
	@Min(value=0,message="el porcentaje no puede ser < 0")
	@NotNull(message="este campo no debe ser null")
	private BigDecimal porcentajeRetencion;
	@NotNull(message="este campo no debe ser null")
	protected String tipoImpuesto;
	@Min(value=0,message="el valor no puede ser menor a 0")
	@NotNull(message="este campo no debe ser null")
	private BigDecimal valorRetenido;
	
	@Override
	public int compareTo(ComprobanteRetencionDetalleBinding o) {
		int retorno=tipoImpuesto.compareTo(o.tipoImpuesto);
		if(retorno==0){
			retorno=codigoImpuesto.compareTo(o.codigoImpuesto);
			if(retorno==0){
				retorno=codigoDocumento.compareTo(o.codigoDocumento);
				if(retorno==0){
					retorno=numeroDocumento.compareToIgnoreCase(o.numeroDocumento);
				}
			}
		}
		return retorno;
	}

	public BigDecimal getBaseImponible() {
		return baseImponible;
	}

	public String getCodigoDocumento() {
		return codigoDocumento.replace("\r", "").replace("\n", "");
	}

	public String getCodigoImpuesto() {
		return codigoImpuesto.replace("\r", "").replace("\n", "");
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public String getNumeroDocumento() {
		return numeroDocumento.replace("\r", "").replace("\n", "");
	}

	public BigDecimal getPorcentajeRetencion() {
		return porcentajeRetencion;
	}

	public String getTipoImpuesto() {
		return tipoImpuesto.replace("\r", "").replace("\n", "");
	}

	public BigDecimal getValorRetenido() {
		return valorRetenido;
	}

	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}

	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}

	public void setCodigoImpuesto(String codigoImpuesto) {
		this.codigoImpuesto = codigoImpuesto;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public void setPorcentajeRetencion(BigDecimal porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}

	public void setTipoImpuesto(String tipoImpuesto) {
		this.tipoImpuesto = tipoImpuesto;
	}

	public void setValorRetenido(BigDecimal valorRetenido) {
		this.valorRetenido = valorRetenido;
	}

	public String getFechaEmisionTexto() {		
		return sdf.format(getFechaEmision());
	}
	
}
