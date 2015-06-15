package ec.com.vipsoft.ce.visorride;

import java.io.StringReader;
import java.util.List;

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

import org.xml.sax.InputSource;

import ec.com.vipsoft.ce.backend.service.VerificadorRespuestaIndividual;
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.Autorizacion;
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.ConsultaAutorizacion;
import ec.com.vipsoft.ce.sri.autorizacion.wsclient.RespuestaAutorizacionComprobante;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.cryptografia.CryptoUtil;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteAutorizado;
import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico;
import ec.com.vipsoft.erp.abinadi.dominio.Entidad;

@Stateless
public class AutorizacionEnArchivoXML {
	@Inject
	private UtilClaveAcceso utilClaveAcceso;
	@PersistenceContext
	private EntityManager em;
	@Inject
	private ConsultaAutorizacion consultaAutorizacion;
	@EJB
	private VerificadorRespuestaIndividual verificadorRespuestaIndividual;
	@Inject
	private CryptoUtil cryptoUtil;

	public Autorizacion obtenerAutorizacion(String claveAcceso){
		Autorizacion autorizacion=null;
		String ca = utilClaveAcceso.obtenerTipoDocumento(claveAcceso);
		String ruc = cryptoUtil.encrypt(utilClaveAcceso.obtemerRucEmisor(claveAcceso));
		Query qEntidad = em.createQuery("select e from Entidad e where e.ruc=?1");
		qEntidad.setParameter(1, ruc);
		List<Entidad> listadoEntidad = qEntidad.getResultList();
		
		boolean tieneLogo = true;
		if (!listadoEntidad.isEmpty()) {
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
						String verificacionComprobante = verificadorRespuestaIndividual.verificarAutorizacionComprobante(claveAcceso,_comprobante.getId());
						autorizacion=(Autorizacion)unmarshaller.unmarshal(new InputSource(new StringReader(verificacionComprobante)));
						ComprobanteAutorizado cautorizado=new ComprobanteAutorizado();						
						cautorizado.setEnXML(verificacionComprobante.getBytes());		
						_comprobante.setComprobanteAutorizado(cautorizado);
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
		}
		return autorizacion;
	}
}
