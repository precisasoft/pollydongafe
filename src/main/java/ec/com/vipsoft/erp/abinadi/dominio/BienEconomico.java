package ec.com.vipsoft.erp.abinadi.dominio;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

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
	@NotNull
	private String codigo;
	private String codigoIce;
	//@Column(name="codigoiva" ,columnDefinition="char(1) default '2' check codigoiva='0' or codigoiva='2' or codigoiva='6' or codigoiva='7'")
	private String codigoIva;
	@NotNull
	private String descripcion;
	@NotNull
	@ManyToOne
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
		}
		return retorno;
	}
   
}
