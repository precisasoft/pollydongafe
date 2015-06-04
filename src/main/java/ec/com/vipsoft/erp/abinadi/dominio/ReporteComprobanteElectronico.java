package ec.com.vipsoft.erp.abinadi.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReporteComprobanteElectronico implements Serializable,Comparable<ReporteComprobanteElectronico>{


	private static final long serialVersionUID = -972303187702583592L;
	private String fecha;
	private String autorizacion;
	private String claveAcceso;
	private String numeroDocumento;
	private String nota;
	private BigDecimal monto;
	
public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}

	public String getClaveAcceso() {
		return claveAcceso;
	}

	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

		@Override
	public int compareTo(ReporteComprobanteElectronico o) {
		int retorno=0;
		if((claveAcceso!=null)&&(o.claveAcceso==null)){
			retorno="A".compareTo("B");
		}
		if(retorno==0){
			if((claveAcceso!=null)&&(o.claveAcceso==null)){
				retorno="A".compareTo("B");
			}
		}
		if(retorno==0){
			retorno=numeroDocumento.compareTo(o.numeroDocumento);
		}
		
		return retorno;
	}
//	@Override
//	public int compareTo(ReporteComprobanteElectronico o) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
	
}
