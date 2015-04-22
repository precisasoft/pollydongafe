package ec.com.vipsoft.ce.backend.service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.com.vipsoft.ce.backend.remoteinterface.ListadorBienEconomicoRemote;
import ec.com.vipsoft.erp.abinadi.dominio.BienEconomico;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.erp.abinadi.dominio.Producto;
import ec.com.vipsoft.erp.abinadi.dominio.Servicio;

@Stateless

public class ListadorBienEconomico implements Serializable, ListadorBienEconomicoRemote{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 239601331385020277L;
	@PersistenceContext
	private EntityManager em;
	/* (non-Javadoc)
	 * @see ec.com.vipsoft.ce.backend.service.ListadorBienEconomicoRemote#listarBienesDisponibles(java.lang.String)
	 */
	@Override
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
	/* (non-Javadoc)
	 * @see ec.com.vipsoft.ce.backend.service.ListadorBienEconomicoRemote#listarBien(java.lang.String, java.lang.String)
	 */
	@Override
	public BienEconomico listarBien(String rucEmisor,String codigo){
		BienEconomico retorno=null;
		Query qentidad=em.createQuery("select e from Entidad e where e.ruc=?1");
		qentidad.setParameter(1, rucEmisor);
		List<Entidad>listadoEntidad=qentidad.getResultList();
		if(!listadoEntidad.isEmpty()){
			Entidad entidad=listadoEntidad.get(0);
			Query qbienes=em.createQuery("select b from BienEconomico b where b.entidad=?1 and b.codigo=?2");
			qbienes.setParameter(1, entidad);
			qbienes.setParameter(2, codigo);
			List<BienEconomico>listadoBienes=qbienes.getResultList();
			if(!listadoBienes.isEmpty()){
				retorno=listadoBienes.get(0);
			}
		}
		return retorno;		
	}
	/* (non-Javadoc)
	 * @see ec.com.vipsoft.ce.backend.service.ListadorBienEconomicoRemote#registrarBienEconomico(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void registrarBienEconomico(String rucEmisor,String codigo,String descripcion,String codigoIva,String codigoPorcentajeIva,boolean esProducto){
		BienEconomico nuevoBien=listarBien(rucEmisor,codigo);
		if(nuevoBien==null){
			Query qentidad=em.createQuery("select e from Entidad e where e.ruc=?1");
			qentidad.setParameter(1, rucEmisor);
			List<Entidad>listadoEntidad=qentidad.getResultList();
			if(!listadoEntidad.isEmpty()){
				Entidad e=em.getReference(Entidad.class, listadoEntidad.get(0).getId());
				if(esProducto){
					nuevoBien=new Producto();
				}else{
					nuevoBien=new Servicio();
				}
				nuevoBien.setCodigoIva(codigoPorcentajeIva);
				nuevoBien.setDescripcion(descripcion);
				nuevoBien.setCodigo(codigo);
				nuevoBien.setEntidad(e);
				em.persist(nuevoBien);
			}
			
		}else{
			BienEconomico modificado=em.find(BienEconomico.class, nuevoBien.getId());
			modificado.setCodigoIva(codigoPorcentajeIva);
			modificado.setDescripcion(descripcion);
		}
		
	}
}
