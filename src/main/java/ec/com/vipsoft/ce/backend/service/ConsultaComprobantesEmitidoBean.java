package ec.com.vipsoft.ce.backend.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.com.vipsoft.ce.backend.remoteinterface.ConsultaComprobantesEmitido;
import ec.com.vipsoft.ce.utils.LlenadorNumeroComprobante;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.erp.abinadi.dominio.ReporteComprobanteElectronico;


@WebService
@LocalBean
@Stateless
public class ConsultaComprobantesEmitidoBean implements ConsultaComprobantesEmitido {

	@PersistenceContext
	private EntityManager em;
	@Inject
	private LlenadorNumeroComprobante llenadorNumeroComprobante;
	@Inject
	private UtilClaveAcceso utilClaveAcceso;
	
	
	private List<ReporteComprobanteElectronico> consulta(String rucEmisorEncriptado,String establecimiento_puntoventa_secuencia){
		
		Query queryEntidad=em.createQuery("select e from Entidad e where e.ruc=?1");
		queryEntidad.setParameter(1, rucEmisorEncriptado);
		List<Entidad>listaEntidad=queryEntidad.getResultList();
		List<ReporteComprobanteElectronico>listadoRetorno=new ArrayList<ReporteComprobanteElectronico>();
		if(!listaEntidad.isEmpty()){
			Entidad entidad=listaEntidad.get(0);
			String numeroComprobante=llenadorNumeroComprobante.llenarNumeroDocumento(establecimiento_puntoventa_secuencia);
			String establecimiento=llenadorNumeroComprobante.obtenerSucursal(numeroComprobante);
			String puntoVenta=llenadorNumeroComprobante.obtenerPuntoEmision(numeroComprobante);
			String secuencia=llenadorNumeroComprobante.obtenerSecuencia(numeroComprobante);
			Query qcomprobante=em.createQuery("select c from ComprobanteElectronico c where c.entidadEmisora=?1 and c.establecimiento=?2 and c.puntoEMision=?3 and c.secuencia=?4");
			qcomprobante.setParameter(1, entidad);
			qcomprobante.setParameter(2, establecimiento);
			qcomprobante.setParameter(3, puntoVenta);
			qcomprobante.setParameter(4, secuencia);
			List<ComprobanteElectronico> resultList = qcomprobante.getResultList();
			for(ComprobanteElectronico c:resultList){
				ReporteComprobanteElectronico bean=new ReporteComprobanteElectronico();
				bean.setAutorizacion(c.getNumeroAutorizacion());
				bean.setClaveAcceso(c.getClaveAcceso());
				
				if(c.getClaveAcceso()!=null){
					bean.setEnProduccion((!utilClaveAcceso.esEnPruebas(c.getClaveAcceso())));	
				}else{
					bean.setEnProduccion(true);	
				}
				
				if(utilClaveAcceso.esEnContingencia(c.getClaveAcceso())){
					bean.setContingencia(true);
					bean.setFecha(c.getFechaAutorizacion());
				}else{
					bean.setContingencia(false);
				}
				if(c.getNumeroAutorizacion()!=null){
					bean.setEnviadoSRI(true);
				}else{
					if(c.isEnviado()){
						bean.setEnviadoSRI(true);
						bean.setFecha(c.getFechaEnvio());
						if(c.getCodigoError()!=null){
							bean.setCodigoError(Integer.valueOf(c.getCodigoError()));
						}
					}else{
						bean.setEnviadoSRI(false);
						bean.setFecha(c.getFechaRegistro());
						if((c.getCodigoError()!=null)&&(c.getCodigoError().length()>0)){
							bean.setCodigoError(Integer.valueOf(c.getCodigoError()));
						}
					}
				}
				bean.setNumeroDocumento(numeroComprobante);
				listadoRetorno.add(bean);
				
			}			
		}
		
		return listadoRetorno;
	}
	@Override	
	public ReporteComprobanteElectronico consultaFactura(String rucEmisorEncriptado,String establecimiento_puntoventa_secuencia) {
		ReporteComprobanteElectronico retorno=null;
		List<ReporteComprobanteElectronico> listadoRetorno=consulta(rucEmisorEncriptado, establecimiento_puntoventa_secuencia);
		for(ReporteComprobanteElectronico c:listadoRetorno){
			if(utilClaveAcceso.obtenerTipoDocumento(c.getClaveAcceso()).equalsIgnoreCase("01")){
				retorno=c;
				break;
			}
		}
		return retorno;
	}	

	@Override
	public ReporteComprobanteElectronico consultaNC(String rucEmisorEncriptado,	String establecimiento_puntoventa_secuencia) {
		ReporteComprobanteElectronico retorno=null;
		List<ReporteComprobanteElectronico> listadoRetorno=consulta(rucEmisorEncriptado, establecimiento_puntoventa_secuencia);
		for(ReporteComprobanteElectronico c:listadoRetorno){
			if(utilClaveAcceso.obtenerTipoDocumento(c.getClaveAcceso()).equalsIgnoreCase("04")){
				retorno=c;
				break;
			}
		}
		return retorno;
	}

	@Override
	public ReporteComprobanteElectronico consultaGuiaRemision(String rucEmisorEncriptado,String establecimiento_puntoventa_secuencia) {
		ReporteComprobanteElectronico retorno=null;
		List<ReporteComprobanteElectronico> listadoRetorno=consulta(rucEmisorEncriptado, establecimiento_puntoventa_secuencia);
		for(ReporteComprobanteElectronico c:listadoRetorno){
			if(utilClaveAcceso.obtenerTipoDocumento(c.getClaveAcceso()).equalsIgnoreCase("06")){
				retorno=c;
				break;
			}
		}
		return retorno;
	}

	@Override
	public ReporteComprobanteElectronico consultaComprobanteRetencion(String rucEmisorEncriptado,String establecimiento_puntoventa_secuencia) {
		ReporteComprobanteElectronico retorno=null;
		List<ReporteComprobanteElectronico> listadoRetorno=consulta(rucEmisorEncriptado, establecimiento_puntoventa_secuencia);
		for(ReporteComprobanteElectronico c:listadoRetorno){
			if(utilClaveAcceso.obtenerTipoDocumento(c.getClaveAcceso()).equalsIgnoreCase("07")){
				retorno=c;
				break;
			}
		}
		return retorno;
	}

}
