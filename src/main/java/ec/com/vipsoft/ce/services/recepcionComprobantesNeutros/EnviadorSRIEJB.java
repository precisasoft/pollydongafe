package ec.com.vipsoft.ce.services.recepcionComprobantesNeutros;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.Base64;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import ec.com.vipsoft.ce.backend.service.VerificadorIndisponibilidad;
import ec.com.vipsoft.erp.abinadi.procesos.DetalleRecepcion;
import ec.com.vipsoft.erp.abinadi.procesos.RespuestaRecepcionDocumento;

@Stateless
public class EnviadorSRIEJB {
	@EJB
	private VerificadorIndisponibilidad verificadorEnContingencia;

	private static String URL_WSDL_RECEPCION_COMPROBATES= "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl";
	private static String URL_OPERACION_RECEPCION_COMPROBATES = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes";
	private RespuestaRecepcionDocumento enviarDocumento(byte[] xml) throws SOAPException
	{
		QName serviceName =	  new QName(URL_WSDL_RECEPCION_COMPROBATES, "RecepcionComprobanteService");
		
		QName portName=new QName(URL_WSDL_RECEPCION_COMPROBATES,"RecepcionComprobantesPort");
		String endpointAddress= URL_OPERACION_RECEPCION_COMPROBATES;
		
		Service service = Service.create(serviceName);

		// Add a port to the Service
		service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING,endpointAddress);
		Dispatch<SOAPMessage> dispatch = service.createDispatch(portName,SOAPMessage.class, Service.Mode.MESSAGE);
		// Use Dispatch as BindingProvider

		StringBuilder sbrequest = new StringBuilder("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ec=\"http://ec.gob.sri.ws.recepcion\">");
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
	
	private RespuestaRecepcionDocumento converToRespuestaRecepcionDocumento(	String xmlRespuesta) {
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


	//@Asynchronous
	public RespuestaRecepcionDocumento enviarComprobanteAlSRI(String documentoXmlFirmado, boolean enPruebas) {
		
		if(enPruebas){
			URL_WSDL_RECEPCION_COMPROBATES= "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl";
			URL_OPERACION_RECEPCION_COMPROBATES = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes";
		}else{
			URL_WSDL_RECEPCION_COMPROBATES= "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl";
			URL_OPERACION_RECEPCION_COMPROBATES = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes";
		}		
		RespuestaRecepcionDocumento respuestaRecepcion = null;
		try {
			respuestaRecepcion = enviarDocumento(documentoXmlFirmado.getBytes());
		} catch (SOAPException e) {
			verificadorEnContingencia.darUnToqueIndisponibilidad();
			e.printStackTrace();
		}
		return respuestaRecepcion;
		
	}
}
