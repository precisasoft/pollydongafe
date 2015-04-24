package ec.com.vipsoft.ce.visorride;

import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.xml.sax.InputSource;

import ec.com.vipsoft.ce.backend.service.ContenedorReportesRide;
import ec.com.vipsoft.ce.backend.service.VerificadorRespuestaIndividual;
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.Autorizacion;
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.ConsultaAutorizacion;
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.RespuestaAutorizacionComprobante;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteAutorizado;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.sri.comprobanteRetencion._v1_0.ComprobanteRetencion;
import ec.com.vipsoft.sri.factura._v1_1_0.Factura;
import ec.com.vipsoft.sri.factura._v1_1_0.Factura.Detalles.Detalle;
import ec.com.vipsoft.sri.factura._v1_1_0.Impuesto;
import ec.com.vipsoft.sri.guiaremision._v1_1_0.Destinatario;
import ec.com.vipsoft.sri.guiaremision._v1_1_0.GuiaRemision;
import ec.com.vipsoft.sri.guiaremision._v1_1_0.GuiaRemision.InfoAdicional.CampoAdicional;
import ec.com.vipsoft.sri.notaDebito.v_1_0.NotaDebito;
import ec.com.vipsoft.sri.notaDebito.v_1_0.NotaDebito.InfoAdicional;
import ec.com.vipsoft.sri.notacredito._v1_1_0.NotaCredito;
@Stateless
public class CreadorRide {
	
	
	@EJB
	private ContenedorReportesRide contenedorRIDE;
	@Inject
	private UtilClaveAcceso utilClaveAcceso;
	@PersistenceContext
	private EntityManager em;
	@Inject
	private ConsultaAutorizacion consultaAutorizacion;
	@EJB
	private VerificadorRespuestaIndividual verificadorRespuestaIndividual;
	
	public byte[] obtenerPDF(String claveAcceso){
		JasperPrint print=obtenerPrint(claveAcceso);
		byte[] enPDF = null;
		try {
			enPDF = JasperExportManager.exportReportToPdf(print);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enPDF;
	}
	
	
	private JasperPrint obtenerPrint(String claveAcceso){		
		String ca = utilClaveAcceso.obtenerTipoDocumento(claveAcceso);
		String ruc = utilClaveAcceso.obtemerRucEmisor(claveAcceso);
		Query qEntidad = em.createQuery("select e from Entidad e where e.ruc=?1");
		qEntidad.setParameter(1, ruc);
		List<Entidad> listadoEntidad = qEntidad.getResultList();
		Autorizacion autorizacion = null;
		boolean tieneLogo = true;
		if (!listadoEntidad.isEmpty()) {
			tieneLogo = true;
			Entidad entidad = listadoEntidad.get(0);
			tieneLogo = entidad.isTieneLogo();

			// verificar si tenemos en bd la autorización ... si no buscarla
			// ahora.
			Query qcomprobante = em.createQuery("select c from ComprobanteElectronico c where c.claveAcceso=?1");
			qcomprobante.setParameter(1, claveAcceso);
			List<ComprobanteElectronico> listadoComprobante = qcomprobante.getResultList();
			JAXBContext contexto;
			try {
				contexto = JAXBContext.newInstance(Autorizacion.class);
				Unmarshaller unmarshaller = contexto.createUnmarshaller();
				if (!listadoComprobante.isEmpty()) {
					ComprobanteElectronico _comprobante = em.find(ComprobanteElectronico.class, listadoComprobante.get(0).getId());
					if (_comprobante.getComprobanteAutorizado() != null) {
						ComprobanteAutorizado cautorizado = _comprobante.getComprobanteAutorizado();
						StringReader reader = new StringReader(new String(cautorizado.getEnXML()));
						autorizacion = (Autorizacion) unmarshaller.unmarshal(new InputSource(reader));
					} else {
						autorizacion=verificadorRespuestaIndividual.verificarAutorizacion(claveAcceso);
						if(autorizacion.getEstado().equalsIgnoreCase("AUTORIZADO")){
							_comprobante.setAutorizado(true);
							_comprobante.setNumeroAutorizacion(autorizacion.getNumeroAutorizacion());
							_comprobante.setFechaAutorizacion(autorizacion.getFechaAutorizacion());
							ComprobanteAutorizado ca__=new ComprobanteAutorizado();
							ca__.setEnXML(autorizacion.getComprobante().getBytes());
							_comprobante.setComprobanteAutorizado(ca__);
						}
						
						
						
					}
				} else {
					try {
						RespuestaAutorizacionComprobante _consultarAutorizacion = consultaAutorizacion.consultarAutorizacion(claveAcceso);
						if (!_consultarAutorizacion.getAutorizaciones().isEmpty()) {
							if(!_consultarAutorizacion.getAutorizaciones().isEmpty())							
								autorizacion=_consultarAutorizacion.getAutorizaciones().get(0);
							
						}
					} catch (SOAPException e) {
						e.printStackTrace();
					}
				}
			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			tieneLogo = false;
			try {
				RespuestaAutorizacionComprobante _consultarAutorizacion = consultaAutorizacion.consultarAutorizacion(claveAcceso);
				if (!_consultarAutorizacion.getAutorizaciones().isEmpty()) {
					if(!_consultarAutorizacion.getAutorizaciones().isEmpty())							
						autorizacion=_consultarAutorizacion.getAutorizaciones().get(0);
					
				}
			} catch (SOAPException e) {
				e.printStackTrace();
			}
		}		
		Map<String,Object>parametros=new HashMap<>();
		if(autorizacion.getAmbiente()!=null){
			if(autorizacion.getAmbiente().equalsIgnoreCase("PRUEBAS")){
				parametros.put("ambiente", "PRUEBAS");
			}else{
				parametros.put("ambiente", "PRODUCCION");
			}	
		}else{
			if(utilClaveAcceso.esEnPruebas(claveAcceso)){
				parametros.put("ambiente", "PRUEBAS");
			}else{
				parametros.put("ambiente", "PRODUCCION");
			}
		}
		
		parametros.put("claveAcceso", claveAcceso);
		parametros.put("numeroAutorizacion","NO AUTORIZADO");
		if(autorizacion.getNumeroAutorizacion().length()>0){
			parametros.put("numeroAutorizacion",autorizacion.getNumeroAutorizacion());
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		parametros.put("fechaHoraAutorizacion", sdf.format(autorizacion.getFechaAutorizacion()));		
		JRBeanCollectionDataSource datos=null;				
		JasperReport reporte=obtenerReporte(claveAcceso,tieneLogo);
		
		
		try {
		
		switch (ca) {  
		case "01":	//factura
		{
			ArrayList<ReporteFacturaDetalleBean>detalles=new ArrayList<>();
			JAXBContext contextoFactura = JAXBContext.newInstance(Factura.class);			
			Unmarshaller unmarshallerFactura=contextoFactura.createUnmarshaller();
			Factura comprobante=(Factura)unmarshallerFactura.unmarshal(new InputSource(new StringReader(autorizacion.getComprobante())));
			parametros.put("numeroDocumento",comprobante.getInfoTributaria().getEstab()+"-"+comprobante.getInfoTributaria().getPtoEmi()+"-"+comprobante.getInfoTributaria().getSecuencial());
			parametros.put("rucEmisor", comprobante.getInfoTributaria().getRuc());
			parametros.put("razonSocialEmisor", comprobante.getInfoTributaria().getRazonSocial());
			parametros.put("direccionSucursal",comprobante.getInfoFactura().getDirEstablecimiento());
			
			int i=1;
			for(ec.com.vipsoft.sri.factura._v1_1_0.Factura.InfoAdicional.CampoAdicional campoAdicional:comprobante.getInfoAdicional().getCampoAdicional()){
				parametros.put("adicional"+i, campoAdicional.getNombre()+" "+campoAdicional.getValue());
				i++;
			}
			parametros.put("nombreComercial", comprobante.getInfoTributaria().getNombreComercial());
			parametros.put("direccionMatriz",comprobante.getInfoTributaria().getDirMatriz());
			if(comprobante.getInfoTributaria().getTipoEmision().equalsIgnoreCase("1")){
				parametros.put("tipoEmision","NORMAL");	
			}else{
				parametros.put("tipoEmision","CONTINGENCIA");
			}
						
			parametros.put("obligadoContabilidad",comprobante.getInfoFactura().getObligadoContabilidad().toString());			
			parametros.put("resolucionEspecial", comprobante.getInfoFactura().getContribuyenteEspecial());
				// ///////////////////////////////////////////////////////////7
				// /////////////////////////////////////////////////////////////
				// trampita con rocarsystem
				if (comprobante.getInfoTributaria().getRuc()
						.equalsIgnoreCase("1791739477001")) {
					parametros.put("resolucionEspecial", "826");
				}
			
			
			
			parametros.put("identificacionCliente",comprobante.getInfoFactura().getIdentificacionComprador());
			parametros.put("razonSocialCliente",comprobante.getInfoFactura().getRazonSocialComprador());
			parametros.put("fechaEmision",comprobante.getInfoFactura().getFechaEmision());
			
			
			
			parametros.put("guiaRemision",comprobante.getInfoFactura().getGuiaRemision());
			BigDecimal subtotal12=BigDecimal.ZERO;
			BigDecimal subtotalIva0=BigDecimal.ZERO;
			BigDecimal subtotalNoObjetoIVA=BigDecimal.ZERO;
			BigDecimal subtotalExentoIva=BigDecimal.ZERO;
			BigDecimal ice=BigDecimal.ZERO;
			BigDecimal iva12=BigDecimal.ZERO;
			BigDecimal totalDescuento=comprobante.getInfoFactura().getTotalDescuento();
			
			for( Detalle detalle :comprobante.getDetalles().getDetalle()){
				ReporteFacturaDetalleBean _detalle=new ReporteFacturaDetalleBean();
				_detalle.setCantidad(detalle.getCantidad());
				_detalle.setCodigoPrincipal(detalle.getCodigoPrincipal());
				_detalle.setCodigoAuxiliar(detalle.getCodigoAuxiliar());
				_detalle.setDescripcion(detalle.getDescripcion());
				_detalle.setDescuento(detalle.getDescuento());
				_detalle.setPrecioUnitario(detalle.getPrecioUnitario());
				_detalle.setPrecioTotal(detalle.getPrecioTotalSinImpuesto());
				if(detalle.getDetallesAdicionales()!=null){
					if(detalle.getDetallesAdicionales().getDetAdicional()!=null){
						if(!detalle.getDetallesAdicionales().getDetAdicional().isEmpty()){
							if(detalle.getDetallesAdicionales().getDetAdicional().get(0)!=null){
								_detalle.setDetalleAdicional(detalle.getDetallesAdicionales().getDetAdicional().get(0).getValor());
							}	
						}
							
					}
						
				}				
				//_detalle.setDetalleAdicional(detalle.getDetallesAdicionales().getDetAdicional().get(0).);
				
				for(Impuesto impuesto:detalle.getImpuestos().getImpuesto()){
					
					//iva
					if(impuesto.getCodigo().equalsIgnoreCase("2")){
						if(impuesto.getCodigoPorcentaje().equalsIgnoreCase("2")){
							subtotal12=subtotal12.add(impuesto.getBaseImponible());
							iva12=iva12.add(impuesto.getBaseImponible().multiply(new BigDecimal("0.12")));
							//iva12=iva12.add(impuesto.getValor());
						}
						if(impuesto.getCodigoPorcentaje().equalsIgnoreCase("0")){
							subtotalIva0=subtotalIva0.add(impuesto.getBaseImponible());
						}
						if(impuesto.getCodigoPorcentaje().equalsIgnoreCase("6")){
							subtotalNoObjetoIVA=subtotalNoObjetoIVA.add(impuesto.getBaseImponible());
						}
						if(impuesto.getCodigoPorcentaje().equalsIgnoreCase("7")){
							subtotalExentoIva=subtotalExentoIva.add(impuesto.getBaseImponible());
						}
					}
					//ice
					if(impuesto.getCodigo().equalsIgnoreCase("3")){
						ice=ice.add(impuesto.getValor());
								
					}
					//IRBPNR
					if(impuesto.getCodigo().equalsIgnoreCase("5")){
						
					}
				}
				detalles.add(_detalle);
			}
		//totalDescuento=totalDescuento.add(detalle.getDescuento());
		parametros.put("subtotal12",subtotal12);		
		parametros.put("subtotal0", subtotalIva0);
		parametros.put("subtotalNoObjetoIVA", subtotalNoObjetoIVA);
		parametros.put("subtotalExentoIVA", subtotalExentoIva);
		BigDecimal totalSinImpuestos=BigDecimal.ZERO;
		totalSinImpuestos=totalSinImpuestos.add(subtotal12);
		totalSinImpuestos=totalSinImpuestos.add(subtotalIva0);
		totalSinImpuestos=totalSinImpuestos.add(subtotalNoObjetoIVA);
		totalSinImpuestos=totalSinImpuestos.add(subtotalExentoIva);				
		parametros.put("subtotalSinImpuestos", totalSinImpuestos);
		parametros.put("totalDescuento", totalDescuento);
		parametros.put("ice",ice);
		parametros.put("iva12",iva12);
		parametros.put("irbpnr", BigDecimal.ZERO);
		parametros.put("propina",BigDecimal.ZERO);
		BigDecimal valorTotal=comprobante.getInfoFactura().getImporteTotal();
		parametros.put("valorTotal",valorTotal);
			
			//parte de los totales en los parametros ..
			//datos ....
			datos=new JRBeanCollectionDataSource(detalles);			
		}	
			break;
		case "04":  //nota de credito
		{
			ArrayList<ReporteFacturaDetalleBean>detalles=new ArrayList<>();
			JAXBContext contextoNc=JAXBContext.newInstance(NotaCredito.class);
			Unmarshaller unmarshallerNC=contextoNc.createUnmarshaller();
			NotaCredito comprobante=(NotaCredito)unmarshallerNC.unmarshal(new InputSource(new StringReader(autorizacion.getComprobante())));
			parametros.put("numeroDocumento",comprobante.getInfoTributaria().getEstab()+"-"+comprobante.getInfoTributaria().getPtoEmi()+"-"+comprobante.getInfoTributaria().getSecuencial());
			parametros.put("rucEmisor", comprobante.getInfoTributaria().getRuc());
			parametros.put("razonSocialEmisor", comprobante.getInfoTributaria().getRazonSocial());
			parametros.put("direccionSucursal",comprobante.getInfoNotaCredito().getDirEstablecimiento());
			
			int i=1;
			for(ec.com.vipsoft.sri.notacredito._v1_1_0.NotaCredito.InfoAdicional.CampoAdicional campoAdicional:comprobante.getInfoAdicional().getCampoAdicional()){
				parametros.put("adicional"+i, campoAdicional.getNombre()+" "+campoAdicional.getValue());
				i++;
			}
			parametros.put("nombreComercial", comprobante.getInfoTributaria().getNombreComercial());
			parametros.put("direccionMatriz",comprobante.getInfoTributaria().getDirMatriz());
			if(comprobante.getInfoTributaria().getTipoEmision().equalsIgnoreCase("1")){
				parametros.put("tipoEmision","NORMAL");	
			}else{
				parametros.put("tipoEmision","CONTINGENCIA");
			}
						
			parametros.put("obligadoContabilidad",comprobante.getInfoNotaCredito().getObligadoContabilidad().toString());			
			parametros.put("resolucionEspecial", comprobante.getInfoNotaCredito().getContribuyenteEspecial());
				// ///////////////////////////////////////////////////////////7
				// /////////////////////////////////////////////////////////////
				// trampita con rocarsystem
				if (comprobante.getInfoTributaria().getRuc().equalsIgnoreCase("1791739477001")) {
					parametros.put("resolucionEspecial", "826");
				}									
			parametros.put("identificacionCliente",comprobante.getInfoNotaCredito().getIdentificacionComprador());
			parametros.put("razonSocialCliente",comprobante.getInfoNotaCredito().getRazonSocialComprador());
			parametros.put("fechaEmision",comprobante.getInfoNotaCredito().getFechaEmision());
			if(comprobante.getInfoNotaCredito().getCodDocModificado().equalsIgnoreCase("01")){
				parametros.put("codDocModificado","FACTURA");
			}
			parametros.put("numDocModificado",comprobante.getInfoNotaCredito().getNumDocModificado());
			parametros.put("fechaEmisionDocSustento",comprobante.getInfoNotaCredito().getFechaEmisionDocSustento());
			parametros.put("motivo", comprobante.getInfoNotaCredito().getMotivo());
			BigDecimal subtotal12=BigDecimal.ZERO;
			BigDecimal subtotalIva0=BigDecimal.ZERO;
			BigDecimal subtotalNoObjetoIVA=BigDecimal.ZERO;
			BigDecimal subtotalExentoIva=BigDecimal.ZERO;
			BigDecimal ice=BigDecimal.ZERO;
			BigDecimal iva12=BigDecimal.ZERO;
			
			for(ec.com.vipsoft.sri.notacredito._v1_1_0.NotaCredito.Detalles.Detalle detalle :comprobante.getDetalles().getDetalle()){
				ReporteFacturaDetalleBean _detalle=new ReporteFacturaDetalleBean();
				_detalle.setCantidad(detalle.getCantidad());
				_detalle.setCodigoPrincipal(detalle.getCodigoInterno());
				_detalle.setCodigoAuxiliar(detalle.getCodigoAdicional());
				_detalle.setDescripcion(detalle.getDescripcion());
				_detalle.setDescuento(detalle.getDescuento());
				_detalle.setPrecioUnitario(detalle.getPrecioUnitario());
				_detalle.setPrecioTotal(detalle.getPrecioTotalSinImpuesto());
				if(detalle.getDetallesAdicionales()!=null){
					if(detalle.getDetallesAdicionales().getDetAdicional()!=null){
						if(!detalle.getDetallesAdicionales().getDetAdicional().isEmpty()){
							if(detalle.getDetallesAdicionales().getDetAdicional().get(0)!=null){
								_detalle.setDetalleAdicional(detalle.getDetallesAdicionales().getDetAdicional().get(0).getValor());
							}	
						}						
					}						
				}				
				//_detalle.setDetalleAdicional(detalle.getDetallesAdicionales().getDetAdicional().get(0).);				
				for(ec.com.vipsoft.sri.notacredito._v1_1_0.Impuesto impuesto:detalle.getImpuestos().getImpuesto()){					
					//iva
					if(impuesto.getCodigo().equalsIgnoreCase("2")){
						if(impuesto.getCodigoPorcentaje().equalsIgnoreCase("2")){
							subtotal12=subtotal12.add(impuesto.getBaseImponible());
							iva12=iva12.add(impuesto.getValor());
						}
						if(impuesto.getCodigoPorcentaje().equalsIgnoreCase("0")){
							subtotalIva0=subtotalIva0.add(impuesto.getBaseImponible());
						}
						if(impuesto.getCodigoPorcentaje().equalsIgnoreCase("6")){
							subtotalNoObjetoIVA=subtotalNoObjetoIVA.add(impuesto.getBaseImponible());
						}
						if(impuesto.getCodigoPorcentaje().equalsIgnoreCase("7")){
							subtotalExentoIva=subtotalExentoIva.add(impuesto.getBaseImponible());
						}
					}
					//ice
					if(impuesto.getCodigo().equalsIgnoreCase("3")){
						ice=ice.add(impuesto.getValor());							
					}
					//IRBPNR
					if(impuesto.getCodigo().equalsIgnoreCase("5")){
						
					}
				}
				detalles.add(_detalle);
			}
			parametros.put("subtotal12",subtotal12);
			parametros.put("subtotal0", subtotalIva0);
			parametros.put("subtotalNoObjetoIVA", subtotalNoObjetoIVA);
			parametros.put("subtotalExentoIVA", subtotalExentoIva);
			BigDecimal totalSinImpuestos=BigDecimal.ZERO;
			totalSinImpuestos=totalSinImpuestos.add(subtotal12);
			totalSinImpuestos=totalSinImpuestos.add(subtotalIva0);
			totalSinImpuestos=totalSinImpuestos.add(subtotalNoObjetoIVA);
			totalSinImpuestos=totalSinImpuestos.add(subtotalExentoIva);
			
			
			
			
			parametros.put("subtotalSinImpuestos", totalSinImpuestos);
			parametros.put("totalDescuento", new BigDecimal("0.00"));
			parametros.put("ice",ice);
			parametros.put("iva12",iva12);
			parametros.put("irbpnr", BigDecimal.ZERO);
			parametros.put("propina",BigDecimal.ZERO);
			BigDecimal grantotal=subtotal12.add(subtotalIva0).add(subtotalNoObjetoIVA).add(subtotalExentoIva).add(ice).add(iva12);			
			parametros.put("valorTotal",grantotal.setScale(2,RoundingMode.HALF_UP));
			datos=new JRBeanCollectionDataSource(detalles,false);
		}	
			break;
		case "05":  //nota de debito
		{
			JAXBContext contextoND=JAXBContext.newInstance(NotaDebito.class);
			Unmarshaller unmarshalleND=contextoND.createUnmarshaller();
			NotaDebito comprobante=(NotaDebito)unmarshalleND.unmarshal(new InputSource(new StringReader(autorizacion.getComprobante())));
			parametros.put("numeroDocumento",comprobante.getInfoTributaria().getDirMatriz());
			parametros.put("rucEmisor", comprobante.getInfoTributaria().getRuc());
			parametros.put("razonSocialEmisor", comprobante.getInfoTributaria().getRazonSocial());
			
			
			int i=1;
			for(InfoAdicional.CampoAdicional campoAdicional:comprobante.getInfoAdicional().getCampoAdicional()){
				parametros.put("adicional"+i, campoAdicional.getNombre()+" "+campoAdicional.getValue());
				i++;
			}
			parametros.put("nombreComercial", comprobante.getInfoTributaria().getNombreComercial());
			parametros.put("direccionMatriz",comprobante.getInfoTributaria().getDirMatriz());			
			parametros.put("tipoEmision",comprobante.getInfoTributaria().getTipoEmision());
			parametros.put("obligadoContabilidad",comprobante.getInfoNotaDebito().getObligadoContabilidad());
			parametros.put("resolucionEspecial",comprobante.getInfoNotaDebito().getContribuyenteEspecial());
			
		}
			break;
		case "06": //guia remision
		{
			JAXBContext contextoGuiaRemision=JAXBContext.newInstance(GuiaRemision.class);
			Unmarshaller unmarshallerGR=contextoGuiaRemision.createUnmarshaller();
			GuiaRemision comprobante=(GuiaRemision)unmarshallerGR.unmarshal(new InputSource(new StringReader(autorizacion.getComprobante())));
			parametros.put("numeroDocumento", comprobante.getInfoTributaria().getEstab()+"-"+comprobante.getInfoTributaria().getPtoEmi()+"-"+ comprobante.getInfoTributaria().getSecuencial());
			parametros.put("rucEmisor", comprobante.getInfoTributaria().getRuc());
			parametros.put("razonSocialEmisor", comprobante.getInfoTributaria().getRazonSocial());
			parametros.put("direccionSucursal", comprobante.getInfoGuiaRemision().getDirEstablecimiento());
			parametros.put("identificacionTransportista", comprobante.getInfoGuiaRemision().getRucTransportista());
			parametros.put("razonSocialTransportista",comprobante.getInfoGuiaRemision().getRazonSocialTransportista());
			parametros.put("placa",comprobante.getInfoGuiaRemision().getPlaca());
			parametros.put("puntoPartida",comprobante.getInfoGuiaRemision().getDirPartida());
			parametros.put("fechaInicioTransporte",comprobante.getInfoGuiaRemision().getFechaIniTransporte());
			parametros.put("fechaFinTransporte",comprobante.getInfoGuiaRemision().getFechaFinTransporte());
			parametros.put("fechaEmision",comprobante.getDestinatarios().getDestinatario().get(0).getFechaEmisionDocSustento());		
			
			
			int i=1;
			for(CampoAdicional campoAdicional:comprobante.getInfoAdicional().getCampoAdicional()){
				parametros.put("adicional"+i, campoAdicional.getNombre()+" "+campoAdicional.getValue());
				i++;
			}
			parametros.put("nombreComercial", comprobante.getInfoTributaria().getNombreComercial());
			parametros.put("direccionMatriz",comprobante.getInfoTributaria().getDirMatriz());	
			if (comprobante.getInfoTributaria().getTipoEmision().equalsIgnoreCase("1")) {
				parametros.put("tipoEmision", "NORMAL");
			} else {
				parametros.put("tipoEmision", "CONTINGENCIA");
			}
			parametros.put("obligadoContabilidad",comprobante.getInfoGuiaRemision().getObligadoContabilidad().toString());
			parametros.put("resolucionEspecial",comprobante.getInfoGuiaRemision().getContribuyenteEspecial());
			ArrayList<ReporteGuiaRemisionDetalleBean>_beans=new ArrayList<>();
			for(Destinatario destinatario : comprobante.getDestinatarios().getDestinatario()){
					for (ec.com.vipsoft.sri.guiaremision._v1_1_0.Detalle d : destinatario.getDetalles().getDetalle()) {						
						ReporteGuiaRemisionDetalleBean bean = new ReporteGuiaRemisionDetalleBean();
						bean.setIdentificacionDestinatario(destinatario.getIdentificacionDestinatario());
						bean.setRazonSocialDestinatario(destinatario.getRazonSocialDestinatario());
						bean.setDirDestinatario(destinatario.getDirDestinatario());
						bean.setMotivoTraslado(destinatario.getMotivoTraslado());
						bean.setCodDocSustento(destinatario.getCodDocSustento());
						bean.setNumDocSustento(destinatario.getNumDocSustento());
						bean.setNumAutDocSustento(destinatario.getNumAutDocSustento());
						bean.setFechaEmisionDocSustento(destinatario.getFechaEmisionDocSustento());
						
						bean.setFechaEmision(destinatario.getFechaEmisionDocSustento());
						bean.setCodigoInterno(d.getCodigoInterno());
						bean.setDescripcion(d.getDescripcion());
						bean.setCantidad(d.getCantidad());
						_beans.add(bean);
					}				
			}			
			datos=new JRBeanCollectionDataSource(_beans,false);		
		}
			break;
			case "07": // retencion
			{
				JAXBContext contextoRetencion = JAXBContext.newInstance(ComprobanteRetencion.class);
				Unmarshaller unmarshaller = contextoRetencion.createUnmarshaller();
				ComprobanteRetencion comprobante = (ComprobanteRetencion) unmarshaller.unmarshal(new InputSource(new StringReader(autorizacion.getComprobante())));
				parametros.put("numeroDocumento", comprobante.getInfoTributaria().getEstab()+"-"+comprobante.getInfoTributaria().getPtoEmi()+"-"+ comprobante.getInfoTributaria().getSecuencial());
				parametros.put("rucEmisor", comprobante.getInfoTributaria().getRuc());
				parametros.put("razonSocialEmisor", comprobante.getInfoTributaria().getRazonSocial());
				parametros.put("direccionSucursal", comprobante.getInfoCompRetencion().getDirEstablecimiento());
				parametros.put("razonSocialBeneficiario",comprobante.getInfoCompRetencion().getRazonSocialSujetoRetenido());
				parametros.put("rucBeneficiario",comprobante.getInfoCompRetencion().getIdentificacionSujetoRetenido());
				parametros.put("fechaEmision", comprobante.getInfoCompRetencion().getFechaEmision());

				int i = 1;
				for (ec.com.vipsoft.sri.comprobanteRetencion._v1_0.ComprobanteRetencion.InfoAdicional.CampoAdicional campoAdicional : comprobante.getInfoAdicional().getCampoAdicional()) {
					parametros.put("adicional" + i, campoAdicional.getNombre()+" "+ campoAdicional.getValue());
					i++;
				}
				parametros.put("nombreComercial", comprobante.getInfoTributaria().getNombreComercial());
				parametros.put("direccionMatriz", comprobante.getInfoTributaria().getDirMatriz());
				if (comprobante.getInfoTributaria().getTipoEmision().equalsIgnoreCase("1")) {
					parametros.put("tipoEmision", "NORMAL");
				} else {
					parametros.put("tipoEmision", "CONTINGENCIA");
				}

				parametros.put("obligadoContabilidad", comprobante.getInfoCompRetencion().getObligadoContabilidad().toString());
				parametros.put("resolucionEspecial", comprobante.getInfoCompRetencion().getContribuyenteEspecial());
				ArrayList<ReporteRetencionDetalleBean>_beans=new ArrayList<ReporteRetencionDetalleBean>();
				for(ec.com.vipsoft.sri.comprobanteRetencion._v1_0.Impuesto im:comprobante.getImpuestos().getImpuesto()){					
					ReporteRetencionDetalleBean bean=new ReporteRetencionDetalleBean();
					bean.setBaseImponible(im.getBaseImponible());
					if(im.getCodDocSustento().equalsIgnoreCase("01")){
						bean.setComprobante("FACTURA");
					}
					if(im.getCodDocSustento().equalsIgnoreCase("04")){
						bean.setComprobante("NC");
					}
					if(im.getCodDocSustento().equalsIgnoreCase("05")){
						bean.setComprobante("ND");
					}
					if(im.getCodDocSustento().equalsIgnoreCase("06")){
						bean.setComprobante("GUIA REMISION");
					}
					if(im.getCodDocSustento().equalsIgnoreCase("07")){
						bean.setComprobante("RETENCION");
					}
					bean.setEjercicioFiscal(comprobante.getInfoCompRetencion().getPeriodoFiscal());
					bean.setFechaEmision(im.getFechaEmisionDocSustento());
					if(im.getCodigo().equalsIgnoreCase("1")){
						bean.setImpuesto("RENTA");
					}
					if(im.getCodigo().equalsIgnoreCase("2")){
						bean.setImpuesto("IVA");
					}
					if(im.getCodigo().equalsIgnoreCase("6")){
						bean.setImpuesto("ISD");
					}
					bean.setNumero(im.getNumDocSustento());
					bean.setPorcentajeRetencion(im.getPorcentajeRetener());
					bean.setValorRetenido(im.getValorRetenido());
					_beans.add(bean);
				}
				datos=new JRBeanCollectionDataSource(_beans,false);
			}
				break;

			default:
				break;
			}
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(reporte, parametros,datos);
		} catch (JRException e) {
//			if(reporte==null){
//				if(utilClaveAcceso.obtenerTipoDocumento(claveAcceso).equalsIgnoreCase("04")){
//					reporte=JRL
//				}
		//	}
			e.printStackTrace();
		}
		return jasperPrint;		
	}
	
	
	private JasperReport obtenerReporte(String claveAcceso,boolean tiene__logo) {
		JasperReport reporte = null;	
		String tipoDocumento = utilClaveAcceso.obtenerTipoDocumento(claveAcceso);
		boolean tieneLogo = tiene__logo;
		switch (tipoDocumento) {
		case "01": // factura
			if (tieneLogo) {
				reporte = contenedorRIDE.getRideFactura();
			} else {
				reporte = contenedorRIDE.getRideFacturaSinLogo(); //para pruebas
				//reporte = contenedorRIDE.getRideFacturaSinLogo();  //correcto
			}
			break;
		case "04": // nota de credito
			if (tieneLogo) {
				reporte = contenedorRIDE.getRideNotaCredito();
			} else {
				reporte = contenedorRIDE.getRideNotaCreditoSinLogo();
			}
			break;
		case "05":// nota de debito
			if (tieneLogo) {
				reporte = contenedorRIDE.getRideNotaDebito();
			} else {
				reporte = contenedorRIDE.getRideNotaDebitoSinLogo();
			}

			break;
		case "06":// guia de remision
			if (tieneLogo) {
				reporte = contenedorRIDE.getRideGuiaRemision();
			} else {
				reporte = contenedorRIDE.getRideGuiaRemisionSinLogo();
			}

			break;
		case "07":// comprobante de retencion
			if (tieneLogo) {
				reporte = contenedorRIDE.getRideRetencion();
			} else {
				reporte = contenedorRIDE.getRideRetencionSinLogo();
			}

			break;
		default:
			reporte = contenedorRIDE.getRideFactura();
			break;
		}
		return reporte;
	}
}
