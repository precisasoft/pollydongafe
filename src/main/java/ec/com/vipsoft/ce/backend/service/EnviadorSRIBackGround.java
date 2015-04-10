package ec.com.vipsoft.ce.backend.service;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.com.vipsoft.ce.services.recepcionComprobantesNeutros.EnviadorSRIEJB;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;
import ec.com.vipsoft.erp.abinadi.procesos.RespuestaRecepcionDocumento;

@Stateless
public class EnviadorSRIBackGround {
	@PersistenceContext
	private EntityManager em;
	@EJB
	private EnviadorSRIEJB enviadorSRI;
	@EJB
	private VerificadorIndisponibilidad verificadorIndisponibilidad;
	@Inject
	private UtilClaveAcceso utilClaveAcceso;
	
	@Schedule(dayOfMonth="*",hour="*",minute="*",year="*",month="*",second="0,10,20,30,40,50")
	public void enviarAlSRIComprobantesXMLPendientes(){
		if(!verificadorIndisponibilidad.estamosEnContingencia()){
			Query q=em.createQuery("select c from ComprobanteElectronico c where c.enviado=?1 and c.codigoError is null order by c.id asc");
			q.setParameter(1, Boolean.FALSE);
			List<ComprobanteElectronico>listaComprobantes=q.getResultList();
			if(!listaComprobantes.isEmpty()){
				for(ComprobanteElectronico c:listaComprobantes){
					
					RespuestaRecepcionDocumento enviarComprobanteAlSRI = enviadorSRI.enviarComprobanteAlSRI(c.getDocumentoFirmado().getConvertidoEnXML(),utilClaveAcceso.esEnPruebas(c.getClaveAcceso()));
					if(enviarComprobanteAlSRI!=null){
						ComprobanteElectronico _c=em.find(ComprobanteElectronico.class, c.getId());
						if(enviarComprobanteAlSRI.getEstado().equalsIgnoreCase("RECIBIDA")){
							_c.setEnviado(true);
							_c.setFechaEnvio(new Date());
						}else{
							if(!enviarComprobanteAlSRI.getDetalle().isEmpty()){
								//error generar del sri ... es como mandarlos a contingencia por unos segundos
								if(enviarComprobanteAlSRI.getDetalle().get(0).getCodigo().equalsIgnoreCase("50")){
									verificadorIndisponibilidad.darUnToqueIndisponibilidad();
								}else{
									_c.setCodigoError(enviarComprobanteAlSRI.getDetalle().get(0).getCodigo());
									_c.setMensajeError(enviarComprobanteAlSRI.getDetalle().get(0).getMensaje());
									_c.setFechaEnvio(new Date());
								}
								
							}
							
						}
					}
				}
			}	
		}							
	}
}
