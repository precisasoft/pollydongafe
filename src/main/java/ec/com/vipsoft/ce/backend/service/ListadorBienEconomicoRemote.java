package ec.com.vipsoft.ce.backend.service;

import java.util.Set;

import javax.ejb.Remote;

import ec.com.vipsoft.erp.abinadi.dominio.BienEconomico;

@Remote
public interface ListadorBienEconomicoRemote {

	public abstract Set<BienEconomico> listarBienesDisponibles(String rucEmisor);

	public abstract BienEconomico listarBien(String rucEmisor, String codigo);

	public abstract void registrarBienEconomico(String rucEmisor,	String codigo, String descripcion, String codigoIva,String codigoPorcentajeIva, boolean esProducto);

}