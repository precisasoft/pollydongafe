package ec.com.vipsoft.ce.backend.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.InputSource;

import ec.com.vipsoft.ce.comprobantesNeutros.FacturaDetalleBinding;
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.Autorizacion;
import ec.com.vipsoft.ce.utils.LlenadorNumeroComprobante;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteAutorizado;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico.TipoComprobante;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.sri.factura._v1_1_0.Factura;
import ec.com.vipsoft.sri.factura._v1_1_0.Factura.Detalles.Detalle;
import ec.com.vipsoft.sri.factura._v1_1_0.Impuesto;

@Stateless
public class LectorDetallesFacturaAprobada {
	@Inject
	private LlenadorNumeroComprobante llenadorNumeroComprobante;
	@PersistenceContext
	private EntityManager em;
	
	public List<FacturaDetalleBinding>leerDetallesDeFactura(String rucEmisor,String _numeroDocumento){
		String numeroComprobante=llenadorNumeroComprobante.llenarNumeroDocumento(_numeroDocumento);
		ArrayList<FacturaDetalleBinding>retorno=new ArrayList<FacturaDetalleBinding>();
		Query qentidad=em.createQuery("select e from Entidad e where e.ruc=?1");
		qentidad.setParameter(1, rucEmisor);
		//qentidad.setParameter(2, Boolean.TRUE);
		List<Entidad>listadoEntidad=qentidad.getResultList();
		if(!listadoEntidad.isEmpty()){
			Entidad entidad=em.getReference(Entidad.class, listadoEntidad.get(0).getId());
			Query qFactura=em.createQuery("select f from ComprobanteElectronico f where f.entidadEmisora=?1 and f.tipo=?2 and f.establecimiento=?3 and f.puntoEMision=?4 and f.secuencia=?5 and f.autorizado=?6");
			qFactura.setParameter(1, entidad);
			qFactura.setParameter(2, TipoComprobante.factura);
			StringTokenizer stt=new StringTokenizer(numeroComprobante,"-");
			qFactura.setParameter(3, stt.nextToken());
			qFactura.setParameter(4, stt.nextToken());
			qFactura.setParameter(5, stt.nextToken());
			qFactura.setParameter(6, Boolean.TRUE);
			List<ComprobanteElectronico>listadoComprobante=qFactura.getResultList();
			if(!listadoComprobante.isEmpty()){
				
				ComprobanteElectronico comprobante=listadoComprobante.get(0);
				ComprobanteAutorizado comprobanteAutorizado = comprobante.getComprobanteAutorizado();
				StringReader reader = new StringReader(new String(comprobanteAutorizado.getEnXML()));
				try {
					JAXBContext contextoautorizacion=JAXBContext.newInstance(Autorizacion.class);
					Unmarshaller unmarshaller=contextoautorizacion.createUnmarshaller();
					JAXBContext contextoFactura = JAXBContext.newInstance(Factura.class);			
					Unmarshaller unmarshallerFactura=contextoFactura.createUnmarshaller();
					Autorizacion autorizacion = (Autorizacion) unmarshaller.unmarshal(new InputSource(reader));
					Factura factura=(Factura)unmarshallerFactura.unmarshal(new InputSource(new StringReader(autorizacion.getComprobante())));
					if(factura!=null){
						for(Detalle detalle : factura.getDetalles().getDetalle()){
							for(Impuesto impuesto :detalle.getImpuestos().getImpuesto()){
								FacturaDetalleBinding bean=new FacturaDetalleBinding();
								bean.setCantidad(detalle.getCantidad());
								bean.setCodigo(detalle.getCodigoPrincipal());
								bean.setValorUnitario(detalle.getPrecioUnitario());
								bean.setDescripcion(detalle.getDescripcion());
								bean.setDescuento(detalle.getDescuento());
								bean.setCodigoIVA(impuesto.getCodigoPorcentaje());
								bean.setIva12(impuesto.getValor());
								bean.calcularValorTotal();
								if(!detalle.getDetallesAdicionales().getDetAdicional().isEmpty()){
									bean.setInfoAdicional1(detalle.getDetallesAdicionales().getDetAdicional().get(0).getNombre()+" "+detalle.getDetallesAdicionales().getDetAdicional().get(0).getValor());
								}
								retorno.add(bean);
							}							
							
						}
					}
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			
		}
		return retorno;
	}

}
