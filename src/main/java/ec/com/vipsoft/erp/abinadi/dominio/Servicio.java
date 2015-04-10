package ec.com.vipsoft.erp.abinadi.dominio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("s")
public class Servicio extends BienEconomico {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1458245412318269532L;

}
