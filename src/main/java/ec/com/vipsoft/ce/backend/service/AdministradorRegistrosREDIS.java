package ec.com.vipsoft.ce.backend.service;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import redis.clients.jedis.Jedis;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.sri.comprobanteRetencion._v1_0.ComprobanteRetencion;
import ec.com.vipsoft.sri.factura._v1_1_0.Factura;
import ec.com.vipsoft.sri.guiaremision._v1_1_0.GuiaRemision;
import ec.com.vipsoft.sri.notacredito._v1_1_0.NotaCredito;

@Stateless
public class AdministradorRegistrosREDIS implements Serializable{
	Logger loger=Logger.getLogger(AdministradorRegistrosREDIS.class.getCanonicalName());
	@Inject
	private UtilClaveAcceso utilClaveAcceso;
	public void registrarIntentoEnvio(String claveAcceso){
		StringBuilder sb=new StringBuilder();
		sb.append("E");
		if(utilClaveAcceso.esEnPruebas(claveAcceso)){
			sb.append("U");
		}else{
			sb.append("P");
		}
		sb.append(claveAcceso);
		Jedis jedis=new Jedis("localhost");
		jedis.set(sb.toString(), "1");
		jedis.expire(sb.toString(), 1800);		
	}
	public boolean estaEnviadoOEspera(String claveAcceso){
		StringBuilder sb=new StringBuilder();
		sb.append("E");
		if(utilClaveAcceso.esEnPruebas(claveAcceso)){
			sb.append("U");
		}else{
			sb.append("P");
		}
		sb.append(claveAcceso);
		Jedis jedis=new Jedis("localhost");
		return jedis.exists(sb.toString());
	}
	
	private static final long serialVersionUID = 6963060065091149909L;
	public String generarCadenaFactura(Factura f){
		StringBuilder sb=new StringBuilder("F:");
		sb.append(f.getInfoTributaria().getRuc());
		sb.append(":").append(f.getInfoTributaria().getEstab()+":"+f.getInfoTributaria().getPtoEmi()+":"+f.getInfoTributaria().getSecuencial());
		return sb.toString();
	}
	public String generarCadenaNotaCredito(NotaCredito nc){
		StringBuilder sb=new StringBuilder("NC:");
		sb.append(nc.getInfoTributaria().getRuc());
		sb.append(":").append(nc.getInfoTributaria().getEstab()+":"+nc.getInfoTributaria().getPtoEmi()+":"+nc.getInfoTributaria().getSecuencial());
		return sb.toString();
	}
	public String generarCadenaComprobanteRetencion(ComprobanteRetencion r){
		StringBuilder sb=new StringBuilder("NC:");
		sb.append(r.getInfoTributaria().getRuc());
		sb.append(":").append(r.getInfoTributaria().getEstab()+":"+r.getInfoTributaria().getPtoEmi()+":"+r.getInfoTributaria().getSecuencial());
		return sb.toString();
	}
	public String generarCadenaGuiaRemision(GuiaRemision g){
		StringBuilder sb=new StringBuilder("NC:");
		sb.append(g.getInfoTributaria().getRuc());
		sb.append(":").append(g.getInfoTributaria().getEstab()+":"+g.getInfoTributaria().getPtoEmi()+":"+g.getInfoTributaria().getSecuencial());
		return sb.toString();
	}
	/**
	 * registra el archivo xml sin firmar ...
	 * @param f
	 */
	public void registrarFactura(Factura f){
		
		Document convertidoEnDOM;
		try {
			convertidoEnDOM = DocumentBuilderFactory.newInstance()	.newDocumentBuilder().newDocument();
			JAXBContext contexto = JAXBContext.newInstance(Factura.class);
			Marshaller marshaller = contexto.createMarshaller();
			marshaller.marshal(f, convertidoEnDOM);
			DOMSource source = new DOMSource(convertidoEnDOM);
			StringWriter writer = new StringWriter();
			StreamResult result2 = new StreamResult(writer);
			
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.transform(source, result2);			
			
			Jedis jedis=new Jedis("localhost");
			
			String claveFactura=generarCadenaFactura(f);
			String documentoSinFirmar=writer.toString();
			loger.info("vamos a guardar en redis "+claveFactura+"   :     "+documentoSinFirmar);
			jedis.set(claveFactura, documentoSinFirmar);
			jedis.expire(claveFactura, 172800);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//JAXBContext contexto=new 
	//	String generarDocumento=
	//	jedis.set(generarCadenaFactura(f), value)
	}
	public void borrarFacturaXML(String claveAcceso) {
		StringBuilder sb=new StringBuilder();
		sb.append("F:");
		sb.append(utilClaveAcceso.obtemerRucEmisor(claveAcceso));
		sb.append(":").append(utilClaveAcceso.obtenerCodigoEstablecimiento(claveAcceso)).append(":").append(utilClaveAcceso.obtenerCodigoPuntoEmision(claveAcceso)).append(":").append(utilClaveAcceso.obtenerSecuanciaDocumento(claveAcceso));
		Jedis jedis=new Jedis("localhost");
		jedis.expire(sb.toString(),5);
		
//		StringBuilder sb=new StringBuilder("F:");
//		sb.append(f.getInfoTributaria().getRuc());
//		sb.append(":").append(f.getInfoTributaria().getEstab()+":"+f.getInfoTributaria().getPtoEmi()+":"+f.getInfoTributaria().getSecuencial());
//		return sb.toString();
		
	}
}
