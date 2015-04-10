package ec.com.vipsoft.ce.backend.service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.com.vipsoft.erp.abinadi.dominio.BienEconomico;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;

@Stateless
public class ListadorBienEconomico implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 239601331385020277L;
	@PersistenceContext
	private EntityManager em;
	public Set<BienEconomico>listarBienesDisponibles(String rucEmisor){
		Set<BienEconomico>retorno=new TreeSet<>();
		Query qentidad=em.createQuery("select e from Entidad e where e.ruc=?1");
		qentidad.setParameter(1, rucEmisor);
		List<Entidad>listadoEntidad=qentidad.getResultList();
		if(!listadoEntidad.isEmpty()){
			Entidad entidad=listadoEntidad.get(0);
			Query qbienes=em.createQuery("select b from BienEconomico b where b.entidad=?1");
			qbienes.setParameter(1, entidad);
			List<BienEconomico>listadoBienes=qbienes.getResultList();
			if(!listadoBienes.isEmpty()){
				retorno.addAll(listadoBienes);
			}
		}
		return retorno;		
	}
}
