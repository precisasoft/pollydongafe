package ec.com.vipsoft.ce.backend.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ConvertirPruebaEnProduccion {

	@PersistenceContext
	private EntityManager em;
	public void convertirYEnviarCompronbateDePruebasAProduccion(String claveAccesoPruebas){
		
		
	}
}
