package ec.com.vipsoft.ce.backend.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.com.vipsoft.ce.backend.remoteinterface.NotificadorSecuenciaFaltante;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;
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
	public List<ReporteComprobanteElectronico> listarSecuenciasFaltantes(String rucEmisor,Date fechaI,Date fechaF) {
		Query qentidad=em.createQuery("select e from Entidad e where e.ruc=?1");
		qentidad.setParameter(1, rucEmisor);
		List<Entidad>listaEntidad=qentidad.getResultList();
		if(!listaEntidad.isEmpty()){
			Query qcomprobantes=em.createQuery("select c from ComprobanteElectronico c where c.entidadEmisora=?1 and c.fechaEmision>=?2 and c.fechaEmision<=?3");
			qcomprobantes.setParameter(1, listaEntidad.get(0));
			qcomprobantes.setParameter(2,fechaI);
			qcomprobantes.setParameter(3,fechaF);
			List<ComprobanteElectronico>listadoComprobantes=qcomprobantes.getResultList();
			
					
		}
		
		return null;
	}

//	@Override
//	public List<ReporteComprobanteElectronico> listarSecuenciasFaltantes(
//			String rucEmisor, String sucursal, String ptoVenta, Long inicio,
//			Long fin, TipoComprobante tipo) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	

}
