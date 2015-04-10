package ec.com.vipsoft.ce.backend.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.com.vipsoft.erp.abinadi.dominio.Entidad;
import ec.com.vipsoft.erp.abinadi.dominio.Establecimiento;
import ec.com.vipsoft.erp.abinadi.dominio.PuntoVenta;

@Stateless
public class GeneradorClaveAccesoPorEntidad {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GeneradorClaveAcceso generadorReal;
    @EJB
    private VerificadorIndisponibilidad verificadorDisponibilidad;

    public String generarClaveAccesoFactura(String rucEmisor,  String codigoEstablecimiento, String codigoPuntoVenta,String secuenciaDocumento) {
        String claveAcceso = null;
        Query q = em.createQuery("select e from Entidad e where e.ruc=?1");
        q.setParameter(1, rucEmisor);
        List<Entidad> listadoEntidad = q.getResultList();
        if (listadoEntidad.isEmpty()) {
            claveAcceso = "entidad o ruc no registrado";
        } else {
            Entidad entidad = em.find(Entidad.class, listadoEntidad.get(0).getId());
            Query qEstablecimiento = em.createQuery("select s from Establecimiento s where s.entidad.id=?1 and s.codigo=?2");
            qEstablecimiento.setParameter(1, entidad.getId());
            qEstablecimiento.setParameter(2, codigoEstablecimiento);
            List<Establecimiento> listaEstablecimiento = qEstablecimiento.getResultList();
            if (!listaEstablecimiento.isEmpty()) {
                Establecimiento _Establecimiento = em.find(Establecimiento.class, listaEstablecimiento.get(0).getId());
                for (PuntoVenta ptoe : _Establecimiento.getPos()) {
                    if (ptoe.getCodigoPuntoVenta().equalsIgnoreCase(codigoPuntoVenta)) {
                        PuntoVenta elpuntoDeVenta = em.find(PuntoVenta.class, ptoe.getId());
                        claveAcceso = generadorReal.generarClaveAccesoFactura(rucEmisor, Integer.valueOf(codigoEstablecimiento), Integer.valueOf(codigoPuntoVenta), entidad.isFacturaEnPruebas(), Long.valueOf(secuenciaDocumento), entidad.siguienteCAFactura());
                        break;
                    }
                }
            }
        }
        if (verificadorDisponibilidad.estamosEnContingencia()) {
            claveAcceso = generadorReal.generarClaveAccesoContingencia(claveAcceso);
        }
        return claveAcceso;
    }
    
    public String generarClaveAccesoFactura(String rucEmisor,  String codigoEstablecimiento, String codigoPuntoVenta) {
        String claveAcceso = null;
        Query q = em.createQuery("select e from Entidad e where e.ruc=?1");
        q.setParameter(1, rucEmisor);
        List<Entidad> listadoEntidad = q.getResultList();
        if (listadoEntidad.isEmpty()) {
            claveAcceso = "entidad o ruc no registrado";
        } else {
            Entidad entidad = em.find(Entidad.class, listadoEntidad.get(0).getId());
            Query qEstablecimiento = em.createQuery("select s from Establecimiento s where s.entidad.id=?1 and s.codigo=?2");
            qEstablecimiento.setParameter(1, entidad.getId());
            qEstablecimiento.setParameter(2, codigoEstablecimiento);
            List<Establecimiento> listaEstablecimiento = qEstablecimiento.getResultList();
            if (!listaEstablecimiento.isEmpty()) {
                Establecimiento _Establecimiento = em.find(Establecimiento.class, listaEstablecimiento.get(0).getId());
                for (PuntoVenta ptoe : _Establecimiento.getPos()) {
                    if (ptoe.getCodigoPuntoVenta().equalsIgnoreCase(codigoPuntoVenta)) {
                        PuntoVenta elpuntoDeVenta = em.find(PuntoVenta.class, ptoe.getId());
                        claveAcceso = generadorReal.generarClaveAccesoFactura(rucEmisor, Integer.valueOf(codigoEstablecimiento), Integer.valueOf(codigoPuntoVenta), entidad.isFacturaEnPruebas(), elpuntoDeVenta.siguienteSecuenciaFactura(), entidad.siguienteCAFactura());
                        break;
                    }
                }
            }
        }
        if (verificadorDisponibilidad.estamosEnContingencia()) {
            claveAcceso = generadorReal.generarClaveAccesoContingencia(claveAcceso);
        }
        return claveAcceso;
    }

    
    public String generarClaveAccesoComprobanteRetencion( String rucEmisor,  String codigoEstablecimiento,  String codigoPuntoVenta) {
        String claveAcceso = null;
        Query q = em.createQuery("select e from Entidad e where e.ruc=?1");
        q.setParameter(1, rucEmisor);
        List<Entidad> listadoEntidad = q.getResultList();
        if (listadoEntidad.isEmpty()) {
            claveAcceso = "entidad o ruc no registrado";
        } else {
            Entidad entidad = em.find(Entidad.class, listadoEntidad.get(0).getId());
            Query qEstablecimiento = em.createQuery("select s from Establecimiento s where s.entidad.id=?1 and s.codigo=?2");
            qEstablecimiento.setParameter(1, entidad.getId());
            qEstablecimiento.setParameter(2, codigoEstablecimiento);
            List<Establecimiento> listaEstablecimiento = qEstablecimiento.getResultList();
            if (!listaEstablecimiento.isEmpty()) {
                Establecimiento _Establecimiento = em.find(Establecimiento.class, listaEstablecimiento.get(0).getId());
                for (PuntoVenta ptoe : _Establecimiento.getPos()) {
                    if (ptoe.getCodigoPuntoVenta().equalsIgnoreCase(codigoPuntoVenta)) {
                        PuntoVenta elpuntoDeVenta = em.find(PuntoVenta.class, ptoe.getId());
                        boolean enPruebas=entidad.isComprobanteRetencionEnPruebas();
                        long siguienteSecuenciaRetencion = elpuntoDeVenta.siguienteSecuenciaRetencion();
                        Integer  siguienteCARetencion  = entidad.siguienteCARetencion();
                        claveAcceso = generadorReal.generarClaveAccesoComprobanteRetencion(rucEmisor, Integer.valueOf(codigoEstablecimiento), Integer.valueOf(codigoPuntoVenta), enPruebas,siguienteSecuenciaRetencion ,siguienteCARetencion);
                        break;
                    }
                }
            }
        }
        if (verificadorDisponibilidad.estamosEnContingencia()) {
            claveAcceso = generadorReal.generarClaveAccesoContingencia(claveAcceso);
        }
        return claveAcceso;
    }
    public String generarClaveAccesoComprobanteRetencion( String rucEmisor,  String codigoEstablecimiento,  String codigoPuntoVenta,String secuenciaDocumento) {
        String claveAcceso = null;
        Query q = em.createQuery("select e from Entidad e where e.ruc=?1");
        q.setParameter(1, rucEmisor);
        List<Entidad> listadoEntidad = q.getResultList();
        if (listadoEntidad.isEmpty()) {
            claveAcceso = "entidad o ruc no registrado";
        } else {
            Entidad entidad = em.find(Entidad.class, listadoEntidad.get(0).getId());
            Query qEstablecimiento = em.createQuery("select s from Establecimiento s where s.entidad.id=?1 and s.codigo=?2");
            qEstablecimiento.setParameter(1, entidad.getId());
            qEstablecimiento.setParameter(2, codigoEstablecimiento);
            List<Establecimiento> listaEstablecimiento = qEstablecimiento.getResultList();
            if (!listaEstablecimiento.isEmpty()) {
                Establecimiento _Establecimiento = em.find(Establecimiento.class, listaEstablecimiento.get(0).getId());
                for (PuntoVenta ptoe : _Establecimiento.getPos()) {
                    if (ptoe.getCodigoPuntoVenta().equalsIgnoreCase(codigoPuntoVenta)) {
                        PuntoVenta elpuntoDeVenta = em.find(PuntoVenta.class, ptoe.getId());
                        boolean enPruebas=entidad.isComprobanteRetencionEnPruebas();
                        long siguienteSecuenciaRetencion = Long.valueOf(secuenciaDocumento);
                        Integer  siguienteCARetencion  = entidad.siguienteCARetencion();
                        claveAcceso = generadorReal.generarClaveAccesoComprobanteRetencion(rucEmisor, Integer.valueOf(codigoEstablecimiento), Integer.valueOf(codigoPuntoVenta), enPruebas,siguienteSecuenciaRetencion ,siguienteCARetencion);
                        break;
                    }
                }
            }
        }
        if (verificadorDisponibilidad.estamosEnContingencia()) {
            claveAcceso = generadorReal.generarClaveAccesoContingencia(claveAcceso);
        }
        return claveAcceso;
    }

    public String generarClaveAccesoGuiaRemision(String rucEmisor, String codigoEstablecimiento, String codigoPuntoVenta) {
        String claveAcceso = null;
        Query q = em.createQuery("select e from Entidad e where e.ruc=?1");
        q.setParameter(1, rucEmisor);
        List<Entidad> listadoEntidad = q.getResultList();
        if (listadoEntidad.isEmpty()) {
            claveAcceso = "entidad o ruc no registrado";
        } else {
            Entidad entidad = em.find(Entidad.class, listadoEntidad.get(0).getId());
            Query qEstablecimiento = em.createQuery("select s from Establecimiento s where s.entidad.id=?1 and s.codigo=?2");
            qEstablecimiento.setParameter(1, entidad.getId());
            qEstablecimiento.setParameter(2, codigoEstablecimiento);
            List<Establecimiento> listaEstablecimiento = qEstablecimiento.getResultList();
            if (!listaEstablecimiento.isEmpty()) {
                Establecimiento _Establecimiento = em.find(Establecimiento.class, listaEstablecimiento.get(0).getId());
                for (PuntoVenta ptoe : _Establecimiento.getPos()) {
                    if (ptoe.getCodigoPuntoVenta().equalsIgnoreCase(codigoPuntoVenta)) {
                        PuntoVenta elpuntoDeVenta = em.find(PuntoVenta.class, ptoe.getId());                        
                        claveAcceso = generadorReal.generarClaveAccesoGuiaRemision(rucEmisor, Integer.valueOf(codigoEstablecimiento), Integer.valueOf(codigoPuntoVenta), entidad.isGuiaRemisionEnPruebas(), elpuntoDeVenta.siguienteSecuenciaGuiaRemision(), entidad.siguienteCAGuiaRemision());
                        break;
                    }
                }
            }
        }
        if (verificadorDisponibilidad.estamosEnContingencia()) {
            claveAcceso = generadorReal.generarClaveAccesoContingencia(claveAcceso);
        }
        return claveAcceso;
    }
    public String generarClaveAccesoGuiaRemision(String rucEmisor, String codigoEstablecimiento, String codigoPuntoVenta,String secuenciaDoc) {
        String claveAcceso = null;
        Query q = em.createQuery("select e from Entidad e where e.ruc=?1");
        q.setParameter(1, rucEmisor);
        List<Entidad> listadoEntidad = q.getResultList();
        if (listadoEntidad.isEmpty()) {
            claveAcceso = "entidad o ruc no registrado";
        } else {
            Entidad entidad = em.find(Entidad.class, listadoEntidad.get(0).getId());
            Query qEstablecimiento = em.createQuery("select s from Establecimiento s where s.entidad.id=?1 and s.codigo=?2");
            qEstablecimiento.setParameter(1, entidad.getId());
            qEstablecimiento.setParameter(2, codigoEstablecimiento);
            List<Establecimiento> listaEstablecimiento = qEstablecimiento.getResultList();
            if (!listaEstablecimiento.isEmpty()) {
                Establecimiento _Establecimiento = em.find(Establecimiento.class, listaEstablecimiento.get(0).getId());
                for (PuntoVenta ptoe : _Establecimiento.getPos()) {
                    if (ptoe.getCodigoPuntoVenta().equalsIgnoreCase(codigoPuntoVenta)) {
                        PuntoVenta elpuntoDeVenta = em.find(PuntoVenta.class, ptoe.getId());                        
                        claveAcceso = generadorReal.generarClaveAccesoGuiaRemision(rucEmisor, Integer.valueOf(codigoEstablecimiento), Integer.valueOf(codigoPuntoVenta), entidad.isGuiaRemisionEnPruebas(), Long.valueOf(secuenciaDoc), entidad.siguienteCAGuiaRemision());
                        break;
                    }
                }
            }
        }
        if (verificadorDisponibilidad.estamosEnContingencia()) {
            claveAcceso = generadorReal.generarClaveAccesoContingencia(claveAcceso);
        }
        return claveAcceso;
    }
    
    public String generarClaveAccesoNotaCredito( String rucEmisor,  String codigoEstablecimiento, String codigoPuntoVenta) {
        String claveAcceso = null;
        Query q = em.createQuery("select e from Entidad e where e.ruc=?1");
        q.setParameter(1, rucEmisor);
        List<Entidad> listadoEntidad = q.getResultList();
        if (listadoEntidad.isEmpty()) {
            claveAcceso = "entidad o ruc no registrado";
        } else {
            Entidad entidad = em.find(Entidad.class, listadoEntidad.get(0).getId());
            Query qEstablecimiento = em.createQuery("select s from Establecimiento s where s.entidad.id=?1 and s.codigo=?2");
            qEstablecimiento.setParameter(1, entidad.getId());
            qEstablecimiento.setParameter(2, codigoEstablecimiento);
            List<Establecimiento> listaEstablecimiento = qEstablecimiento.getResultList();
            if (!listaEstablecimiento.isEmpty()) {
                Establecimiento _Establecimiento = em.find(Establecimiento.class, listaEstablecimiento.get(0).getId());
                for (PuntoVenta ptoe : _Establecimiento.getPos()) {
                    if (ptoe.getCodigoPuntoVenta().equalsIgnoreCase(codigoPuntoVenta)) {
                        PuntoVenta elpuntoDeVenta = em.find(PuntoVenta.class, ptoe.getId());
                        claveAcceso = generadorReal.generarClaveAccesoNotaCredito(rucEmisor, Integer.valueOf(codigoEstablecimiento), Integer.valueOf(codigoPuntoVenta), entidad.isNotaCreditoEnPruebas(), elpuntoDeVenta.siguienteSecuenciaFactura(), entidad.siguienteCAFactura());
                        break;
                    }
                }
            }
        }
        if (verificadorDisponibilidad.estamosEnContingencia()) {
            claveAcceso = generadorReal.generarClaveAccesoContingencia(claveAcceso);
        }
        return claveAcceso;
    }
    public String generarClaveAccesoNotaCredito( String rucEmisor,  String codigoEstablecimiento, String codigoPuntoVenta,String secuenciaDoc) {
        String claveAcceso = null;
        Query q = em.createQuery("select e from Entidad e where e.ruc=?1");
        q.setParameter(1, rucEmisor);
        List<Entidad> listadoEntidad = q.getResultList();
        if (listadoEntidad.isEmpty()) {
            claveAcceso = "entidad o ruc no registrado";
        } else {
            Entidad entidad = em.find(Entidad.class, listadoEntidad.get(0).getId());
            Query qEstablecimiento = em.createQuery("select s from Establecimiento s where s.entidad.id=?1 and s.codigo=?2");
            qEstablecimiento.setParameter(1, entidad.getId());
            qEstablecimiento.setParameter(2, codigoEstablecimiento);
            List<Establecimiento> listaEstablecimiento = qEstablecimiento.getResultList();
            if (!listaEstablecimiento.isEmpty()) {
                Establecimiento _Establecimiento = em.find(Establecimiento.class, listaEstablecimiento.get(0).getId());
                for (PuntoVenta ptoe : _Establecimiento.getPos()) {
                    if (ptoe.getCodigoPuntoVenta().equalsIgnoreCase(codigoPuntoVenta)) {
                        PuntoVenta elpuntoDeVenta = em.find(PuntoVenta.class, ptoe.getId());
                        claveAcceso = generadorReal.generarClaveAccesoNotaCredito(rucEmisor, Integer.valueOf(codigoEstablecimiento), Integer.valueOf(codigoPuntoVenta), entidad.isNotaCreditoEnPruebas(), Long.valueOf(secuenciaDoc), entidad.siguienteCAFactura());
                        break;
                    }
                }
            }
        }
        if (verificadorDisponibilidad.estamosEnContingencia()) {
            claveAcceso = generadorReal.generarClaveAccesoContingencia(claveAcceso);
        }
        return claveAcceso;
    }

    public String generarClaveAccesoNotaDebito( String rucEmisor,  String codigoEstablecimiento,  String codigoPuntoVenta) {
        String claveAcceso = null;
        Query q = em.createQuery("select e from Entidad e where e.ruc=?1");
        q.setParameter(1, rucEmisor);
        List<Entidad> listadoEntidad = q.getResultList();
        if (listadoEntidad.isEmpty()) {
            claveAcceso = "entidad o ruc no registrado";
        } else {
            Entidad entidad = em.find(Entidad.class, listadoEntidad.get(0).getId());
            Query qEstablecimiento = em.createQuery("select s from Establecimiento s where s.entidad.id=?1 and s.codigo=?2");
            qEstablecimiento.setParameter(1, entidad.getId());
            qEstablecimiento.setParameter(2, codigoEstablecimiento);
            List<Establecimiento> listaEstablecimiento = qEstablecimiento.getResultList();
            if (!listaEstablecimiento.isEmpty()) {
                Establecimiento _Establecimiento = em.find(Establecimiento.class, listaEstablecimiento.get(0).getId());
                for (PuntoVenta ptoe : _Establecimiento.getPos()) {
                    if (ptoe.getCodigoPuntoVenta().equalsIgnoreCase(codigoPuntoVenta)) {
                        PuntoVenta elpuntoDeVenta = em.find(PuntoVenta.class, ptoe.getId());
                        claveAcceso = generadorReal.generarClaveAccesoNotaDebito(rucEmisor, Integer.valueOf(codigoEstablecimiento), Integer.valueOf(codigoPuntoVenta), entidad.isNotaDebitoEnPruebas(), elpuntoDeVenta.siguienteSecuenciaNotaDebito(), entidad.siguienteCANotaDebito());
                        break;
                    }
                }
            }
        }
        if (verificadorDisponibilidad.estamosEnContingencia()) {
            claveAcceso = generadorReal.generarClaveAccesoContingencia(claveAcceso);
        }
        return claveAcceso;
    }

    public String generarClaveAccesoNotaDebito( String rucEmisor,  String codigoEstablecimiento,  String codigoPuntoVenta,String secuencaDoc) {
        String claveAcceso = null;
        Query q = em.createQuery("select e from Entidad e where e.ruc=?1");
        q.setParameter(1, rucEmisor);
        List<Entidad> listadoEntidad = q.getResultList();
        if (listadoEntidad.isEmpty()) {
            claveAcceso = "entidad o ruc no registrado";
        } else {
            Entidad entidad = em.find(Entidad.class, listadoEntidad.get(0).getId());
            Query qEstablecimiento = em.createQuery("select s from Establecimiento s where s.entidad.id=?1 and s.codigo=?2");
            qEstablecimiento.setParameter(1, entidad.getId());
            qEstablecimiento.setParameter(2, codigoEstablecimiento);
            List<Establecimiento> listaEstablecimiento = qEstablecimiento.getResultList();
            if (!listaEstablecimiento.isEmpty()) {
                Establecimiento _Establecimiento = em.find(Establecimiento.class, listaEstablecimiento.get(0).getId());
                for (PuntoVenta ptoe : _Establecimiento.getPos()) {
                    if (ptoe.getCodigoPuntoVenta().equalsIgnoreCase(codigoPuntoVenta)) {
                        PuntoVenta elpuntoDeVenta = em.find(PuntoVenta.class, ptoe.getId());
                        claveAcceso = generadorReal.generarClaveAccesoNotaDebito(rucEmisor, Integer.valueOf(codigoEstablecimiento), Integer.valueOf(codigoPuntoVenta), entidad.isNotaDebitoEnPruebas(), Long.valueOf(secuencaDoc), entidad.siguienteCANotaDebito());
                        break;
                    }
                }
            }
        }
        if (verificadorDisponibilidad.estamosEnContingencia()) {
            claveAcceso = generadorReal.generarClaveAccesoContingencia(claveAcceso);
        }
        return claveAcceso;
    }

    public String generarClaveAccesoContingencia( String claveAccesoNormal) {
        return generadorReal.generarClaveAccesoContingencia(claveAccesoNormal);
    }

//	private long obtenerNumeroDocumentoFactura(String rucEmpresa,String codigoEstablecimiento,String codigoPuntoVenta){
//		long retorno=0;
//		 Query q = em.createQuery("select e from Entidad e where e.ruc=?1");
//	        q.setParameter(1, rucEmpresa);
//	        List<Entidad> listadoEntidad = q.getResultList();
//	        if (listadoEntidad.isEmpty()) {
//	            
//	//        	retorno.append("entidad o ruc no registrado");
//	        } else {
//	            Entidad entidad = em.find(Entidad.class, listadoEntidad.get(0).getId());
//	            Query qEstablecimiento = em.createQuery("select s from Establecimiento s where s.entidad.id=?1 and s.codigo=?2");
//	            qEstablecimiento.setParameter(1, entidad.getId());
//	            qEstablecimiento.setParameter(2, codigoEstablecimiento);
//	            List<Establecimiento> listaEstablecimiento = qEstablecimiento.getResultList();
//	            if (!listaEstablecimiento.isEmpty()) {
//	                Establecimiento _Establecimiento = em.find(Establecimiento.class, listaEstablecimiento.get(0).getId());
//	                for (PuntoVenta ptoe : _Establecimiento.getPos()) {
//	                	if(ptoe.getCodigoPuntoVenta().equalsIgnoreCase(codigoPuntoVenta)){
//	                	  PuntoVenta elpuntoDeVenta=em.find(PuntoVenta.class, ptoe.getId());
//	                	  retorno=elpuntoDeVenta.siguienteSecuenciaFactura();
//	                	  
//	                	}
//	                }
//	            }
//	        }
//
//		return retorno;
//	}
}
