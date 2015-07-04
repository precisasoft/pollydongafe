package ec.com.vipsoft.erp.abinadi.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReporteComprobanteElectronico implements Serializable,
		Comparable<ReporteComprobanteElectronico> {

	private static final long serialVersionUID = -972303187702583592L;
	private String autorizacion;
	private String claveAcceso;
	private Date fecha;
	private BigDecimal monto;
	private String nota;
	private String numeroDocumento;
	private Boolean contingencia;
	private Boolean enviadoSRI;
	private Boolean enProduccion;
	private Integer codigoError;
	
	

	public Integer getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(Integer codigoError) {
		this.codigoError = codigoError;
	}

	public Boolean getEnProduccion() {
		return enProduccion;
	}

	public void setEnProduccion(Boolean enProduccion) {
		this.enProduccion = enProduccion;
	}

	public Boolean getEnviadoSRI() {
		return enviadoSRI;
	}

	public void setEnviadoSRI(Boolean enviadoSRI) {
		this.enviadoSRI = enviadoSRI;
	}

	public Boolean getContingencia() {
		return contingencia;
	}

	public void setContingencia(Boolean contingencia) {
		this.contingencia = contingencia;
	}

	@Override
	public int compareTo(ReporteComprobanteElectronico o) {
		int retorno = 0;
		if ((claveAcceso != null) && (o.claveAcceso == null)) {
			retorno = "A".compareTo("B");
		}
		if (retorno == 0) {
			if ((claveAcceso != null) && (o.claveAcceso == null)) {
				retorno = "A".compareTo("B");
			}
		}
		if (retorno == 0) {
			retorno = numeroDocumento.compareTo(o.numeroDocumento);
		}

		return retorno;
	}

	// @Override
	// public int compareTo(ReporteComprobanteElectronico o) {
	// // TODO Auto-generated method stub
	// return 0;
	// }

	public String getAutorizacion() {
		return autorizacion;
	}

	public String getClaveAcceso() {
		return claveAcceso;
	}


	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public String getNota() {
		return nota;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}

	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}

	

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

}
