package ec.com.vipsoft.ce.services.recepcionComprobantesNeutros;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import ec.com.vipsoft.ce.backend.service.GeneradorClaveAccesoPorEntidad;
import ec.com.vipsoft.ce.comprobantesNeutros.FacturaDetalleBinding;
import ec.com.vipsoft.ce.comprobantesNeutros.NotaCreditoBinding;
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.Autorizacion;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteAutorizado;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico.TipoComprobante;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.sri.factura._v1_1_0.Factura;
import ec.com.vipsoft.sri.notacredito._v1_1_0.Impuesto;
import ec.com.vipsoft.sri.notacredito._v1_1_0.NotaCredito;
import ec.com.vipsoft.sri.notacredito._v1_1_0.NotaCredito.Detalles.Detalle;
import ec.com.vipsoft.sri.notacredito._v1_1_0.NotaCredito.Detalles.Detalle.DetallesAdicionales.DetAdicional;
import ec.com.vipsoft.sri.notacredito._v1_1_0.ObligadoContabilidad;
import ec.com.vipsoft.sri.notacredito._v1_1_0.TotalConImpuestos.TotalImpuesto;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.firmaJava.role.SimpleClaimedRole;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.IPassStoreKS;
import es.mityc.javasign.pkstore.keystore.KSStore;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;

@Stateless
public class ReceptorNotaCreditoNeutra {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private GeneradorClaveAccesoPorEntidad generadorClaveAcceso;
	@Inject
	private UtilClaveAcceso utilClaveAccesl;
	@EJB
	private ProcesoEnvioEJB procesoEnvio;
	public String procesarNotaCredito(NotaCreditoBinding notaCredito){
		StringBuilder sb=new StringBuilder();
		String claveAcceso=generadorClaveAcceso.generarClaveAccesoNotaCredito(notaCredito.getRucEmisor(), notaCredito.getCodigoEstablecimiento(), notaCredito.getCodigoPuntoVenta());
		sb.append(claveAcceso);
		String rucEmisor = notaCredito.getRucEmisor();
		
		Query q = em.createQuery("select e from Entidad e where e.ruc=?1 and e.habilitado=?2");
		q.setParameter(1, rucEmisor);
		q.setParameter(2, Boolean.TRUE);
		List<Entidad> lista = q.getResultList();
		if (!lista.isEmpty()) {
			Entidad entidad=em.find(Entidad.class, lista.get(0).getId());
			
			
			Query qFactura=em.createQuery("select f from ComprobanteElectronico f where f.entidadEmisora=?1 and f.tipo=?2 and f.establecimiento=?3 and f.puntoEMision=?4 and f.secuencia=?5 and f.autorizado=?6");
			qFactura.setParameter(1, entidad);
			qFactura.setParameter(2, TipoComprobante.factura);
			StringTokenizer stt=new StringTokenizer(notaCredito.getNumeroFacturaAModificar(),"-");
			qFactura.setParameter(3, stt.nextToken());
			qFactura.setParameter(4, stt.nextToken());
			qFactura.setParameter(5, stt.nextToken());
			qFactura.setParameter(6, Boolean.TRUE);
			List<ComprobanteElectronico>listadoComprobante=qFactura.getResultList();
			if(!listadoComprobante.isEmpty()){
				
				ComprobanteElectronico comprobanteFactura=listadoComprobante.get(0);
				ComprobanteAutorizado comprobanteAutorizado = comprobanteFactura.getComprobanteAutorizado();
				StringReader reader = new StringReader(new String(comprobanteAutorizado.getEnXML()));
				try {
					JAXBContext contextoautorizacion=JAXBContext.newInstance(Autorizacion.class);
					Unmarshaller unmarshaller=contextoautorizacion.createUnmarshaller();
					JAXBContext contextoFactura = JAXBContext.newInstance(Factura.class);			
					Unmarshaller unmarshallerFactura=contextoFactura.createUnmarshaller();
					Autorizacion autorizacion = (Autorizacion) unmarshaller.unmarshal(new InputSource(reader));
					Factura factura=(Factura)unmarshallerFactura.unmarshal(new InputSource(new StringReader(autorizacion.getComprobante())));
					String puntoEmision=utilClaveAccesl.obtenerCodigoPuntoEmision(claveAcceso);
					String establecimiento=utilClaveAccesl.obtenerCodigoEstablecimiento(claveAcceso);
					String secuenciaDocumento=utilClaveAccesl.obtenerSecuanciaDocumento(claveAcceso);
					String ambiente=utilClaveAccesl.obtenerAmbiente(claveAcceso);
					String codigoDocumento=utilClaveAccesl.obtenerTipoDocumento(claveAcceso);
					NotaCredito comprobante=new NotaCredito();
					comprobante.setVersion("1.1.0");
					comprobante.setId("comprobante");
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
					if(entidad.isObligadoContabilidad()){
						comprobante.getInfoNotaCredito().setObligadoContabilidad(ObligadoContabilidad.SI);
						if(entidad.getResolucionContribuyenteEspecial()!=null){
							comprobante.getInfoNotaCredito().setContribuyenteEspecial(entidad.getResolucionContribuyenteEspecial());
						}                           
					}else{
						comprobante.getInfoNotaCredito().setObligadoContabilidad(ObligadoContabilidad.NO);
					}
					comprobante.getInfoNotaCredito().setDirEstablecimiento(entidad.getDireccionMatriz());
					comprobante.getInfoNotaCredito().setCodDocModificado("01");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
					comprobante.getInfoNotaCredito().setFechaEmision(sdf2.format(new Date()));
					comprobante.getInfoNotaCredito().setFechaEmisionDocSustento(factura.getInfoFactura().getFechaEmision());
					comprobante.getInfoNotaCredito().setIdentificacionComprador(factura.getInfoFactura().getIdentificacionComprador());
					comprobante.getInfoNotaCredito().setMoneda("DOLAR");
					comprobante.getInfoNotaCredito().setMotivo(notaCredito.getMotivoAModificar());
					comprobante.getInfoNotaCredito().setNumDocModificado(notaCredito.getNumeroFacturaAModificar());
					comprobante.getInfoNotaCredito().setRazonSocialComprador(factura.getInfoFactura().getRazonSocialComprador());
					comprobante.getInfoNotaCredito().setTipoIdentificacionComprador(factura.getInfoFactura().getTipoIdentificacionComprador());
					
					///////////////////////////////////////////////////////////////////////////////////////////////
					
					BigDecimal baseIva0=new BigDecimal("0.00");
					BigDecimal baseIva12=new BigDecimal("0.00");
					BigDecimal iva12=new BigDecimal("0.00");
					BigDecimal baseNoSujeto=new BigDecimal("0.00");
					BigDecimal baseExcento=new BigDecimal("0.00");
					for(FacturaDetalleBinding dam : notaCredito.getDetallesAModficar()){
						
						Detalle detalle=new Detalle();
						detalle.setCantidad(dam.getCantidad());
						if(dam.getCodigoAlterno()!=null){
							detalle.setCodigoAdicional(dam.getCodigoAlterno());	
						}
						detalle.setCodigoInterno(dam.getCodigo());
						detalle.setDescripcion(dam.getDescripcion());
						detalle.setDescuento(dam.getDescuento());
						if(dam.getInfoAdicional1()!=null){
							
							DetAdicional da=new DetAdicional();
							da.setNombre("info");
							da.setValor(dam.getInfoAdicional1());
							detalle.getDetallesAdicionales().getDetAdicional().add(da);	
						}
						Impuesto impuesto=new Impuesto();
						impuesto.setCodigo("2");
						impuesto.setCodigoPorcentaje(dam.getCodigoIVA());
						impuesto.setBaseImponible(dam.calculaBaeImponible());
						if(dam.getCodigoIVA().equalsIgnoreCase("2")){							
							impuesto.setTarifa(new BigDecimal("12.00"));							
							impuesto.setValor(dam.getIva12());
							iva12=iva12.add(dam.getIva12());
							baseIva12=baseIva12.add((impuesto.getBaseImponible()));
						}
						else{
							impuesto.setTarifa(new BigDecimal("0.00"));	
							impuesto.setValor(new BigDecimal("0.00"));
							if(dam.getCodigoIVA().equals("0")){
								baseIva0=baseIva0.add((impuesto.getBaseImponible()));	
							}
							if(dam.getCodigoIVA().equals("6")){
								baseNoSujeto=baseNoSujeto.add((impuesto.getBaseImponible()));	
							}
							if(dam.getCodigoIVA().equals("7")){
								baseExcento=baseExcento.add((impuesto.getBaseImponible()));	
							}
							
						}
						detalle.getImpuestos().getImpuesto().add(impuesto);
						detalle.setPrecioUnitario(dam.getValorUnitario());
						BigDecimal precioTotalSinImpuestos=new BigDecimal("0.00");
						precioTotalSinImpuestos=detalle.getCantidad().multiply(detalle.getPrecioUnitario());
						precioTotalSinImpuestos=precioTotalSinImpuestos.subtract(detalle.getDescuento());
						detalle.setPrecioTotalSinImpuesto(precioTotalSinImpuestos.setScale(2,RoundingMode.HALF_UP));
						comprobante.getDetalles().getDetalle().add(detalle);
					}
					BigDecimal sumaBases=baseExcento.add(baseIva0.add(baseIva12).add(baseNoSujeto));
					comprobante.getInfoNotaCredito().setTotalSinImpuestos(sumaBases);
					if(baseIva12.doubleValue()>0){
						TotalImpuesto totali=new TotalImpuesto();
						totali.setBaseImponible(baseIva12);
						totali.setCodigo("2");
						totali.setCodigoPorcentaje("2");						
						totali.setValor(iva12);
						comprobante.getInfoNotaCredito().getTotalConImpuestos().getTotalImpuesto().add(totali);
						comprobante.getInfoNotaCredito().setValorModificacion(totali.getValor());
						
					}
					
//					/////////////////////////////////////////////////////////////////////////////////////////////////////////////////     
//					
					
					try {
						Document convertidoEnDOM = DocumentBuilderFactory.newInstance()	.newDocumentBuilder().newDocument();
						JAXBContext contexto = JAXBContext.newInstance(NotaCredito.class);
						Marshaller marshaller = contexto.createMarshaller();
						marshaller.marshal(comprobante, convertidoEnDOM);

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
						parametros.put("establecimiento",comprobante.getInfoTributaria().getEstab());
						parametros.put("codigoPuntoVenta",comprobante.getInfoTributaria().getPtoEmi());	
						parametros.put("enPruebas",utilClaveAccesl.esEnPruebas(claveAcceso));
						parametros.put("claveAcceso", claveAcceso);
						parametros.put("secuenciaDocumento", utilClaveAccesl.obtenerSecuanciaDocumento(claveAcceso));
						parametros.put("intentos", 0);
						parametros.put("maxIntentos", 5);
						parametros.put("idCliente", factura.getInfoFactura().getIdentificacionComprador());
					
						parametros.put("tipoComprobante",TipoComprobante.notaCredito );
						procesoEnvio.lanzarProcesoEnvio(parametros);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
		
			}
			
		
		}
			
		
		return sb.toString();
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
