package ec.com.vipsoft.ce.backend.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.erp.abinadi.dominio.Establecimiento;
import ec.com.vipsoft.erp.abinadi.dominio.PuntoVenta;


@Stateless
//@WebService
public class AdministradorSecuenciasProduccion {

	@PersistenceContext
	private EntityManager em;
	
	public void estableceSecuenciaFactura(String rucEmisor,String codestablecimiento,String codpuntoVenta,String secuencia,boolean enProduccion){
		Query q=em.createQuery("select e from Entidad e where e.ruc=?1");
		q.setParameter(1, rucEmisor);
		List<Entidad>listaEntidad=q.getResultList();
		if(!listaEntidad.isEmpty()){
			Entidad entidad=em.find(Entidad.class, listaEntidad.get(0).getId());
			entidad.setFacturaEnPruebas(!enProduccion);
			Query qestablecimiento=em.createQuery("select e from Establecimiento e where e.entidad=?1 and e.codigo=?2");
			qestablecimiento.setParameter(1, entidad);
			qestablecimiento.setParameter(2, codestablecimiento);
			List<Establecimiento>listaEstablecimiento=qestablecimiento.getResultList();
			if(!listaEstablecimiento.isEmpty()){
				Establecimiento establecimiento=em.find(Establecimiento.class, listaEstablecimiento.get(0).getId());
				Query qPuntoVenta=em.createQuery("select p from PuntoVenta p where p.establecimiento=?1 and p.codigoPuntoVenta=?2");
				qPuntoVenta.setParameter(1, establecimiento);
				qPuntoVenta.setParameter(2, codpuntoVenta);
				List<PuntoVenta>listaPuntoVenta=qPuntoVenta.getResultList();
				if(!listaPuntoVenta.isEmpty()){
					PuntoVenta puntoVenta=em.find(PuntoVenta.class,listaPuntoVenta.get(0).getId());
					puntoVenta.setSecuenciaFacturacion(Long.valueOf(secuencia));
				}
			}
		}
	}
	public void estableceSecuenciaNotaCredito(String rucEmisor,String codestablecimiento,String codpuntoVenta,String secuencia,boolean enProduccion){
		Query q=em.createQuery("select e from Entidad e where e.ruc=?1");
		q.setParameter(1, rucEmisor);
		List<Entidad>listaEntidad=q.getResultList();
		if(!listaEntidad.isEmpty()){
			Entidad entidad=em.find(Entidad.class, listaEntidad.get(0).getId());
			entidad.setNotaCreditoEnPruebas(!enProduccion);
			Query qestablecimiento=em.createQuery("select e from Establecimiento e where e.entidad=?1 and e.codigo=?2");
			qestablecimiento.setParameter(1, entidad);
			qestablecimiento.setParameter(2, codestablecimiento);
			List<Establecimiento>listaEstablecimiento=qestablecimiento.getResultList();
			if(!listaEstablecimiento.isEmpty()){
				Establecimiento establecimiento=em.find(Establecimiento.class, listaEstablecimiento.get(0).getId());
				Query qPuntoVenta=em.createQuery("select p from PuntoVenta p where p.establecimiento=?1 and p.codigoPuntoVenta=?2");
				qPuntoVenta.setParameter(1, establecimiento);
				qPuntoVenta.setParameter(2, codpuntoVenta);
				List<PuntoVenta>listaPuntoVenta=qPuntoVenta.getResultList();
				if(!listaPuntoVenta.isEmpty()){
					PuntoVenta puntoVenta=em.find(PuntoVenta.class,listaPuntoVenta.get(0).getId());
					puntoVenta.setSecuenciaNotaCredito(Long.valueOf(secuencia));
				}
			}
		}
	}
	public void estableceSecuenciaGuiaRenmision(String rucEmisor,String codestablecimiento,String codpuntoVenta,String secuencia,boolean enProduccion){
		Query q=em.createQuery("select e from Entidad e where e.ruc=?1");
		q.setParameter(1, rucEmisor);
		List<Entidad>listaEntidad=q.getResultList();
		if(!listaEntidad.isEmpty()){
			Entidad entidad=em.find(Entidad.class, listaEntidad.get(0).getId());
			entidad.setGuiaRemisionEnPruebas(!enProduccion);
			Query qestablecimiento=em.createQuery("select e from Establecimiento e where e.entidad=?1 and e.codigo=?2");
			qestablecimiento.setParameter(1, entidad);
			qestablecimiento.setParameter(2, codestablecimiento);
			List<Establecimiento>listaEstablecimiento=qestablecimiento.getResultList();
			if(!listaEstablecimiento.isEmpty()){
				Establecimiento establecimiento=em.find(Establecimiento.class, listaEstablecimiento.get(0).getId());
				Query qPuntoVenta=em.createQuery("select p from PuntoVenta p where p.establecimiento=?1 and p.codigoPuntoVenta=?2");
				qPuntoVenta.setParameter(1, establecimiento);
				qPuntoVenta.setParameter(2, codpuntoVenta);
				List<PuntoVenta>listaPuntoVenta=qPuntoVenta.getResultList();
				if(!listaPuntoVenta.isEmpty()){
					PuntoVenta puntoVenta=em.find(PuntoVenta.class,listaPuntoVenta.get(0).getId());
					puntoVenta.setSecuenciaGuiaRemision(Long.valueOf(secuencia));
				}
			}
		}
	}
	public void estableceSecuenciaRetencion(String rucEmisor,String codestablecimiento,String codpuntoVenta,String secuencia,boolean enProduccion){
		Query q=em.createQuery("select e from Entidad e where e.ruc=?1");
		q.setParameter(1, rucEmisor);
		List<Entidad>listaEntidad=q.getResultList();
		if(!listaEntidad.isEmpty()){
			Entidad entidad=em.find(Entidad.class, listaEntidad.get(0).getId());
			entidad.setComprobanteRetencionEnPruebas(!enProduccion);
			Query qestablecimiento=em.createQuery("select e from Establecimiento e where e.entidad=?1 and e.codigo=?2");
			qestablecimiento.setParameter(1, entidad);
			qestablecimiento.setParameter(2, codestablecimiento);
			List<Establecimiento>listaEstablecimiento=qestablecimiento.getResultList();
			if(!listaEstablecimiento.isEmpty()){
				Establecimiento establecimiento=em.find(Establecimiento.class, listaEstablecimiento.get(0).getId());
				Query qPuntoVenta=em.createQuery("select p from PuntoVenta p where p.establecimiento=?1 and p.codigoPuntoVenta=?2");
				qPuntoVenta.setParameter(1, establecimiento);
				qPuntoVenta.setParameter(2, codpuntoVenta);
				List<PuntoVenta>listaPuntoVenta=qPuntoVenta.getResultList();
				if(!listaPuntoVenta.isEmpty()){
					PuntoVenta puntoVenta=em.find(PuntoVenta.class,listaPuntoVenta.get(0).getId());
					puntoVenta.setSecuenciaRetencion(Long.valueOf(secuencia));
				}
			}
		}
	}
}
