package ec.com.vipsoft.ce.backend.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.com.vipsoft.ce.backend.model.Contingencia;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;

@Stateless
public class AdministradorAutorizacionContingencia {
	
	@PersistenceContext
	EntityManager em;
	public String siguienteClaveContingencia(Entidad e){
		Query q=em.createQuery("select c.id from Contingencia c where c.entidad.id=?1 and  c.usado=?1 order by c.id");
		q.setMaxResults(1);
		q.setParameter(1, e.getId());
		q.setParameter(2, Boolean.FALSE);
		Contingencia contingencia=em.find(Contingencia.class,(String) q.getResultList().get(0));
		contingencia.setFechaUso(new Date());
		contingencia.setUsado(true);
		return contingencia.getId();
		
	}
	public String siguienteClaveContingencia(String rucEmisor){
		Query q=em.createQuery("select c.id from Contingencia c where c.entidad.ruc=?1 and  c.usado=?1 order by c.id");
		q.setMaxResults(1);
		q.setParameter(1, rucEmisor);
		q.setParameter(2, Boolean.FALSE);
		Contingencia contingencia=em.find(Contingencia.class,(String) q.getResultList().get(0));
		contingencia.setFechaUso(new Date());
		contingencia.setUsado(true);
		return contingencia.getId();
		
	}
	public void registrarNumeroContingenciaDisponible(List<String> numerosDisonibles,Entidad e){
		for(String numeroDisponible:numerosDisonibles){
			Contingencia numeroContingencia=new Contingencia();
			numeroContingencia.setId(numeroDisponible);
			numeroContingencia.setEntidad(e);
			numeroContingencia.setUsado(false);
			em.persist(numeroContingencia);	
		}
		
	}
	public void registrarNumeroContingenciaDisponible(String numeroDisonible,Entidad e){
		Contingencia numeroContingencia=new Contingencia();
		numeroContingencia.setId(numeroDisonible);
		numeroContingencia.setEntidad(e);
		numeroContingencia.setUsado(false);
		em.persist(numeroContingencia);
	}

}
