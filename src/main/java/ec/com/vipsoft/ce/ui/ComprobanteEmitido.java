package ec.com.vipsoft.ce.ui;

import java.io.Serializable;

public class ComprobanteEmitido implements Serializable,Comparable<ComprobanteEmitido> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8267536263320428796L;
	private Long id;
	private String numeroDocumento;
	private String fechaEmision;
	private String claveAcceso;
	private String numeroAutorizacion;	
	private String tipo;
	private String nota;
	private String monto;
	
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getNumeroAutorizacion() {
		return numeroAutorizacion;
	}
	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClaveAcceso() {
		return claveAcceso;
	}
	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@Override
	public int compareTo(ComprobanteEmitido o) {
		int retorno=0;
		if(retorno==0){
			if((id!=null)&&(o.id!=null)){
				retorno=id.compareTo(o.id);
				if(retorno==0){
					retorno=retorno*(-1);
				}
			}
			if(retorno==0){
				if((numeroAutorizacion!=null)&&(o.numeroAutorizacion!=null)){
					retorno=numeroAutorizacion.compareTo(o.numeroAutorizacion);
					retorno=retorno*-1;
				}
				if(retorno==0){
					retorno=numeroDocumento.compareTo(o.numeroDocumento);
					if(retorno==0){
						retorno=claveAcceso.compareTo(o.claveAcceso);
					}
					if(retorno<1){
						retorno=1;
					}
					if(retorno>1){
						retorno=-1;
					}
				}	
			}													
		}
		return retorno;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}
	
	
}
