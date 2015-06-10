package ec.com.vipsoft.ce.backend.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.com.vipsoft.ce.backend.remoteinterface.RegistradorUsuarioRemote;
import ec.com.vipsoft.ce.backend.remoteinterface.RegistrarEntidadRemote;
import ec.com.vipsoft.ce.ui.RegistradorUsuarioBean;
import ec.com.vipsoft.cryptografia.CryptoUtil;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.erp.abinadi.dominio.Establecimiento;
import ec.com.vipsoft.erp.abinadi.dominio.PuntoVenta;

@Stateless
@WebService
public class RegistrarEntidad implements RegistrarEntidadRemote {
	
	@PersistenceContext
	private EntityManager em;
	@EJB
	private RegistradorUsuarioBean registradorUsuario;
	@Inject
	private CryptoUtil cryptoUtil;
	//@RolesAllowed(value="administrador")
	/* (non-Javadoc)
	 * @see ec.com.vipsoft.ce.backend.service.RegistrarEntidadRemote#registrarEntidad(ec.com.vipsoft.erp.abinadi.dominio.Entidad, ec.com.vipsoft.erp.abinadi.dominio.Establecimiento)
	 */
	@Override
	public boolean registrarEntidad(@WebParam(name = "entidad") Entidad entidad,Establecimiento establecimiento,String usuario,String pass){
		establecimiento.setEntidad(entidad);	
		for(PuntoVenta pos:establecimiento.getPos()){
			pos.setEstablecimiento(establecimiento);
		}
	
			String rucEncriptado=cryptoUtil.encrypt(entidad.getRuc());
			entidad.setRuc(rucEncriptado);
		
		em.persist(entidad);
		em.persist(establecimiento);
		registradorUsuario.registrarUsuario(usuario+"@"+entidad.getDominioInternet(), pass, "", "");
		return true;
	}

}
