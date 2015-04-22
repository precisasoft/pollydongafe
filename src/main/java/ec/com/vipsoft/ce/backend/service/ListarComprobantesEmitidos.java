package ec.com.vipsoft.ce.backend.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.com.vipsoft.ce.ui.ComprobanteEmitido;
import ec.com.vipsoft.ce.utils.LlenadorNumeroComprobante;
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
	
	@Inject
	private LlenadorNumeroComprobante llenadorNumeroComprobante;	
	
	
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
				bean.setId(c.getId());
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
	public Set<ComprobanteEmitido> buscarComprobnate(String rucEmisor,	String tipoComprobante, String numeroDocumento_buscar) {
		TreeSet<ComprobanteEmitido>listadoRetorno=new TreeSet<>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Query qentidad=em.createQuery("select e from Entidad e where e.ruc=?1");
		qentidad.setParameter(1, rucEmisor);
		List<Entidad>listadoEntidad=qentidad.getResultList();
		if(!listadoEntidad.isEmpty()){
			Entidad entidad=em.getReference(Entidad.class, listadoEntidad.get(0).getId());
			Query q=em.createQuery("select c from ComprobanteElectronico c where c.entidadEmisora=?1   and c.tipo=?2 and c.establecimiento=?3 and c.puntoEMision=?4 and c.secuencia=?5");			
			q.setMaxResults(4);
			//q.setParameter(1, idMinimo);
			q.setParameter(1, entidad);
			if(tipoComprobante.equalsIgnoreCase("factura")){
				q.setParameter(2, TipoComprobante.factura);
				//factura, guiaRemision, notaCredito, notaDebito, retencion;
			}
			if(tipoComprobante.equalsIgnoreCase("NC")){
				q.setParameter(2, TipoComprobante.notaCredito);			
			}
			if(tipoComprobante.equalsIgnoreCase("GR")){
				q.setParameter(2, TipoComprobante.guiaRemision);			
			}
			if(tipoComprobante.equalsIgnoreCase("CR")){
				q.setParameter(2, TipoComprobante.retencion);			
			}
			q.setParameter(3,llenadorNumeroComprobante.obtenerPuntoEmision(numeroDocumento_buscar));
			q.setParameter(4, llenadorNumeroComprobante.obtenerPuntoEmision(numeroDocumento_buscar));
			q.setParameter(5, llenadorNumeroComprobante.obtenerSecuencia(numeroDocumento_buscar));
			List<ComprobanteElectronico>listadoComprobante=q.getResultList();
			if(!listadoComprobante.isEmpty()){
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
		}
		return listadoRetorno;
	}
	public Set<ComprobanteEmitido> buscarComprobnate(String rucEmisor, String tipoComprobante, Date fecha) {
		TreeSet<ComprobanteEmitido>listadoRetorno=new TreeSet<>();
		LocalTime time1=LocalTime.of(0, 0);
		LocalTime time2=LocalTime.of(23, 59,59,999999);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Query qentidad=em.createQuery("select e from Entidad e where e.ruc=?1");
		qentidad.setParameter(1, rucEmisor);
		List<Entidad>listadoEntidad=qentidad.getResultList();
		if(!listadoEntidad.isEmpty()){
			Entidad entidad=em.getReference(Entidad.class, listadoEntidad.get(0).getId());
			Query q=em.createQuery("select c from ComprobanteElectronico c where c.entidadEmisora=?1   and c.tipo=?2 and c.fechaEnvio>=?3 and c.fechaEnvio<?4");			
			q.setMaxResults(4);
			//q.setParameter(1, idMinimo);
			q.setParameter(1, entidad);
			if(tipoComprobante.equalsIgnoreCase("factura")){
				q.setParameter(2, TipoComprobante.factura);
				//factura, guiaRemision, notaCredito, notaDebito, retencion;
			}
			if(tipoComprobante.equalsIgnoreCase("NC")){
				q.setParameter(2, TipoComprobante.notaCredito);			
			}
			if(tipoComprobante.equalsIgnoreCase("GR")){
				q.setParameter(2, TipoComprobante.guiaRemision);			
			}
			if(tipoComprobante.equalsIgnoreCase("CR")){
				q.setParameter(2, TipoComprobante.retencion);			
			}
			LocalDateTime inicioDia=LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault()).withHour(0).withMinute(0).withSecond(0);
			Date horaInicio=Date.from(inicioDia.atZone(ZoneId.systemDefault()).toInstant());			
			q.setParameter(3, horaInicio);
			LocalDateTime finDia=LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59).withNano(999999998);
			Date horaFina=Date.from(finDia.atZone(ZoneId.systemDefault()).toInstant());			
			q.setParameter(4, horaFina);
			List<ComprobanteElectronico>listadoComprobante=q.getResultList();
			if(!listadoComprobante.isEmpty()){
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
			
		}
		return listadoRetorno;
	}


}
