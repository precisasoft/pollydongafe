package ec.com.vipsoft.erp.abinadi.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class DemografiaCliente implements Serializable,Comparable<DemografiaCliente>{

	private static final long serialVersionUID = 8164778285052880649L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String identificacion;	
	private String tipoIdentificacion;
	@NotNull
	private String razonSocial;
	private String direccion;
	private String correoElectronico;
	
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	@ManyToOne
	private Entidad entidadEmisora;
	
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	@Override
	public int compareTo(DemografiaCliente o) {
		int retorno=razonSocial.compareTo(o.razonSocial);
		if(retorno==0){
			retorno=identificacion.compareTo(o.identificacion);
			if(retorno==0){
				retorno=id.compareTo(o.id);	
			}			
		}
		return retorno;
	}
	public Entidad getEntidadEmisora() {
		return entidadEmisora;
	}
	public void setEntidadEmisora(Entidad entidadEmisora) {
		this.entidadEmisora = entidadEmisora;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

}
