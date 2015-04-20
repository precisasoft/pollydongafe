package ec.com.vipsoft.ce.backend.service;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.com.vipsoft.ce.ui.ComprobanteEmitido;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico.TipoComprobante;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
@Stateless
public class ListarComprobantesEmitidos {
	public ListarComprobantesEmitidos() {
		mapaErrores.put("45","secuencial registrado");
		mapaErrores.put("46", "RUC no existe");
		mapaErrores.put("43", "Clave de acceso registrada");
		mapaErrores.put("35", "documento xml inválido");
		mapaErrores.put("50", "Error general interno del sri");
		mapaErrores.put("52", "Error en diferencias");
		mapaErrores.put("56", "Establecimiento cerrado");
		mapaErrores.put("70", "Clave de acceso en proceso");
		mapaErrores.put("59", "Identificación del cliente no existe");
	}
	
		
	
	
	private  Map<String,String>mapaErrores=new HashMap<>();
	
	@PersistenceContext
	private EntityManager em;
	public Set<ComprobanteEmitido>listarSiguientes(String rucEmisor,Long idMinimo){		
		TreeSet<ComprobanteEmitido>listadoRetorno=new TreeSet<>();
		Query qentidad=em.createQuery("select e from Entidad e where e.ruc=?1");
		qentidad.setParameter(1, rucEmisor);
		List<Entidad>listadoEntidad=qentidad.getResultList();
		if(!listadoEntidad.isEmpty()){
			GregorianCalendar ahora=new GregorianCalendar();
			ahora.add(GregorianCalendar.DAY_OF_MONTH, -60);
			Entidad entidad=em.getReference(Entidad.class, listadoEntidad.get(0).getId());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Query q=em.createQuery("select c from ComprobanteElectronico c where c.entidadEmisora=?1   and c.fechaRegistro>=?2 order by c.id desc");
			q.setMaxResults(4000);
			//q.setParameter(1, idMinimo);
			q.setParameter(1, entidad);
			q.setParameter(2, ahora.getTime());
			List<ComprobanteElectronico>listadoComprobante=q.getResultList();
			
			for(ComprobanteElectronico c:listadoComprobante){
				ComprobanteEmitido bean=new ComprobanteEmitido();			
				bean.setClaveAcceso(c.getClaveAcceso());
				bean.setNumeroDocumento(c.getPuntoEMision()+"-"+c.getPuntoEMision()+"-"+c.getSecuencia());
				if(c.getCodigoError()!=null){
					if(c.getCodigoError()!=null){
						StringBuilder sberror=new StringBuilder("(");
						sberror.append(c.getCodigoError());
						sberror.append(") ");
						if(mapaErrores.containsKey(String.valueOf(c.getCodigoError()))){
							sberror.append(mapaErrores.get(c.getCodigoError()));	
						}
						bean.setNota(sberror.toString());
						
					}
					
				}
				if(c.getFechaAutorizacion()!=null){
					bean.setFechaAutorizacion(sdf.format(c.getFechaAutorizacion()));	
				}	
				if(c.getFechaEnvio()!=null){
					bean.setFechaEmision(sdf.format(c.getFechaEnvio()));	
				}
				
				bean.setId(c.getId());
				if(c.getNumeroAutorizacion()!=null){
					bean.setNumeroAutorizacion(c.getNumeroAutorizacion());	
				}
				if(c.getTipo().equals(TipoComprobante.factura)){
					bean.setTipo("FACTURA");
				}
				if(c.getTipo().equals(TipoComprobante.notaCredito)){
					bean.setTipo("N/C");
				}
				if(c.getTipo().equals(TipoComprobante.notaDebito)){
					bean.setTipo("N/D");
				}
				if(c.getTipo().equals(TipoComprobante.guiaRemision)){
					bean.setTipo("GR");
				}
				if(c.getTipo().equals(TipoComprobante.retencion)){
					bean.setTipo("RETENCION");
				}
				listadoRetorno.add(bean);
			}	
		}
		
		return listadoRetorno;		
	}
	public Set<ComprobanteEmitido> listarCompronbantesDeBeneficiario(String name) {
		TreeSet<ComprobanteEmitido>listadoRetorno=new TreeSet<>();
		GregorianCalendar ahora=new GregorianCalendar();
		ahora.add(GregorianCalendar.MONTH, -4);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Query q=em.createQuery("select c from ComprobanteElectronico c where c.identificacionBeneficiario=?1 and c.autorizado=?2   order by c.id desc");
		q.setMaxResults(2000);
		//q.setParameter(1, idMinimo);
		q.setParameter(1, name);
		q.setParameter(2, Boolean.TRUE);
		//q.setParameter(3, ahora.getTime());
		List<ComprobanteElectronico>listadoComprobante=q.getResultList();
		
		for(ComprobanteElectronico c:listadoComprobante){
			ComprobanteEmitido bean=new ComprobanteEmitido();			
			bean.setClaveAcceso(c.getClaveAcceso());
			bean.setNumeroDocumento(c.getPuntoEMision()+"-"+c.getPuntoEMision()+"-"+c.getSecuencia());
			if(c.getCodigoError()!=null){
				if(c.getCodigoError()!=null){
					StringBuilder sberror=new StringBuilder("(");
					sberror.append(c.getCodigoError());
					sberror.append(") ");
					if(mapaErrores.containsKey(String.valueOf(c.getCodigoError()))){
						sberror.append(mapaErrores.get(c.getCodigoError()));	
					}
					bean.setNota(sberror.toString());
					
				}
				
			}
			if(c.getFechaAutorizacion()!=null){
				bean.setFechaAutorizacion(sdf.format(c.getFechaAutorizacion()));	
			}	
			if(c.getFechaEnvio()!=null){
				bean.setFechaEmision(sdf.format(c.getFechaEnvio()));	
			}
			
			bean.setId(c.getId());
			if(c.getNumeroAutorizacion()!=null){
				bean.setNumeroAutorizacion(c.getNumeroAutorizacion());	
			}
			if(c.getTipo().equals(TipoComprobante.factura)){
				bean.setTipo("FACTURA");
			}
			if(c.getTipo().equals(TipoComprobante.notaCredito)){
				bean.setTipo("N/C");
			}
			if(c.getTipo().equals(TipoComprobante.notaDebito)){
				bean.setTipo("N/D");
			}
			if(c.getTipo().equals(TipoComprobante.guiaRemision)){
				bean.setTipo("GR");
			}
			if(c.getTipo().equals(TipoComprobante.retencion)){
				bean.setTipo("RETENCION");
			}
			listadoRetorno.add(bean);
		}	
		return listadoRetorno;
	}


}
