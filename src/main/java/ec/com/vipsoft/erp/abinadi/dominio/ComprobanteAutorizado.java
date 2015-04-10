package ec.com.vipsoft.erp.abinadi.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ComprobanteAutorizado implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4964183567075231998L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private byte[] enXML;
	private byte[] enPDF;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public byte[] getEnXML() {
		return enXML;
	}
	public void setEnXML(byte[] enXML) {
		this.enXML = enXML;
	}
	public byte[] getEnPDF() {
		return enPDF;
	}
	public void setEnPDF(byte[] enPDF) {
		this.enPDF = enPDF;
	}
	
}
