package ec.com.vipsoft.ce.backend.service;

import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.com.vipsoft.ce.backend.remoteinterface.RegistrarEntidadRemote;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.erp.abinadi.dominio.Establecimiento;
import ec.com.vipsoft.erp.abinadi.dominio.PuntoVenta;

@Stateless
@WebService
public class RegistrarEntidad implements RegistrarEntidadRemote {
	
	@PersistenceContext
	private EntityManager em;
	//@RolesAllowed(value="administrador")
	/* (non-Javadoc)
	 * @see ec.com.vipsoft.ce.backend.service.RegistrarEntidadRemote#registrarEntidad(ec.com.vipsoft.erp.abinadi.dominio.Entidad, ec.com.vipsoft.erp.abinadi.dominio.Establecimiento)
	 */
	@Override
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
