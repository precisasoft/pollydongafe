package ec.com.vipsoft.erp.abinadi.dominio;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity

public class DocumentoFirmado implements Serializable {
	private static final long serialVersionUID = 3712073068069037348L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(columnDefinition = "varchar(32768)")
	private String convertidoEnXML;
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	private Entidad entidad;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getConvertidoEnXML() {
		return convertidoEnXML;
	}
	public void setConvertidoEnXML(String convertidoEnXML) {
		this.convertidoEnXML = convertidoEnXML;
	}
	public Entidad getEntidad() {
		return entidad;
	}
	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}
 
}
