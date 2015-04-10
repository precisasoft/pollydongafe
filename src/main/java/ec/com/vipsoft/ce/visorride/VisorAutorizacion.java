package ec.com.vipsoft.ce.visorride;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import ec.com.vipsoft.ce.sri.autorizacion.wsclient.Autorizacion;

/**
 * Servlet implementation class VisorAutorizacion
 */
@WebServlet("/VisorAutorizacion")
public class VisorAutorizacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private AutorizacionEnArchivoXML buscardorAutorizacion;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisorAutorizacion() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String claveAcceso=request.getParameter("claveAcceso");
		if(claveAcceso!=null){
			Autorizacion autorizacion=buscardorAutorizacion.obtenerAutorizacion(claveAcceso);
			if(autorizacion!=null){
				StringBuilder nombreArchivo=new StringBuilder("inline; filename=");
				nombreArchivo.append(claveAcceso);
				nombreArchivo.append(".xml");
				
				try {
					Document documento=DocumentBuilderFactory.newInstance()	.newDocumentBuilder().newDocument();
					JAXBContext contexto=JAXBContext.newInstance(Autorizacion.class);
					Marshaller marshaller = contexto.createMarshaller();
					marshaller.marshal(autorizacion, documento);
					response.setContentType ("application/xml");
					////////////////////////////////////////////////////////777
					Source source = new DOMSource(documento);
					ByteArrayOutputStream out2 = new ByteArrayOutputStream();
					Result result = new StreamResult(out2);
					TransformerFactory factory = TransformerFactory.newInstance();
					Transformer transformer = factory.newTransformer();
					transformer.transform(source, result);

					// convierte en String el firmado
					DOMSource domSource = new DOMSource(documento);
					StringWriter writer = new StringWriter();
					StreamResult result2 = new StreamResult(writer);
					//	TransformerFactory tf = TransformerFactory.newInstance();
					//	Transformer transformer2 = tf.newTransformer();
					transformer.transform(domSource, result2);
					StringBuilder sb=new StringBuilder(); 
					sb.append(writer.toString());
					
					
					/////////////////////////////////////////////////////777
					byte[] datosPdf=sb.toString().getBytes();
					
					response.setHeader ("Content-disposition",nombreArchivo.toString());
					response.setHeader ("Cache-Control", "max-age=30");
					response.setHeader ("Pragma", "No-cache");
					response.setDateHeader ("Expires", 0);
					response.setContentLength (datosPdf.length);
					ServletOutputStream out;
					out = response.getOutputStream ();

					out.write (datosPdf, 0, datosPdf.length);
					out.flush ();
					out.close ();
					
					
//				
//					
					
				} catch (JAXBException | ParserConfigurationException |  TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
					
			}
			
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
