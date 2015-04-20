package ec.com.vipsoft.erp.abinadi.dominio;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity implementation class for Entity: BienEconomico
 *
 */
@Entity
@DiscriminatorColumn(name="tipo",length=1,discriminatorType=DiscriminatorType.STRING)
public class BienEconomico implements Serializable ,Comparable<BienEconomico>{

	   
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private static final long serialVersionUID = 1L;
//	@NotNull
	private String codigo;
	private String codigoIce;	
	//@Column(name="codigoiva" ,columnDefinition="char(1) default '2' check codigoiva='0' or codigoiva='2' or codigoiva='6' or codigoiva='7'")
	private String codigoIva;
	//@NotNull
	private String descripcion;
	//@NotNull
	//@ManyToOne
	private Entidad entidad;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCodigoIce() {
		return codigoIce;
	}
	public void setCodigoIce(String codigoIce) {
		this.codigoIce = codigoIce;
	}
	public String getCodigoIva() {
		return codigoIva;
	}
	public void setCodigoIva(String codigoIva) {
		this.codigoIva = codigoIva;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Entidad getEntidad() {
		return entidad;
	}
	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}
	public BienEconomico() {
		super();
	}   
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public int compareTo(BienEconomico o) {
		int retorno=codigo.compareTo(o.codigo);
		if(retorno==0){
			retorno=descripcion.compareTo(o.descripcion);
			if(retorno==0){
				retorno=entidad.compareTo(o.entidad);
			}
		}
		return retorno;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((entidad == null) ? 0 : entidad.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BienEconomico other = (BienEconomico) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (entidad == null) {
			if (other.entidad != null)
				return false;
		} else if (!entidad.equals(other.entidad))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
   
}
