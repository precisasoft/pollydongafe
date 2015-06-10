package ec.com.vipsoft.ce.backend.remoteinterface;

import java.util.List;

import javax.ejb.Remote;

import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
@Remote
public interface AdministradorAutorizacionContingenciaRemote {

	//public abstract void registrarNumeroContingenciaDisponible(	List<String> numerosDisonibles, Entidad e);
	public abstract void registrarNumeroContingenciaDisponible(	List<String> numerosDisonibles, String ruc);

	//public abstract void registrarNumeroContingenciaDisponible(	String numeroDisonible, Entidad e);

}