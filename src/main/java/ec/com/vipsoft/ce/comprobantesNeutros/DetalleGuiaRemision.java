package ec.com.vipsoft.ce.comprobantesNeutros;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DetalleGuiaRemision implements Serializable{
	private static final long serialVersionUID = 2499466998500930041L;
	@Min(value=0, message="el valor no puede ser negativo")
	private BigDecimal cantidad;
	private String codigoInterno;
	@NotNull
	private String descripcion;
	private Map<String,String>infoAdicional;
	
	public DetalleGuiaRemision() {
		super();
		infoAdicional=new HashMap<>();
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public String getCodigoInterno() {
		return codigoInterno.replace("\r", "").replace("\n", "");
	}

	public String getDescripcion() {
		return descripcion.replace("\r", "").replace("\n", "");
	}

	public Map<String, String> getInfoAdicional() {
		return infoAdicional;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public void setCodigoInterno(String codigoInterno) {
		this.codigoInterno = codigoInterno;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setInfoAdicional(Map<String, String> infoAdicional) {
		this.infoAdicional = infoAdicional;
	}
	protected String sinCRLF(String contenido){
		return contenido.replace("\r", "").replace("\n", "");		
	}
	public void anadirInfoAdicional(@Size(max=300,min=1)String clave,@Size(max=300,min=1)String valor){
		if(infoAdicional.size()<15){
			String _clave=sinCRLF(clave);
			String _valor=sinCRLF(valor);
			if((_clave.length()>0)&&(_valor.length()>0)){
				infoAdicional.put(_clave, _valor);	
			}				
		}
		
	}
	
	

}
