package ec.com.vipsoft.erp.abinadi.procesos;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.Base64;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
public class EnviadorSRI implements JavaDelegate {
	private static String URL_WSDL_RECEPCION_COMPROBATES= "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl";
	private static String URL_OPERACION_RECEPCION_COMPROBATES = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes";


	public void executeold(DelegateExecution arg0) throws Exception {
		Logger.getLogger(EnviadorSRI.class.getCanonicalName()).info("ingresando a metodo execute de EnviadorSRI");
		String timeoutrecepcion = (String) arg0.getVariable("timeputrecepcion");
		boolean enPruebas = (boolean) arg0.getVariable("enPruebas");

		// documentoxml
		String documentoXmlFirmado = (String) arg0.getVariable("documentoFirmado");
		if (enPruebas) {
			
			URL_WSDL_RECEPCION_COMPROBATES= "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl";
			URL_OPERACION_RECEPCION_COMPROBATES = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes";
			
			ec.gob.sri.canales.pruebas.recepcion.RecepcionComprobantesService sevice = new ec.gob.sri.canales.pruebas.recepcion.RecepcionComprobantesService();
			ec.gob.sri.canales.pruebas.recepcion.RecepcionComprobantes port = sevice.getRecepcionComprobantesPort();
			ec.gob.sri.canales.pruebas.recepcion.RespuestaSolicitud respuesta = port.validarComprobante(documentoXmlFirmado.getBytes());
			arg0.setVariable("respuestaRecepcion", respuesta.getEstado());
			Logger.getLogger(EnviadorSRI.class.getCanonicalName()).info("la respuesta fue " + respuesta.getEstado());
			if (!respuesta.getEstado().equals("RECIBIDA")) {
				arg0.setVariable("respuesta", "DEVUELTA");
				if(!respuesta.getComprobantes().getComprobante().isEmpty()){
					String codigoError = respuesta.getComprobantes().getComprobante().get(0).getMensajes().getMensaje().get(0).getIdentificador();
					String mensajeError = respuesta.getComprobantes().getComprobante().get(0).getMensajes().getMensaje().get(0).getMensaje();
					Logger.getLogger(EnviadorSRI.class.getCanonicalName()).info("DEVUELTA  ERROR " + codigoError + "    MENSAJE "+ mensajeError);
					arg0.setVariable("codigoError", codigoError);
					arg0.setVariable("mensajeError", mensajeError);
					arg0.setVariable("enviado", false);
				}else{
					arg0.setVariable("enviado", false);
					arg0.setVariable("codigoError", "VACIO");
					arg0.setVariable("mensajeError", "VACIO");
				}
				
			}else{
				arg0.setVariable("enviado", true);
				arg0.setVariable("respuesta", "ENVIADO");
			}
		} else {

			ec.gob.sri.canales.produccion.recepcion.RecepcionComprobantesService sevice = new ec.gob.sri.canales.produccion.recepcion.RecepcionComprobantesService();
			ec.gob.sri.canales.produccion.recepcion.RecepcionComprobantes port = sevice.getRecepcionComprobantesPort();
			ec.gob.sri.canales.produccion.recepcion.RespuestaSolicitud respuesta = port.validarComprobante(documentoXmlFirmado.getBytes());
			arg0.setVariable("respuestaRecepcion", respuesta.getEstado());
			Logger.getLogger(EnviadorSRI.class.getCanonicalName()).info("la respuesta fue " + respuesta.getEstado());
			if (!respuesta.getEstado().equals("RECIBIDA")) {
				arg0.setVariable("respuesta", "DEVUELTA");
				if(!respuesta.getComprobantes().getComprobante().isEmpty()){
					String codigoError = respuesta.getComprobantes().getComprobante().get(0).getMensajes().getMensaje().get(0).getIdentificador();
					String mensajeError = respuesta.getComprobantes().getComprobante().get(0).getMensajes().getMensaje().get(0).getMensaje();
					Logger.getLogger(EnviadorSRI.class.getCanonicalName()).info("DEVUELTA  ERROR " + codigoError + "    MENSAJE "+ mensajeError);
					arg0.setVariable("codigoError", codigoError);
					arg0.setVariable("mensajeError", mensajeError);	
					arg0.setVariable("enviado", false);
				}else{
					arg0.setVariable("enviado", false);
					arg0.setVariable("codigoError", "VACIO");
					arg0.setVariable("mensajeError", "VACIO");					
				}				
			}else{
				arg0.setVariable("enviado", true);
				arg0.setVariable("respuesta", "ENVIADO");
			}
		}
		Logger.getLogger(EnviadorSRI.class.getCanonicalName()).info("finalizando método execute de enviador SRI");
	}

	
	public RespuestaRecepcionDocumento enviarDocumento(byte[] xml) throws SOAPException
	{
		QName serviceName =	  new QName(URL_WSDL_RECEPCION_COMPROBATES, "RecepcionComprobanteService");
		
		QName portName=new QName(URL_WSDL_RECEPCION_COMPROBATES,"RecepcionComprobantesPort");
		String endpointAddress= URL_OPERACION_RECEPCION_COMPROBATES;
		
		Service service = Service.create(serviceName);

		// Add a port to the Service
		service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING,
				endpointAddress);
		Dispatch<SOAPMessage> dispatch = service.createDispatch(portName,
				SOAPMessage.class, Service.Mode.MESSAGE);
		// Use Dispatch as BindingProvider

		StringBuilder sbrequest = new StringBuilder(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ec=\"http://ec.gob.sri.ws.recepcion\">");
		sbrequest.append("<soapenv:Header/>");
		sbrequest.append("<soapenv:Body>");
		sbrequest.append("<ec:validarComprobante>");
		sbrequest.append("<xml>");
		sbrequest.append(Base64.getEncoder().encodeToString(xml));
		sbrequest.append("</xml>");
		sbrequest.append("</ec:validarComprobante>");
		sbrequest.append("</soapenv:Body>");
		sbrequest.append("</soapenv:Envelope>");
		
		
		
		MessageFactory messageFactory;
		messageFactory = MessageFactory.newInstance();
		SOAPMessage mensajer = messageFactory.createMessage();
		mensajer.saveChanges();
		mensajer.getSOAPPart().setContent((Source) new StreamSource(new StringReader(sbrequest.toString())));
		SOAPMessage result = dispatch.invoke(mensajer);
		ByteArrayOutputStream baos = null;
		String s = null;
		try {
			baos = new ByteArrayOutputStream();
			result.writeTo(baos);
			s = baos.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println(s);
		return converToRespuestaRecepcionDocumento(s);
	}
	
	public RespuestaRecepcionDocumento converToRespuestaRecepcionDocumento(	String xmlRespuesta) {
		RespuestaRecepcionDocumento respuestaRecepcionDocumento = new RespuestaRecepcionDocumento();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlRespuesta));
			Document doc = dBuilder.parse(is);
			NodeList respRecpCompXml = doc.getElementsByTagName("RespuestaRecepcionComprobante");
			for (int i = 0; i < respRecpCompXml.getLength(); i++) {
				Element elementRespRecpCompXml = (Element) respRecpCompXml.item(i);
				respuestaRecepcionDocumento.setEstado(elementRespRecpCompXml.getElementsByTagName("estado").item(0).getFirstChild().getNodeValue());
				NodeList comprobanteXml = elementRespRecpCompXml.getElementsByTagName("comprobante");
				for (int j = 0; j < comprobanteXml.getLength(); j++) {
					Element elementCompXml = (Element) comprobanteXml.item(j);
					DetalleRecepcion detalleRecepcion = new DetalleRecepcion();
					detalleRecepcion.setClaveAccesso(elementCompXml.getElementsByTagName("claveAcceso").item(0).getFirstChild().getNodeValue());
					detalleRecepcion.setCodigo(elementCompXml.getElementsByTagName("identificador").item(0)	.getFirstChild().getNodeValue());

					NodeList mensajeXml = elementCompXml.getElementsByTagName("mensaje");
					for (int k = 0; k < mensajeXml.getLength(); k++) {
						Element elementMnsXml = (Element) mensajeXml.item(k);
						NodeList nl = elementMnsXml.getChildNodes();
						for (int z = 0; z < nl.getLength() - 1; z++) {
							if (nl.item(z).getNodeName().equalsIgnoreCase("mensaje")) {
								detalleRecepcion.setMensaje(nl.item(z).getFirstChild().getNodeValue());
							}
						}
					}

					detalleRecepcion.setTipo(elementCompXml.getElementsByTagName("tipo").item(0).getFirstChild().getNodeValue());
					detalleRecepcion.setInformacionAdicional(elementCompXml.getElementsByTagName("informacionAdicional").item(0).getFirstChild().getNodeValue());
					respuestaRecepcionDocumento.getDetalle().add(detalleRecepcion);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return respuestaRecepcionDocumento;
	}


	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		
		if((boolean) arg0.getVariable("enPruebas")){
			URL_WSDL_RECEPCION_COMPROBATES= "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl";
			URL_OPERACION_RECEPCION_COMPROBATES = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes";
		}else{
			URL_WSDL_RECEPCION_COMPROBATES= "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl";
			URL_OPERACION_RECEPCION_COMPROBATES = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes";
		}
		String documentoXmlFirmado = (String) arg0.getVariable("documentoFirmado");
		RespuestaRecepcionDocumento respuestaRecepcion = enviarDocumento(documentoXmlFirmado.getBytes());
		if(respuestaRecepcion.getEstado().equalsIgnoreCase("DEVUELTA")){
			arg0.setVariable("respuesta", "DEVUELTA");
			arg0.setVariable("codigoError", respuestaRecepcion.getDetalle().get(0).getCodigo());
			arg0.setVariable("mensajeError",respuestaRecepcion.getDetalle().get(0).getMensaje());
			arg0.setVariable("enviado", false);
		}else{
			arg0.setVariable("respuesta", "RECIBIDA");
			arg0.setVariable("enviado", true);
		}
		
	}
}
