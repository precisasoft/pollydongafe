package ec.com.vipsoft.ce.backend.service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.com.vipsoft.erp.abinadi.dominio.DemografiaCliente;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;

@Stateless
public class RegistradorDemografia {

	@PersistenceContext
	private EntityManager em;
	public void registrarActualizaCliente(DemografiaCliente demografia,String rucEmisor){
		Query q=em.createQuery("select e from Entidad e where e.ruc=?1");
		q.setParameter(1, rucEmisor);
		List<Entidad>listaEntidad=q.getResultList();
		if(!listaEntidad.isEmpty()){
			Entidad e=em.find(Entidad.class, listaEntidad.get(0).getId());
			Query qde=em.createQuery("select d from DemografiaCliente d where d.entidadEmisora=?1 and d.identificacion=?2");
			qde.setParameter(1, e);
			qde.setParameter(2, demografia.getIdentificacion());
			List<DemografiaCliente>listaDemografia=qde.getResultList();
			if(!listaDemografia.isEmpty()){
				DemografiaCliente dem=em.find(DemografiaCliente.class, listaDemografia.get(0).getId());
				dem.setCorreoElectronico(demografia.getCorreoElectronico());
				dem.setDireccion(demografia.getDireccion());
				dem.setRazonSocial(demografia.getRazonSocial());
				dem.setTipoIdentificacion(demografia.getTipoIdentificacion());					
			}else{
				demografia.setEntidadEmisora(e);				
				em.persist(demografia);
				
			}
			
		}
		
	}

	public Set<DemografiaCliente>listarClientes(String rucEmisor){
		Set<DemografiaCliente>retorno=new TreeSet<DemografiaCliente>();
		
		Query q=em.createQuery("select e from Entidad e where e.ruc=?1");
		q.setParameter(1, rucEmisor);
		List<Entidad>listaEntidad=q.getResultList();
		if(!listaEntidad.isEmpty()){
			Entidad e=em.getReference(Entidad.class, listaEntidad.get(0).getId());
			Query qd=em.createQuery("select d from DemografiaCliente d where d.entidadEmisora=?1");
			qd.setParameter(1, e);
			retorno.addAll(qd.getResultList());		
		}
		return retorno;
		
	}
}
