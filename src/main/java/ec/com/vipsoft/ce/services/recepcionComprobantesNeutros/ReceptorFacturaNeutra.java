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
import java.text.SimpleDateFormat;
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
import ec.com.vipsoft.ce.comprobantesNeutros.FacturaBinding;
import ec.com.vipsoft.ce.comprobantesNeutros.FacturaDetalleBinding;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico.TipoComprobante;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.sri.factura._v1_1_0.Factura;
import ec.com.vipsoft.sri.factura._v1_1_0.Factura.Detalles.Detalle.DetallesAdicionales.DetAdicional;
import ec.com.vipsoft.sri.factura._v1_1_0.Factura.InfoAdicional.CampoAdicional;
import ec.com.vipsoft.sri.factura._v1_1_0.Impuesto;
import ec.com.vipsoft.sri.factura._v1_1_0.ObligadoContabilidad;
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
 * El objetivo de este bean es obtener una factura. anadirle información como la
 * clave de acceso,la secuencia de documento
 *
 * @author chrisvv
 *
 */
@Stateless
@WebService
public class ReceptorFacturaNeutra {
	@EJB
	private GeneradorClaveAccesoPorEntidad generadorClaveAcceso;
	@Inject
	private UtilClaveAcceso utilClaveAccesl;
	@PersistenceContext
	private EntityManager em;

	@EJB
	private ProcesoEnvioEJB procesoEnvio;
	@WebMethod
	@WebResult(name = "claveAcceso")
	public String recibirFactura(@WebParam(name = "factura") FacturaBinding factura) {
		/**
		 * autocompletar para usuario final cuando no se determine quien es
		 */
		if((factura.getIdentificacionBeneficiario()==null)||(factura.getIdentificacionBeneficiario().length()<=1)){
			if(factura.getTotal().doubleValue()<=20d){
				factura.setIdentificacionBeneficiario("9999999999999");				
			}
		}
		if((factura.getIdentificacionBeneficiario().equalsIgnoreCase("9999999999"))||(factura.getIdentificacionBeneficiario().equalsIgnoreCase("9999999999999"))||(factura.getIdentificacionBeneficiario().equalsIgnoreCase("99999999999"))||(factura.getIdentificacionBeneficiario().equalsIgnoreCase("999999999999"))){
			factura.setRazonSocialBeneficiario("USUARIO FINAL");
			factura.setDireccionBeneficiario("USUARIO FINAL");
			factura.setIdentificacionBeneficiario("9999999999999");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		String claveAcceso=null;
		if(factura.getSecuenciaDocumento()!=null){
			claveAcceso = generadorClaveAcceso.generarClaveAccesoFactura(factura.getRucEmisor(), factura.getCodigoEstablecimiento(),	factura.getCodigoPuntoVenta(),factura.getSecuenciaDocumento());
		}else{
			claveAcceso = generadorClaveAcceso.generarClaveAccesoFactura(factura.getRucEmisor(), factura.getCodigoEstablecimiento(),	factura.getCodigoPuntoVenta());
		}			
		String rucEmisor = factura.getRucEmisor();
		String puntoEmision = utilClaveAccesl.obtenerCodigoPuntoEmision(claveAcceso);
		String establecimiento = utilClaveAccesl.obtenerCodigoEstablecimiento(claveAcceso);
		String secuenciaDocumento = utilClaveAccesl	.obtenerSecuanciaDocumento(claveAcceso);
		String ambiente = utilClaveAccesl.obtenerAmbiente(claveAcceso);
		Query q = em.createQuery("select e from Entidad e where e.ruc=?1 and e.habilitado=?2");
		q.setParameter(1, rucEmisor);
		q.setParameter(2, Boolean.TRUE);
		List<Entidad> lista = q.getResultList();
		if (!lista.isEmpty()) {
			Entidad entidad = em.find(Entidad.class, lista.get(0).getId());
			Factura facturaxml = new Factura();
			facturaxml.setId("comprobante");
			facturaxml.setVersion("1.1.0");
			if (utilClaveAccesl.esEnPruebas(claveAcceso)) {
				facturaxml.getInfoTributaria().setAmbiente("1");
			} else {
				facturaxml.getInfoTributaria().setAmbiente("2");
			}
			facturaxml.getInfoTributaria().setAmbiente(ambiente);
			facturaxml.getInfoTributaria().setClaveAcceso(claveAcceso);
			facturaxml.getInfoTributaria().setCodDoc("01");
			facturaxml.getInfoTributaria().setDirMatriz(entidad.getDireccionMatriz());
			facturaxml.getInfoTributaria().setNombreComercial(entidad.getNombreComercial());
			facturaxml.getInfoTributaria().setRazonSocial(entidad.getRazonSocial());
			facturaxml.getInfoTributaria().setRuc(entidad.getRuc());
			facturaxml.getInfoTributaria().setPtoEmi(puntoEmision);
			facturaxml.getInfoTributaria().setEstab(establecimiento);
			facturaxml.getInfoTributaria().setSecuencial(secuenciaDocumento);

			if (entidad.isObligadoContabilidad()) {
				facturaxml.getInfoFactura().setObligadoContabilidad(ObligadoContabilidad.SI);
				if (entidad.getResolucionContribuyenteEspecial() != null) {
					facturaxml.getInfoFactura().setContribuyenteEspecial(entidad.getResolucionContribuyenteEspecial());
				}
			} else {
				facturaxml.getInfoFactura().setObligadoContabilidad(ObligadoContabilidad.NO);
			}
			if (utilClaveAccesl.esEnContingencia(claveAcceso)) {
				facturaxml.getInfoTributaria().setTipoEmision("2");
			} else {
				facturaxml.getInfoTributaria().setTipoEmision("1");
			}

			facturaxml.getInfoFactura().setPropina(new BigDecimal("0.00"));
			facturaxml.getInfoFactura().setDireccionComprador(factura.getDireccionBeneficiario());
			facturaxml.getInfoFactura().setDirEstablecimiento(entidad.getDireccionMatriz());
			facturaxml.getInfoFactura().setFechaEmision(factura.getFechaEmisionTexto());
			if (factura.getGuiaRemision() != null) {
				facturaxml.getInfoFactura().setGuiaRemision(factura.getGuiaRemision());
			}
			facturaxml.getInfoFactura().setIdentificacionComprador(factura.getIdentificacionBeneficiario());
			facturaxml.getInfoFactura().setTipoIdentificacionComprador(	factura.getCodigoTipoIdentificacionBeneficiario());
			facturaxml.getInfoFactura().setRazonSocialComprador(factura.getRazonSocialBeneficiario());
			facturaxml.getInfoFactura().setMoneda("DOLAR");
			facturaxml.getInfoFactura().setTotalSinImpuestos(factura.getSubtotalIva0());
			facturaxml.getInfoFactura().setTotalDescuento(factura.calculaDescuento());	

			BigDecimal totalDescuento=new BigDecimal("0.00");
			BigDecimal totalIva12=new BigDecimal("0.00");
			BigDecimal totalIva0=new BigDecimal("0.00");
			BigDecimal baseExepto=new BigDecimal("0.00");
			BigDecimal baseIva0=new BigDecimal("0.00");
			BigDecimal baseNoSujeto=new BigDecimal("0.00");
			BigDecimal baseICE=new BigDecimal("0.00");
			BigDecimal baseIva12=new BigDecimal("0.00");

			BigDecimal sumatoria=new BigDecimal("0.00");
			BigDecimal sumaSinImpuesto=new BigDecimal("0.00");

			for (FacturaDetalleBinding d : factura.getDetalles()) {
				Factura.Detalles.Detalle detalle = new Factura.Detalles.Detalle();
				detalle.setCantidad(d.getCantidad().setScale(4,	RoundingMode.HALF_UP));
				// detalle.setCodigoPrincipal(d.getCodigoICE());
				// detalle.setCodigoAuxiliar(d.getCodigoInterno());
				detalle.setCodigoPrincipal(aliniarString(d.getCodigo(), 25));
				detalle.setDescripcion(aliniarString(d.getDescripcion(), 300));
				detalle.setPrecioUnitario(d.getValorUnitario());
				detalle.setPrecioTotalSinImpuesto(d.getValorTotal());
				if (d.getDescuento().doubleValue() > 0d) {
					detalle.setDescuento(d.getDescuento());
				} else {
					detalle.setDescuento(new BigDecimal("0.00"));
				}
				totalDescuento = totalDescuento.add(d.getDescuento());
				sumaSinImpuesto = sumaSinImpuesto.add(d.calculaBaeImponible());
				
				if (d.getInfoAdicional1() != null) {
					DetAdicional dead = new DetAdicional();
					dead.setNombre("info");
					dead.setValor(aliniarString(d.getInfoAdicional1(), 300));
					if (d.getInfoAdicional1().length() > 0) {
						detalle.getDetallesAdicionales().getDetAdicional()
								.add(dead);
					}

					// detalle.setDetallesAdicionales(dadicionales);
				}




				if(!d.getCodigoIVA().isEmpty()){			    			    
					Impuesto impuesto=new Impuesto();			    				    
					impuesto.setBaseImponible(d.calculaBaeImponible());			    	
					impuesto.setCodigo("2");			    					    	
					impuesto.setCodigoPorcentaje(d.getCodigoIVA().trim());	
					if(factura.getTipoFactura().equalsIgnoreCase("regular")){
						if(impuesto.getCodigoPorcentaje().equalsIgnoreCase("2")){			    	
							impuesto.setTarifa(new BigDecimal("12"));			    		
							impuesto.setValor(d.getIva12().setScale(2, RoundingMode.HALF_UP));			    		
							baseIva12=baseIva12.add(impuesto.getBaseImponible());
							totalIva12=totalIva12.add(impuesto.getValor());
						}
						if(impuesto.getCodigoPorcentaje().equalsIgnoreCase("0")){			    	
							impuesto.setTarifa(new BigDecimal("0.00"));			    	
							impuesto.setValor(new BigDecimal("0.00"));			    		
							baseIva0=baseIva0.add(impuesto.getBaseImponible());			    		
						}			    		
						if(impuesto.getCodigoPorcentaje().equalsIgnoreCase("6")){			    	
							impuesto.setTarifa(new BigDecimal("0.00"));
							impuesto.setValor(new BigDecimal("0.00"));			    			
							baseNoSujeto=baseNoSujeto.add(impuesto.getBaseImponible());			    
						}

						if(impuesto.getCodigoPorcentaje().equalsIgnoreCase("7")){
							impuesto.setTarifa(new BigDecimal("0.00"));
							impuesto.setValor(new BigDecimal("0.00"));
							baseExepto=baseExepto.add(impuesto.getBaseImponible());
						}

						
					}else{
						impuesto.setCodigoPorcentaje("0");					
						impuesto.setBaseImponible(d.calculaBaeImponible());
						impuesto.setCodigoPorcentaje("0");						
						impuesto.setTarifa(new BigDecimal("0.00"));			    							
						impuesto.setValor(new BigDecimal("0.00"));			    							
						baseIva0=baseIva0.add(impuesto.getBaseImponible());	
					}
					
					detalle.getImpuestos().getImpuesto().add(impuesto);
				}
				d.calcularValorTotal();
				sumatoria = sumatoria.add(d.getValorTotal());
				facturaxml.getDetalles().getDetalle().add(detalle);		
			}
			if(baseIva0.doubleValue()>0){
				Factura.InfoFactura.TotalConImpuestos.TotalImpuesto iva0=new Factura.InfoFactura.TotalConImpuestos.TotalImpuesto();
				iva0.setBaseImponible(baseIva0);
				iva0.setCodigo("2");
				iva0.setCodigoPorcentaje("0");
				iva0.setTarifa(new BigDecimal("0.00"));
				iva0.setValor(new BigDecimal("0.00"));
				facturaxml.getInfoFactura().getTotalConImpuestos().getTotalImpuesto().add(iva0);
			}
			if(baseIva12.doubleValue()>0){
				Factura.InfoFactura.TotalConImpuestos.TotalImpuesto iva12=new Factura.InfoFactura.TotalConImpuestos.TotalImpuesto();
				iva12.setBaseImponible(baseIva12);
				iva12.setCodigo("2");
				iva12.setCodigoPorcentaje("2");
				iva12.setTarifa(new BigDecimal("12.00"));
				iva12.setValor(baseIva12.multiply(new BigDecimal("0.12")).setScale(2, RoundingMode.HALF_UP));
				facturaxml.getInfoFactura().getTotalConImpuestos().getTotalImpuesto().add(iva12);
			}
			if(baseExepto.doubleValue()>0){
				Factura.InfoFactura.TotalConImpuestos.TotalImpuesto excentoiva=new Factura.InfoFactura.TotalConImpuestos.TotalImpuesto();
				excentoiva.setBaseImponible(baseExepto);
				excentoiva.setCodigo("2");
				excentoiva.setCodigoPorcentaje("7");
				excentoiva.setTarifa(new BigDecimal("0.00"));
				excentoiva.setValor(new BigDecimal("0.00"));
				facturaxml.getInfoFactura().getTotalConImpuestos().getTotalImpuesto().add(excentoiva);
			}
			if(baseNoSujeto.doubleValue()>0){				
				Factura.InfoFactura.TotalConImpuestos.TotalImpuesto nosujetoAImpuesto=new Factura.InfoFactura.TotalConImpuestos.TotalImpuesto();
				nosujetoAImpuesto.setCodigo("2");
				nosujetoAImpuesto.setCodigoPorcentaje("6");
				nosujetoAImpuesto.setValor(new BigDecimal("0.00"));
				nosujetoAImpuesto.setBaseImponible(baseNoSujeto);
				facturaxml.getInfoFactura().getTotalConImpuestos().getTotalImpuesto().add(nosujetoAImpuesto);
			}

			facturaxml.getInfoFactura().setImporteTotal(sumatoria);
			facturaxml.getInfoFactura().setTotalSinImpuestos(sumaSinImpuesto);
			facturaxml.getInfoFactura().setFechaEmision(sdf2.format(factura.getFechaEmision()));

			CampoAdicional direccion=new CampoAdicional();
			direccion.setNombre("DIRECCION");
			direccion.setValue(aliniarString(factura.getDireccionBeneficiario(),300));
			if(direccion.getValue().length()>0){
				facturaxml.getInfoAdicional().getCampoAdicional().add(direccion);	
			}


			if(factura.getOrdenCompra()!=null){
				CampoAdicional campoordencompra=new CampoAdicional();
				campoordencompra.setNombre("OC");
				campoordencompra.setValue(aliniarString(factura.getOrdenCompra(),300));
				if(campoordencompra.getValue().length()>0){
					facturaxml.getInfoAdicional().getCampoAdicional().add(campoordencompra);	
				}

			}
			if((factura.getFormaPago()!=null)){
				CampoAdicional campopago=new CampoAdicional();
				campopago.setNombre("Forma de pago");
				campopago.setValue(aliniarString(factura.getFormaPago(),300));	
				if(campopago.getValue().length()>0){
					facturaxml.getInfoAdicional().getCampoAdicional().add(campopago);	
				}

			}

			

			facturaxml.getInfoFactura().setImporteTotal(sumatoria);
			facturaxml.getInfoFactura().setTotalDescuento(totalDescuento);		
			facturaxml.getInfoFactura().setPropina(new BigDecimal("0.00"));
			if(factura.getTipoFactura().equalsIgnoreCase("exportacion")){
				facturaxml.getInfoFactura().setComercioExterior("EXPORTADOR");
				facturaxml.getInfoFactura().setIncoTermFactura("EXW");
				facturaxml.getInfoFactura().setLugarIncoTerm("GUAYAQUIL");
				facturaxml.getInfoFactura().setPaisOrigen("593");
				facturaxml.getInfoFactura().setPuertoEmbarque("GUAYAQUIL");
				facturaxml.getInfoFactura().setPuertoDestino(consultaPais(factura.getCodigoPaisDestino()));
				facturaxml.getInfoFactura().setPaisDestino(factura.getCodigoPaisDestino());
				facturaxml.getInfoFactura().setPaisAdquisicion(factura.getCodigoPaisDestino());
				facturaxml.getInfoFactura().setFleteInternacional(new BigDecimal("0.00"));
				facturaxml.getInfoFactura().setSeguroInternacional(new BigDecimal("0.00"));
				facturaxml.getInfoFactura().setGastosAduaneros(new BigDecimal("0.00"));
				facturaxml.getInfoFactura().setGastosTransporteOtros(new BigDecimal("0.00"));
				facturaxml.getInfoFactura().setGastosTransporteOtros(new BigDecimal("0.00"));
			}
			

			try {
				Document convertidoEnDOM = DocumentBuilderFactory.newInstance()	.newDocumentBuilder().newDocument();
				JAXBContext contexto = JAXBContext.newInstance(Factura.class);
				Marshaller marshaller = contexto.createMarshaller();
				marshaller.marshal(facturaxml, convertidoEnDOM);

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
				parametros.put("establecimiento",factura.getCodigoEstablecimiento());
				parametros.put("codigoPuntoVenta",factura.getCodigoPuntoVenta());	
				parametros.put("enPruebas",utilClaveAccesl.esEnPruebas(claveAcceso));
				parametros.put("claveAcceso", claveAcceso);
				parametros.put("secuenciaDocumento", secuenciaDocumento);
				parametros.put("intentos", 0);
				parametros.put("maxIntentos", 5);
				parametros.put("idCliente", facturaxml.getInfoFactura().getIdentificacionComprador());
				parametros.put("tipoComprobante", TipoComprobante.factura);
				procesoEnvio.lanzarProcesoEnvio(parametros);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}
		return claveAcceso;
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
	public String aliniarString(String texto,int longitudmax){
		String reeemplazdo=texto.replaceAll("\r","");
		String reemplazo2=reeemplazdo.replaceAll("\n", "");
		if(reemplazo2.length()>longitudmax){
			return reemplazo2.substring(0,longitudmax);
		}
		else{
			return reemplazo2;
		}
	}
	private String consultaPais(String codigoPais){
		Map<String,String>mapaPaises=new HashMap<String, String>();
		mapaPaises.put("108", "CHILE");
		mapaPaises.put("105","COLOMBIA");
		mapaPaises.put("101","ARGENTINA");
		mapaPaises.put("110","ESTADOS UNIDOS");
		return mapaPaises.get(codigoPais);
	}
}
