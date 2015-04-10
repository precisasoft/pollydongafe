package ec.com.vipsoft.ce.sri.autorizacion.wsclient;


import javax.xml.soap.SOAPException;

public interface ConsultaAutorizacion{

	public RespuestaAutorizacionComprobante consultarAutorizacion(String clave)	throws SOAPException ;//{
//	
//	
//	
//	
//		QName serviceName = new QName("https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantes","AutorizacionComprobantesService");
//
//		QName portName = new QName("https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantes?wsdl",	"AutorizacionComprobantesPort");
//		String endpointAddress = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantes";
//		Service service = Service.create(serviceName);
//
//		// Add a port to the Service
//		service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING,endpointAddress);
//		Dispatch<SOAPMessage> dispatch = service.createDispatch(portName,SOAPMessage.class, Service.Mode.MESSAGE);
//		// Use Dispatch as BindingProvider
//
//		StringBuilder sbrequest = new StringBuilder("<soapenv:Envelope xmlns:soapenv=");
//		sbrequest.append("\"http://schemas.xmlsoap.org/soap/envelope/\"");
//		sbrequest.append("  xmlns:ec=\"http://ec.gob.sri.ws.autorizacion\"");
//		sbrequest.append("><soapenv:Header/>  <soapenv:Body><ec:autorizacionComprobante><claveAccesoComprobante>");
//		sbrequest.append(clave);
//		sbrequest.append("</claveAccesoComprobante></ec:autorizacionComprobante></soapenv:Body></soapenv:Envelope>");
//		MessageFactory messageFactory;
//		messageFactory = MessageFactory.newInstance();
//		SOAPMessage mensajer = messageFactory.createMessage();
//		mensajer.saveChanges();
//		mensajer.getSOAPPart().setContent((Source) new StreamSource(new StringReader(sbrequest.toString())));
//		SOAPMessage result = dispatch.invoke(mensajer);
//		ByteArrayOutputStream baos = null;
//		String s = null;
//		try {
//			baos = new ByteArrayOutputStream();
//			result.writeTo(baos);
//			s = baos.toString();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return converToRespuestaAutorizacionDocumento(s);
//	}
	public RespuestaAutorizacionComprobante converToRespuestaAutorizacionDocumento(	String xmlRespuesta);// {
//		RespuestaAutorizacionComprobante respuestaAutorizacionComprobante = new RespuestaAutorizacionComprobante();
//		try {
//			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//			InputSource is = new InputSource();
//			is.setCharacterStream(new StringReader(xmlRespuesta));
//			Document doc = dBuilder.parse(is);
//			NodeList respAutCompXml = doc.getElementsByTagName("RespuestaAutorizacionComprobante");
//			for (int i = 0; i < respAutCompXml.getLength(); i++) {
//				Element elementRespAutCompXml = (Element) respAutCompXml.item(i);
//				respuestaAutorizacionComprobante.setClaveAccesoConsultada(elementRespAutCompXml.getElementsByTagName("claveAccesoConsultada").item(0).getFirstChild().getNodeValue());
//				respuestaAutorizacionComprobante.setNumeroComprobantes(Integer.parseInt(elementRespAutCompXml.getElementsByTagName("numeroComprobantes").item(0).getFirstChild().getNodeValue()));
//				NodeList autorizacionXml = elementRespAutCompXml.getElementsByTagName("autorizacion");
//				for (int j = 0; j < autorizacionXml.getLength(); j++) {
//					Element elementAutXml = (Element) autorizacionXml.item(j);
//					Autorizacion autorizacion = new Autorizacion();
//					autorizacion.setEstado(elementAutXml.getElementsByTagName("estado").item(0).getFirstChild().getNodeValue());
//					autorizacion.setNumeroAutorizacion(elementAutXml.getElementsByTagName("numeroAutorizacion").item(0).getFirstChild().getNodeValue());
//					// autorizacion.setFechaAutorizacion(elementAutXml.getElementsByTagName("fechaAutorizacion").item(0).getFirstChild().getNodeValue());
//					autorizacion.setAmbiente(elementAutXml.getElementsByTagName("ambiente").item(0).getFirstChild().getNodeValue());
//					autorizacion.setComprobante(elementAutXml.getElementsByTagName("comprobante").item(0).getFirstChild().getNodeValue());
//					// autorizacion.setInformacionAdicional(elementAutXml.getElementsByTagName("mensajes").item(0).getFirstChild().getNodeValue());
//					respuestaAutorizacionComprobante.getAutorizaciones().add(autorizacion);
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return respuestaAutorizacionComprobante;
//	}

}
