package ec.com.vipsoft.ce.backend.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.com.vipsoft.ce.backend.remoteinterface.NotificadorSecuenciaFaltante;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico.TipoComprobante;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.erp.abinadi.dominio.ReporteComprobanteElectronico;

@Stateless
public class ReporteComprobanteCompletoBean implements NotificadorSecuenciaFaltante,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2658306850694939674L;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<ReporteComprobanteElectronico> listarSecuenciasFaltantes(String rucEmisor,String sucursal,	String ptoVenta, Long inicio, Long fin, TipoComprobante tipo) {
		Query qentidad=em.createQuery("select e from Entidad e where e.ruc=?1");
		qentidad.setParameter(1, rucEmisor);
		List<Entidad>listaEntidad=qentidad.getResultList();
		if(!listaEntidad.isEmpty()){
			Query qcomprobantes=em.createQuery("select c from ComprobanteElectronico c where c.entidadEmisora=?1 and c.tipo=?2 order by c.secuencia asc");
		}
		
		return null;
	}
	

}
