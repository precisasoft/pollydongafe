package ec.com.vipsoft.ce.services.recepcionComprobantesNeutros;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.w3c.dom.Document;

import ec.com.vipsoft.ce.backend.service.GeneradorClaveAccesoPorEntidad;
import ec.com.vipsoft.ce.comprobantesNeutros.DetalleGuiaRemision;
import ec.com.vipsoft.ce.comprobantesNeutros.GuiaRemisionBinding;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;
import ec.com.vipsoft.erp.abinadi.dominio.DocumentoFirmado;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.sri.guiaremision._v1_1_0.Destinatario;
import ec.com.vipsoft.sri.guiaremision._v1_1_0.Detalle;
import ec.com.vipsoft.sri.guiaremision._v1_1_0.Detalle.DetallesAdicionales.DetAdicional;
import ec.com.vipsoft.sri.guiaremision._v1_1_0.GuiaRemision;
import ec.com.vipsoft.sri.guiaremision._v1_1_0.ObligadoContabilidad;

@Stateless
@WebService
public class ReceptorGuiaRemisionNeutra implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 560815337710340868L;
	@EJB
	private GeneradorClaveAccesoPorEntidad generadorClaveAcceso;
	@Inject
	private UtilClaveAcceso utilClaveAccesl;
	@PersistenceContext
	private EntityManager em;
	@WebMethod
	@WebResult(name = "claveAcceso")
	public String receptarGuiaRemision(GuiaRemisionBinding guiaBinding){

		String claveAcceso = generadorClaveAcceso.generarClaveAccesoComprobanteRetencion(guiaBinding.getRucEmisor(), guiaBinding.getCodigoEstablecimiento(), guiaBinding.getCodigoPuntoVenta());        
		String rucEmisor = guiaBinding.getRucEmisor();
		String puntoEmision=utilClaveAccesl.obtenerCodigoPuntoEmision(claveAcceso);
		String establecimiento=utilClaveAccesl.obtenerCodigoEstablecimiento(claveAcceso);
		String secuenciaDocumento=utilClaveAccesl.obtenerSecuanciaDocumento(claveAcceso);
		String ambiente=utilClaveAccesl.obtenerAmbiente(claveAcceso);
		String codigoDocumento=utilClaveAccesl.obtenerTipoDocumento(claveAcceso);
		Query q = em.createQuery("select e from Entidad e where e.ruc=?1 and e.habilitado=?2");
		q.setParameter(1, rucEmisor);
		q.setParameter(2, Boolean.TRUE);
		List<Entidad> lista = q.getResultList();
		if (!lista.isEmpty()) {
			Entidad entidad=em.find(Entidad.class, lista.get(0).getId());
			GuiaRemision comprobante=new GuiaRemision();
			comprobante.getInfoTributaria().setRuc(rucEmisor);
			comprobante.getInfoTributaria().setAmbiente(ambiente);
			comprobante.getInfoTributaria().setClaveAcceso(claveAcceso);
			comprobante.getInfoTributaria().setCodDoc(codigoDocumento);
			comprobante.getInfoTributaria().setDirMatriz(entidad.getDireccionMatriz());
			comprobante.getInfoTributaria().setEstab(establecimiento);
			comprobante.getInfoTributaria().setNombreComercial(entidad.getNombreComercial());
			comprobante.getInfoTributaria().setPtoEmi(puntoEmision);
			comprobante.getInfoTributaria().setRazonSocial(entidad.getRazonSocial());
			comprobante.getInfoTributaria().setSecuencial(secuenciaDocumento);
			if (utilClaveAccesl.esEnContingencia(claveAcceso)) {
				comprobante.getInfoTributaria().setTipoEmision("2");
			} else {
				comprobante.getInfoTributaria().setTipoEmision("1");
			}
			////////////////////////////////////////////////////////////////////////////////
			if(entidad.isObligadoContabilidad()){
				comprobante.getInfoGuiaRemision().setObligadoContabilidad(ObligadoContabilidad.SI);
				if(entidad.getResolucionContribuyenteEspecial()!=null){
					comprobante.getInfoGuiaRemision().setContribuyenteEspecial(entidad.getResolucionContribuyenteEspecial());
				}                           
			}else{
				comprobante.getInfoGuiaRemision().setObligadoContabilidad(ObligadoContabilidad.NO);
			}
			comprobante.getInfoGuiaRemision().setDirEstablecimiento(entidad.getDireccionMatriz());
			comprobante.getInfoGuiaRemision().setFechaIniTransporte(guiaBinding.getFechaInicioTexto());
			comprobante.getInfoGuiaRemision().setFechaFinTransporte(guiaBinding.getFechaFinTexto());
			comprobante.getInfoGuiaRemision().setRucTransportista(guiaBinding.getIdentifcacionTransportista());
			comprobante.getInfoGuiaRemision().setRazonSocialTransportista(guiaBinding.getRazonSocialTransportista());
			comprobante.getInfoGuiaRemision().setPlaca(guiaBinding.getPlaca());
			comprobante.getInfoGuiaRemision().setDirPartida(guiaBinding.getDireccionPartida());					
			comprobante.getInfoGuiaRemision().setTipoIdentificacionTransportista(guiaBinding.getCodigoTipoIdentificacionBeneficiario());
			
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////7
			//////////////////////////////7
			for (ec.com.vipsoft.ce.comprobantesNeutros.Destinatario d: guiaBinding.getDestinatarios()){
				Destinatario destinatario=new Destinatario();
				destinatario.setCodDocSustento(d.getCodigoDocumento());
				destinatario.setCodEstabDestino(d.getCodigoEstablecimientoDestino());
				destinatario.setDirDestinatario(d.getDireccionDestinatario());
				destinatario.setFechaEmisionDocSustento(d.getFechaEmisionTexto());
				destinatario.setIdentificacionDestinatario(d.getIdentificacionDestinatario());
				destinatario.setMotivoTraslado(d.getMotivoTraslado());
				destinatario.setNumAutDocSustento(d.getNumeroAutorizacion());
				destinatario.setNumDocSustento(d.getNumeroDocumento());
				destinatario.setRazonSocialDestinatario(d.getRazonSocialDestinatario());
				destinatario.setRuta(d.getRuta());
				for(DetalleGuiaRemision dd : d.getDetalles()){
					Detalle detalle=new Detalle();
					detalle.setCantidad(dd.getCantidad());
					detalle.setCodigoInterno(dd.getCodigoInterno());
					detalle.setDescripcion(dd.getDescripcion());
					Iterator<Entry<String, String>> iterador = dd.getInfoAdicional().entrySet().iterator();
					while(iterador.hasNext()){
						Entry<String, String> entry = iterador.next();
						DetAdicional daicional=new DetAdicional();
						daicional.setNombre(entry.getKey());
						daicional.setValor(entry.getValue());
						detalle.getDetallesAdicionales().getDetAdicional().add(daicional);	
					}
					destinatario.getDetalles().getDetalle().add(detalle);
				}
				comprobante.getDestinatarios().getDestinatario().add(destinatario);
			}
			
			
			


			//////////////////////////////
			try {
				Document convertidoEnDOM = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
				JAXBContext contexto= JAXBContext.newInstance(GuiaRemision.class);
				Marshaller marshaller = contexto.createMarshaller();
				marshaller.marshal(comprobante, convertidoEnDOM);
				Map<String, Object> parametros = new HashMap<>();
				// parametros.put("documentoOriginal",factura);
				parametros.put("documentoAFirmar", convertidoEnDOM);
				parametros.put("rucEmisor", rucEmisor);
				parametros.put("establecimiento",guiaBinding.getCodigoEstablecimiento());
				parametros.put("codigoPuntoVenta",guiaBinding.getCodigoPuntoVenta());
				parametros.put("archivop12", entidad.getArchivop12());
				parametros.put("contrasena",entidad.getPasswordp12());
				parametros.put("documentoAFirmar", convertidoEnDOM);
				parametros.put("enPruebas", utilClaveAccesl.esEnPruebas(claveAcceso));
				ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
				RuntimeService runtimeService = processEngine.getRuntimeService();
				ProcessInstance proccessInstance = runtimeService.startProcessInstanceByKey("procesoEnvio", parametros);
				// proccessInstance.getProcessVariables().get("")
				ComprobanteElectronico comprobanteAPersistir = new ComprobanteElectronico();
				comprobanteAPersistir.setClaveAcceso(claveAcceso);
				comprobanteAPersistir.setPuntoEMision(puntoEmision);
				comprobanteAPersistir.setEstablecimiento(establecimiento);
				comprobanteAPersistir.setSecuencia(secuenciaDocumento);
				comprobanteAPersistir.setAutorizado(false);
				boolean enviado = (boolean) proccessInstance.getProcessVariables().get("enviado");
				comprobanteAPersistir.setEnviado(enviado);
				if (!enviado) {
					comprobanteAPersistir.setCodigoError((String) proccessInstance.getProcessVariables().get("codigoError"));
					comprobanteAPersistir.setMensajeError((String) proccessInstance.getProcessVariables().get("mensajeError"));
					DocumentoFirmado documentoFi = new DocumentoFirmado();
					documentoFi.setConvertidoEnXML((String) proccessInstance.getProcessVariables().get("documentoFirmado"));
					comprobanteAPersistir.setDocumentoFirmado(documentoFi);
				}
				comprobanteAPersistir.setProcessId(proccessInstance.getProcessInstanceId());
				em.persist(comprobanteAPersistir);
			} catch (JAXBException ex) {
				Logger.getLogger(ReceptorFacturaNeutra.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}           
		}

		return claveAcceso;
	}
}
