package ec.com.vipsoft.erp.abinadi.dominio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("p")
public class Producto extends BienEconomico {

	private static final long serialVersionUID = -1876598384603553645L;

}
