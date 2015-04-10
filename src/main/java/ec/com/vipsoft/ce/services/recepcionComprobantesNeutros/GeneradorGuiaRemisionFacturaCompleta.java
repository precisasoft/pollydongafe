package ec.com.vipsoft.ce.services.recepcionComprobantesNeutros;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
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
import javax.xml.bind.JAXBException;
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
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.Autorizacion;
import ec.com.vipsoft.ce.utils.LlenadorNumeroComprobante;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteAutorizado;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico.TipoComprobante;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.erp.abinadi.dominio.Establecimiento;
import ec.com.vipsoft.sri.factura._v1_1_0.Factura;
import ec.com.vipsoft.sri.guiaremision._v1_1_0.Destinatario;
import ec.com.vipsoft.sri.guiaremision._v1_1_0.Detalle;
import ec.com.vipsoft.sri.guiaremision._v1_1_0.Detalle.DetallesAdicionales.DetAdicional;
import ec.com.vipsoft.sri.guiaremision._v1_1_0.GuiaRemision;
import ec.com.vipsoft.sri.guiaremision._v1_1_0.ObligadoContabilidad;
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
public class GeneradorGuiaRemisionFacturaCompleta {
	@PersistenceContext
	private EntityManager em;
	@Inject
	private LlenadorNumeroComprobante llenadorNumeroComprobante;
	@EJB
	private GeneradorClaveAccesoPorEntidad generadorClavePorEntidad;
	@Inject
	private UtilClaveAcceso utilClaveAcceso;
	@EJB
	private ProcesoEnvioEJB procesoEnvio;
	public String generarGuiaRemisionFacturaCompleta(String rucEmisor,String numeroDocumentoFactura,String identificacionTransportista,String tipoIdentificacionTransportista,String razonSocialTransportista,String placa,Date fechaInicial,Date fechaFinal,String direccionPartida){		
		StringBuilder sb=new StringBuilder();
		Query qentidad=em.createQuery("select e from Entidad e where e.ruc=?1");
		qentidad.setParameter(1, rucEmisor);
		List<Entidad>listaEntidad=qentidad.getResultList();
		if(!listaEntidad.isEmpty()){			
			Entidad entidad=em.find(Entidad.class, listaEntidad.get(0).getId());
			String _numeroFactura=llenadorNumeroComprobante.llenarNumeroDocumento(numeroDocumentoFactura);
			StringTokenizer stok=new StringTokenizer(_numeroFactura,"-");
			String establecimiento=stok.nextToken();
			String puntoEmision=stok.nextToken();
			String secuenciaFactura=stok.nextToken();
			
			Query qcomprobanteelectronica=em.createQuery("select c from ComprobanteElectronico c where c.entidadEmisora=?1 and c.tipo=?2 and c.establecimiento=?3 and c.puntoEMision=?4 and c.secuencia=?5 and c.enviado=?6" );
			qcomprobanteelectronica.setParameter(1,entidad);
			qcomprobanteelectronica.setParameter(2, TipoComprobante.factura);
			qcomprobanteelectronica.setParameter(3, establecimiento);
			qcomprobanteelectronica.setParameter(4, puntoEmision);
			qcomprobanteelectronica.setParameter(5, secuenciaFactura);
			qcomprobanteelectronica.setParameter(6, Boolean.TRUE);
			List<ComprobanteElectronico>listaComprobante=qcomprobanteelectronica.getResultList();
			if(!listaComprobante.isEmpty()){
				ComprobanteElectronico comprobanteElectonicofactura=em.find(ComprobanteElectronico.class, listaComprobante.get(0).getId());
				String claveAcceso=generadorClavePorEntidad.generarClaveAccesoGuiaRemision(rucEmisor, establecimiento, puntoEmision);
				GuiaRemision comprobante=new GuiaRemision();
				comprobante.setVersion("1.1.0");
				comprobante.setId("comprobante");						
				comprobante.getInfoTributaria().setAmbiente(utilClaveAcceso.obtenerAmbiente(claveAcceso));
				boolean enContingencia=utilClaveAcceso.esEnContingencia(claveAcceso);
				if(enContingencia){
					comprobante.getInfoTributaria().setTipoEmision("2");
				}else{
					comprobante.getInfoTributaria().setTipoEmision("1");
				}
				comprobante.getInfoTributaria().setCodDoc("06");
				comprobante.getInfoTributaria().setClaveAcceso(claveAcceso);
				comprobante.getInfoTributaria().setDirMatriz(entidad.getDireccionMatriz());
				comprobante.getInfoTributaria().setRuc(rucEmisor);
				comprobante.getInfoTributaria().setRazonSocial(entidad.getRazonSocial());
				comprobante.getInfoTributaria().setNombreComercial(entidad.getNombreComercial());
				comprobante.getInfoTributaria().setEstab(establecimiento);
				comprobante.getInfoTributaria().setPtoEmi(puntoEmision);
				comprobante.getInfoTributaria().setSecuencial(utilClaveAcceso.obtenerSecuanciaDocumento(claveAcceso));
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				if(entidad.isObligadoContabilidad()){
					comprobante.getInfoGuiaRemision().setObligadoContabilidad(ObligadoContabilidad.SI);
					if(entidad.getResolucionContribuyenteEspecial()!=null){
						comprobante.getInfoGuiaRemision().setContribuyenteEspecial(entidad.getResolucionContribuyenteEspecial());		
					}
				}else{
					comprobante.getInfoGuiaRemision().setObligadoContabilidad(ObligadoContabilidad.NO);
				}
				
				Query qestablecimiento=em.createQuery("select e from Establecimiento e where e.entidad=?1 and e.codigo=?2");
				qestablecimiento.setParameter(1, entidad);
				qestablecimiento.setParameter(2, establecimiento);
				List<Establecimiento>listaEstablecimiento=qestablecimiento.getResultList();
				if(!listaEstablecimiento.isEmpty()){
					Establecimiento _establ=em.find(Establecimiento.class,listaEstablecimiento.get(0).getId());
					comprobante.getInfoGuiaRemision().setDirEstablecimiento(_establ.getDireccion());
					comprobante.getInfoGuiaRemision().setDirPartida(direccionPartida);
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
					comprobante.getInfoGuiaRemision().setFechaIniTransporte(sdf2.format(fechaInicial));
					comprobante.getInfoGuiaRemision().setFechaFinTransporte(sdf2.format(fechaFinal));
					comprobante.getInfoGuiaRemision().setPlaca(placa);
					comprobante.getInfoGuiaRemision().setRazonSocialTransportista(razonSocialTransportista);
					comprobante.getInfoGuiaRemision().setRucTransportista(identificacionTransportista);
					comprobante.getInfoGuiaRemision().setTipoIdentificacionTransportista(tipoIdentificacionTransportista);
					/////////////////////////////////////////////////////////////////////////////////////////////////////////7
				}
				////////////////////////cargar la factura y usar beneficiacrio como destinatarios ... y los detalles
				
				
					 
					try {
						JAXBContext	contexto = JAXBContext.newInstance(Autorizacion.class);
						Unmarshaller unmarshaller = contexto.createUnmarshaller();
						if (comprobanteElectonicofactura.getComprobanteAutorizado() != null) {
							ComprobanteAutorizado cautorizado =comprobanteElectonicofactura.getComprobanteAutorizado();
							StringReader reader = new StringReader(new String(cautorizado.getEnXML()));
							Autorizacion autorizacion = (Autorizacion) unmarshaller.unmarshal(new InputSource(reader));
							JAXBContext contextoFactura = JAXBContext.newInstance(Factura.class);			
							Unmarshaller unmarshallerFactura=contextoFactura.createUnmarshaller();
							Factura factura=(Factura)unmarshallerFactura.unmarshal(new InputSource(new StringReader(autorizacion.getComprobante())));
							Destinatario destinatario=new Destinatario();
							destinatario.setCodDocSustento("04");
							destinatario.setDirDestinatario(factura.getInfoFactura().getDireccionComprador());
							destinatario.setFechaEmisionDocSustento(factura.getInfoFactura().getFechaEmision());
							destinatario.setIdentificacionDestinatario(factura.getInfoFactura().getIdentificacionComprador());
							destinatario.setRazonSocialDestinatario(factura.getInfoFactura().getRazonSocialComprador());
							destinatario.setMotivoTraslado("Venta");
							destinatario.setNumDocSustento(factura.getInfoTributaria().getEstab()+"-"+factura.getInfoTributaria().getEstab()+"-"+factura.getInfoTributaria().getSecuencial());
							destinatario.setNumAutDocSustento(autorizacion.getNumeroAutorizacion());
							for(ec.com.vipsoft.sri.factura._v1_1_0.Factura.Detalles.Detalle detalleFactura:factura.getDetalles().getDetalle()){
								Detalle detalle=new Detalle();
								detalle.setCantidad(detalleFactura.getCantidad());
								detalle.setCodigoInterno(detalleFactura.getCodigoPrincipal());
								detalle.setDescripcion(detalleFactura.getDescripcion());
								if(!detalleFactura.getDetallesAdicionales().getDetAdicional().isEmpty()){
									DetAdicional detalleAdicional=new DetAdicional();
									detalleAdicional.setNombre(detalleFactura.getDetallesAdicionales().getDetAdicional().get(0).getNombre());
									detalleAdicional.setValor(detalleFactura.getDetallesAdicionales().getDetAdicional().get(0).getValor());
									detalle.getDetallesAdicionales().getDetAdicional().add(detalleAdicional);
								}
								destinatario.getDetalles().getDetalle().add(detalle);
							}
							comprobante.getDestinatarios().getDestinatario().add(destinatario);
						}	

					} catch (JAXBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//////////////////////////////////////////////////////////////////////////////////7
					
					try {
						Document convertidoEnDOM = DocumentBuilderFactory.newInstance()	.newDocumentBuilder().newDocument();
						JAXBContext contexto = JAXBContext.newInstance(GuiaRemision.class);
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
						parametros.put("enPruebas",utilClaveAcceso.esEnPruebas(claveAcceso));
						parametros.put("claveAcceso", claveAcceso);
						parametros.put("secuenciaDocumento", utilClaveAcceso.obtenerSecuanciaDocumento(claveAcceso));
						parametros.put("intentos", 0);
						parametros.put("maxIntentos", 5);
						parametros.put("idCliente", comprobante.getDestinatarios().getDestinatario().get(0).getIdentificacionDestinatario());
						parametros.put("tipoComprobante", TipoComprobante.guiaRemision);
						procesoEnvio.lanzarProcesoEnvio(parametros);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}else{
				sb.append("no ha sido aprobada la factura "+numeroDocumentoFactura);
			}
				
		}
		else{
			sb.append("no existe la entidad con ese ruc");
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
