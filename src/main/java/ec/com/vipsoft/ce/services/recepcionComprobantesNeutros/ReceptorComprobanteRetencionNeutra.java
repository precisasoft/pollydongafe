/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.vipsoft.ce.services.recepcionComprobantesNeutros;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import ec.com.vipsoft.ce.backend.service.GeneradorClaveAccesoPorEntidad;
import ec.com.vipsoft.ce.comprobantesNeutros.ComprobanteRetencionBinding;
import ec.com.vipsoft.ce.comprobantesNeutros.ImpuestoRetencion;
import ec.com.vipsoft.ce.utils.LlenadorNumeroComprobante;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico.TipoComprobante;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.sri.comprobanteRetencion._v1_0.ComprobanteRetencion;
import ec.com.vipsoft.sri.comprobanteRetencion._v1_0.ComprobanteRetencion.InfoAdicional.CampoAdicional;
import ec.com.vipsoft.sri.comprobanteRetencion._v1_0.Impuesto;
import ec.com.vipsoft.sri.comprobanteRetencion._v1_0.ObligadoContabilidad;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.firmaJava.role.SimpleClaimedRole;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.IPassStoreKS;
import es.mityc.javasign.pkstore.keystore.KSStore;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;

/**
 *
 * @author chrisvv
 */
@Stateless
@WebService
public class ReceptorComprobanteRetencionNeutra {            
	@EJB
	private GeneradorClaveAccesoPorEntidad generadorClaveAcceso;
	@Inject
	private UtilClaveAcceso utilClaveAccesl;
	@PersistenceContext
	private EntityManager em;
	@EJB
	private ProcesoEnvioEJB procesoEnvio;
	@Inject 
	private LlenadorNumeroComprobante LlenadorNumeroComprobante;
	@WebMethod
	@WebResult(name = "claveAcceso")
	public String receptarComprobanteRetencion(	@WebParam(name = "retencion") ComprobanteRetencionBinding retencion) {
		String motivoRechazo = null;
		boolean correcto=true;
		String retorno=null;
		//continuar porque se ha validado inicialmente
		String rucEmisor = retencion.getRucEmisor();
		String puntoEmision = retencion.getCodigoPuntoVenta();
		String establecimiento = retencion.getCodigoEstablecimiento();		
		Query q = em.createQuery("select e from Entidad e where e.ruc=?1 and e.habilitado=?2");
		q.setParameter(1, rucEmisor);
		q.setParameter(2, Boolean.TRUE);
		@SuppressWarnings("unchecked")
		List<Entidad> lista = q.getResultList();
		if (!lista.isEmpty()) {
			
			Entidad entidad = em.find(Entidad.class, lista.get(0).getId());
			ComprobanteRetencion comprobanteRetencion = new ComprobanteRetencion();
			comprobanteRetencion.setId("comprobante");
			comprobanteRetencion.setVersion("1.0.0");
			comprobanteRetencion.getInfoTributaria().setRuc(rucEmisor);
			
			comprobanteRetencion.getInfoTributaria().setDirMatriz(entidad.getDireccionMatriz());
			comprobanteRetencion.getInfoTributaria().setEstab(establecimiento);
			comprobanteRetencion.getInfoTributaria().setNombreComercial(entidad.getNombreComercial());
			comprobanteRetencion.getInfoTributaria().setPtoEmi(puntoEmision);
			comprobanteRetencion.getInfoTributaria().setRazonSocial(entidad.getRazonSocial());
			
			// //////////////////////////////////////////////////////////////////////////////
			if (entidad.isObligadoContabilidad()) {
				comprobanteRetencion.getInfoCompRetencion().setObligadoContabilidad(ObligadoContabilidad.SI);
				if (entidad.getResolucionContribuyenteEspecial() != null) {
					comprobanteRetencion.getInfoCompRetencion().setContribuyenteEspecial(entidad.getResolucionContribuyenteEspecial());
				}
			} else {
				comprobanteRetencion.getInfoCompRetencion().setObligadoContabilidad(ObligadoContabilidad.NO);
			}
			comprobanteRetencion.getInfoCompRetencion().setDirEstablecimiento(entidad.getDireccionMatriz());
			comprobanteRetencion.getInfoCompRetencion().setFechaEmision(retencion.getFechaEmisionTexto());
			comprobanteRetencion.getInfoCompRetencion().setIdentificacionSujetoRetenido(retencion.getIdentificacionBeneficiario());
			comprobanteRetencion.getInfoCompRetencion().setTipoIdentificacionSujetoRetenido(retencion.getCodigoTipoIdentificacionBeneficiario());
			comprobanteRetencion.getInfoCompRetencion().setPeriodoFiscal(retencion.getPeriodo());
			comprobanteRetencion.getInfoCompRetencion().setRazonSocialSujetoRetenido(retencion.getRazonSocialBeneficiario());
			comprobanteRetencion.getInfoCompRetencion().setTipoIdentificacionSujetoRetenido(retencion.getCodigoTipoIdentificacionBeneficiario());
			// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////7
			// ////////////////////////////7
			BigDecimal totalRetenido=new BigDecimal("0.00");
			for (ImpuestoRetencion d : retencion.getImpuestos()) {
				Impuesto impuesto = new Impuesto();
				impuesto.setBaseImponible(d.getBaseImponible());
				impuesto.setCodDocSustento(d.getTipoComprobante().toString());
				if((d.getCodigo().equalsIgnoreCase("1")||(d.getCodigo().equalsIgnoreCase("2"))||(d.getCodigo().equalsIgnoreCase("6")))){
					impuesto.setCodigo(d.getCodigo());	
				}else{
					correcto=false;
					motivoRechazo="codigo en detalle es incorrecto ...debe ser 1  2 o 6";
				}
				
				impuesto.setCodigoRetencion(d.getCodigoRetention().toUpperCase());
				impuesto.setFechaEmisionDocSustento(d.getFechaEmisionDocumentoSustentoTexTo());
				String numeroComprobante=d.getNumeroDocumento();
				if(numeroComprobante.length()!=15){
					numeroComprobante=LlenadorNumeroComprobante.llenarNumeroDocumento(numeroComprobante).replace("-", "");
				}
				impuesto.setNumDocSustento(numeroComprobante);				
				impuesto.setPorcentajeRetener(d.getPorcentajeRetencion().setScale(2, RoundingMode.HALF_UP));
				if((impuesto.getPorcentajeRetener().doubleValue()>100)||(impuesto.getPorcentajeRetener().doubleValue()<=0)){
					correcto=false;
					motivoRechazo="el porcentaje debe estar entre >0 y <=100";
				}				
				impuesto.setValorRetenido(d.getValorRetenido());
				if(impuesto.getValorRetenido().doubleValue()<=0){
					correcto=false;
					motivoRechazo="el monto retenido no puede ser <=0";
				}
				totalRetenido=totalRetenido.add(impuesto.getValorRetenido());
				comprobanteRetencion.getImpuestos().getImpuesto().add(impuesto);
			}
			
			if(correcto){
				
				
				String claveAcceso=null;
				if(retencion.getSecuenciaDocumento()!=null){
					claveAcceso=generadorClaveAcceso.generarClaveAccesoComprobanteRetencion(retencion.getRucEmisor(),retencion.getCodigoEstablecimiento(),	retencion.getCodigoPuntoVenta(),retencion.getSecuenciaDocumento());
				}else{
					claveAcceso = generadorClaveAcceso.generarClaveAccesoComprobanteRetencion(retencion.getRucEmisor(),retencion.getCodigoEstablecimiento(),	retencion.getCodigoPuntoVenta());	
				}
				String secuenciaDocumento = utilClaveAccesl.obtenerSecuanciaDocumento(claveAcceso);
				String ambiente = utilClaveAccesl.obtenerAmbiente(claveAcceso);
				String codigoDocumento = utilClaveAccesl.obtenerTipoDocumento(claveAcceso);
				comprobanteRetencion.getInfoTributaria().setAmbiente(ambiente);
				comprobanteRetencion.getInfoTributaria().setClaveAcceso(claveAcceso);
				comprobanteRetencion.getInfoTributaria().setCodDoc(codigoDocumento);
				comprobanteRetencion.getInfoTributaria().setSecuencial(secuenciaDocumento);
				if (utilClaveAccesl.esEnContingencia(claveAcceso)) {
					comprobanteRetencion.getInfoTributaria().setTipoEmision("2");
				//	comprobanteRetencion.getInfoTributaria().set
				} else {
					comprobanteRetencion.getInfoTributaria().setTipoEmision("1");
				}
				CampoAdicional catotalRetenido=new CampoAdicional();
				catotalRetenido.setNombre("Total Retenido");
				catotalRetenido.setValue("$"+totalRetenido);
				comprobanteRetencion.getInfoAdicional().getCampoAdicional().add(catotalRetenido);
				// aqui se debería probar la validez			
				/////////////////////////////////////////////////////////////////////////////////////////////////////7

				/////////////////////////////////////////////////////////////////////////////////////////////////////////			
				// ////////////////////////////
				try {
					Document convertidoEnDOM = DocumentBuilderFactory.newInstance()	.newDocumentBuilder().newDocument();
					JAXBContext contexto = JAXBContext.newInstance(ComprobanteRetencion.class);
					Marshaller marshaller = contexto.createMarshaller();
					marshaller.marshal(comprobanteRetencion, convertidoEnDOM);

					StringBuilder sbFacturaFirmadaEnTexto = new StringBuilder();
					ByteArrayInputStream in = new ByteArrayInputStream(	entidad.getArchivop12());
					KeyStore ks = KeyStore.getInstance("PKCS12");
					ks.load(in, entidad.getPasswordp12().toCharArray());
					// JAXBContext context;
					// context = JAXBContext.newInstance(Factura.class);
					// Marshaller m = context.createMarshaller();
					// m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					// Boolean.TRUE);
					// m.marshal(facturaRetorno, documentoAFirmar);
					DataToSign dataToSign = preparaDoc2Sign();
					dataToSign.addObject(new ObjectToSign(new InternObjectToSign("comprobante"), "contenido comprobante", null,	"text/xml", null));
					dataToSign.setDocument(convertidoEnDOM);
					FirmaXML firmaXml = new FirmaXML();
					// final String contrasena = entidad.getPasswordp12();
					X509Certificate certificado = (X509Certificate) ks.getCertificate(ks.aliases().nextElement());
					IPKStoreManager storeManager = new KSStore(ks,
							new IPassStoreKS() {

						@Override
						public char[] getPassword(
								X509Certificate argumento0,
								String argumento1) {
							// TODO Auto-generated method stub
							return entidad.getPasswordp12().toCharArray();
						}
					});
					PrivateKey privateKey = storeManager.getPrivateKey(certificado);
					Provider proveedor = storeManager.getProvider(certificado);
					Object[] datosfirmados = firmaXml.signFile(certificado,	dataToSign, privateKey, proveedor);
					Document documentoFirmado = (Document) datosfirmados[0];

					Source source = new DOMSource(documentoFirmado);
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					Result result = new StreamResult(out);
					TransformerFactory factory = TransformerFactory.newInstance();
					Transformer transformer = factory.newTransformer();
					transformer.transform(source, result);

					// convierte en String el firmado
					DOMSource domSource = new DOMSource(documentoFirmado);
					StringWriter writer = new StringWriter();
					StreamResult result2 = new StreamResult(writer);
				//	TransformerFactory tf = TransformerFactory.newInstance();
				//	Transformer transformer2 = tf.newTransformer();
					transformer.transform(domSource, result2);
					sbFacturaFirmadaEnTexto.append(writer.toString());

					Map<String, Object> parametros = new HashMap<>();
					// parametros.put("documentoOriginal",factura);
					parametros.put("documentoFirmado",sbFacturaFirmadaEnTexto.toString());
					parametros.put("rucEmisor", rucEmisor);
					parametros.put("establecimiento",retencion.getCodigoEstablecimiento());
					parametros.put("codigoPuntoVenta",retencion.getCodigoPuntoVenta());	
					parametros.put("enPruebas",utilClaveAccesl.esEnPruebas(claveAcceso));
					parametros.put("claveAcceso", claveAcceso);
					parametros.put("secuenciaDocumento", secuenciaDocumento);
					parametros.put("intentos", 0);
					parametros.put("maxIntentos", 5);
					parametros.put("idCliente", comprobanteRetencion.getInfoCompRetencion().getIdentificacionSujetoRetenido());
					parametros.put("tipoComprobante", TipoComprobante.retencion);
					procesoEnvio.lanzarProcesoEnvio(parametros);
					retorno=claveAcceso;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}else{
				retorno="RECHAZADO "+motivoRechazo;
			}
		}

		return retorno;
	}
	private DataToSign preparaDoc2Sign() {
		DataToSign dataToSign = new DataToSign();
		dataToSign.setXadesFormat(es.mityc.javasign.EnumFormatoFirma.XAdES_BES);
		dataToSign.setEsquema(XAdESSchemas.XAdES_132);
		dataToSign.setXMLEncoding("UTF-8");
		dataToSign.addClaimedRol(new SimpleClaimedRole("Rol de firma"));
		dataToSign.setEnveloped(true);
		return dataToSign;
	}
}
