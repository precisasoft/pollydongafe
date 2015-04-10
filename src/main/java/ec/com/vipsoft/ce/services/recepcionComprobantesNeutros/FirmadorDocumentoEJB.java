package ec.com.vipsoft.ce.services.recepcionComprobantesNeutros;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.firmaJava.role.SimpleClaimedRole;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.IPassStoreKS;
import es.mityc.javasign.pkstore.keystore.KSStore;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;

public class FirmadorDocumentoEJB {

	public String firmarDocumento(Document documentoAFirmar,String rucEntidad,byte[] bytes,String contrasena) throws Exception{


      StringBuilder sbFacturaFirmadaEnTexto = new StringBuilder();
      ByteArrayInputStream in = new ByteArrayInputStream(bytes);
      KeyStore ks = KeyStore.getInstance("PKCS12");
      ks.load(in,contrasena.toCharArray());
//      JAXBContext context;
//      context = JAXBContext.newInstance(Factura.class);
//      Marshaller m = context.createMarshaller();
//      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//      m.marshal(facturaRetorno, documentoAFirmar);
      DataToSign dataToSign = preparaDoc2Sign();
      dataToSign.addObject(new ObjectToSign(new InternObjectToSign("comprobante"), "contenido comprobante", null, "text/xml", null));
      dataToSign.setDocument(documentoAFirmar);
      FirmaXML firmaXml = new FirmaXML();
 //     final String contrasena = entidad.getPasswordp12();
      X509Certificate certificado = (X509Certificate) ks.getCertificate(ks.aliases().nextElement());
      IPKStoreManager storeManager = new KSStore(ks, new IPassStoreKS() {

          @Override
          public char[] getPassword(X509Certificate argumento0, String argumento1) {
              // TODO Auto-generated method stub
              return contrasena.toCharArray();
          }
      });
      PrivateKey privateKey = storeManager.getPrivateKey(certificado);
      Provider proveedor = storeManager.getProvider(certificado);
      Object[] datosfirmados = firmaXml.signFile(certificado, dataToSign, privateKey, proveedor);
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
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer2 = tf.newTransformer();
      transformer.transform(domSource, result2);
      sbFacturaFirmadaEnTexto.append(writer.toString());
      return sbFacturaFirmadaEnTexto.toString();     
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
