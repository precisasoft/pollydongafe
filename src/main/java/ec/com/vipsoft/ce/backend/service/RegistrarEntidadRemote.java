package ec.com.vipsoft.ce.backend.service;


import javax.ejb.Remote;

import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.erp.abinadi.dominio.Establecimiento;
@Remote
public interface RegistrarEntidadRemote {

	//@RolesAllowed(value="administrador")
	public abstract boolean registrarEntidad(Entidad entidad,Establecimiento establecimiento);

}