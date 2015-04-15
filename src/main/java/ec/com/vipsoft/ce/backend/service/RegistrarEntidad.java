package ec.com.vipsoft.ce.backend.service;

import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.erp.abinadi.dominio.Establecimiento;
import ec.com.vipsoft.erp.abinadi.dominio.PuntoVenta;

@Stateless
@WebService
public class RegistrarEntidad {
	
	@PersistenceContext
	private EntityManager em;
	//@RolesAllowed(value="administrador")
	public boolean registrarEntidad(@WebParam(name = "entidad") Entidad entidad,Establecimiento establecimiento){
		establecimiento.setEntidad(entidad);	
		for(PuntoVenta pos:establecimiento.getPos()){
			pos.setEstablecimiento(establecimiento);
		}
		em.persist(entidad);
		em.persist(establecimiento);
		return true;
	}

}
