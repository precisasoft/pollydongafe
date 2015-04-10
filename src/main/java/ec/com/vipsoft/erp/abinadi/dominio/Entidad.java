/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.vipsoft.erp.abinadi.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

/**
 *Representa la entidad o empresa.
 * @author chrisvv
 */
@Entity
public class Entidad implements Serializable, Comparable<Entidad> {

    private static final long serialVersionUID = 1L;

    
    private Boolean ambienteProduccion;
    private byte[] archivop12;
    private boolean comprobanteRetencionEnPruebas;
    private boolean contribuyenteEspecial;
    /**
     * a este correo se notifica la autorizacion de comprobantes
     */
    private String correoNotificacionInterna;
    private String direccionMatriz;

    private boolean facturaEnPruebas;
    private boolean guiaRemisionEnPruebas;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreComercial;
    private boolean notaCreditoEnPruebas;
    private boolean notaDebitoEnPruebas;
    private boolean notificarXcorreo;
    private boolean obligadoContabilidad;
    private String passwordp12;
    private String razonSocial;
    private String resolucionContribuyenteEspecial;
    @Column(unique = true)
    private String ruc;
    @Column(name = "secf")
    private Integer secuenciacafactura;
    @Column(name = "segr")
    private Integer secuenciacaguiaremision;
    @Column(name = "senc")
    private Integer secuenciacanotacredito;
    @Column(name = "secnd")
    private Integer secuenciacanotadebito;
    @Column(name = "seret")
    private Integer secuenciacaretencion;
    private String nombreReporteFactura;
    private String nombreReporteRetencion;
    private String nombreReporteNotaCredito;
    private String nombreReporteNotaDebito;
    private String nombreReporteGuiaRemision;
    protected Long secuenciaTransaccion;
    private Boolean usaComprobantesElectronicos;
    private  Boolean habilitado;
    @Column(columnDefinition="varchar(30) unique")
    private String dominioInternet;
    private String usuarioAdministrador;
    private boolean tieneLogo;
    private String hostcorreo;
    private boolean starttls;
    private Integer puertoCorreo;
    private boolean usaauthencorreo;
    private String transportecorreo;
    private String corremoEmisor;
    private String passowrdCorreoEmisor;
    private boolean habilitarNotificacionCorreo;
    
    
    
    public boolean isHabilitarNotificacionCorreo() {
		return habilitarNotificacionCorreo;
	}

	public void setHabilitarNotificacionCorreo(boolean habilitarNotificacionCorreo) {
		this.habilitarNotificacionCorreo = habilitarNotificacionCorreo;
	}

	public String getHostcorreo() {
		return hostcorreo;
	}

	public void setHostcorreo(String hostcorreo) {
		this.hostcorreo = hostcorreo;
	}

	public boolean isStarttls() {
		return starttls;
	}

	public void setStarttls(boolean starttls) {
		this.starttls = starttls;
	}

	public Integer getPuertoCorreo() {
		return puertoCorreo;
	}

	public void setPuertoCorreo(Integer puertoCorreo) {
		this.puertoCorreo = puertoCorreo;
	}

	public boolean isUsaauthencorreo() {
		return usaauthencorreo;
	}

	public void setUsaauthencorreo(boolean usaauthencorreo) {
		this.usaauthencorreo = usaauthencorreo;
	}

	public String getTransportecorreo() {
		return transportecorreo;
	}

	public void setTransportecorreo(String transportecorreo) {
		this.transportecorreo = transportecorreo;
	}

	public String getCorremoEmisor() {
		return corremoEmisor;
	}

	public void setCorremoEmisor(String corremoEmisor) {
		this.corremoEmisor = corremoEmisor;
	}

	public String getPassowrdCorreoEmisor() {
		return passowrdCorreoEmisor;
	}

	public void setPassowrdCorreoEmisor(String passowrdCorreoEmisor) {
		this.passowrdCorreoEmisor = passowrdCorreoEmisor;
	}

	public boolean isTieneLogo() {
		return tieneLogo;
	}

	public void setTieneLogo(boolean tieneLogo) {
		this.tieneLogo = tieneLogo;
	}

	public String getUsuarioAdministrador() {
		return usuarioAdministrador;
	}

	public void setUsuarioAdministrador(String usuarioAdministrador) {
		this.usuarioAdministrador = usuarioAdministrador;
	}

	public String getDominioInternet() {
		return dominioInternet;
	}

	public void setDominioInternet(String dominioInternet) {
		this.dominioInternet = dominioInternet;
	}

	public String getNombreReporteFactura() {
		return nombreReporteFactura;
	}

	public void setNombreReporteFactura(String nombreReporteFactura) {
		this.nombreReporteFactura = nombreReporteFactura;
	}

	public String getNombreReporteRetencion() {
		return nombreReporteRetencion;
	}

	public void setNombreReporteRetencion(String nombreReporteRetencion) {
		this.nombreReporteRetencion = nombreReporteRetencion;
	}

	public String getNombreReporteNotaCredito() {
		return nombreReporteNotaCredito;
	}

	public void setNombreReporteNotaCredito(String nombreReporteNotaCredito) {
		this.nombreReporteNotaCredito = nombreReporteNotaCredito;
	}

	public String getNombreReporteNotaDebito() {
		return nombreReporteNotaDebito;
	}

	public void setNombreReporteNotaDebito(String nombreReporteNotaDebito) {
		this.nombreReporteNotaDebito = nombreReporteNotaDebito;
	}

	public String getNombreReporteGuiaRemision() {
		return nombreReporteGuiaRemision;
	}

	public void setNombreReporteGuiaRemision(String nombreReporteGuiaRemision) {
		this.nombreReporteGuiaRemision = nombreReporteGuiaRemision;
	}

	public Entidad() {
        secuenciaTransaccion = 1L;
        inicializarSecuenciasCA();
        habilitado=true;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }    

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }
    @Override
    public int compareTo(Entidad o) {
        return ruc.compareTo(o.ruc);
    }

	@Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entidad)) {
            return false;
        }
        Entidad other = (Entidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

	public Boolean getAmbienteProduccion() {
        return ambienteProduccion;
    }

    public byte[] getArchivop12() {
        return archivop12;
    }

    public String getCorreoNotificacionInterna() {
		return correoNotificacionInterna;
	}

    public String getDireccionMatriz() {
        return direccionMatriz;
    }

    public Long getId() {
        return id;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getPasswordp12() {
        return passwordp12;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getResolucionContribuyenteEspecial() {
        return resolucionContribuyenteEspecial;
    }

    public String getRuc() {
        return ruc;
    }

    public Integer getSecuenciacafactura() {
        return secuenciacafactura;
    }

    public Integer getSecuenciacaguiaremision() {
        return secuenciacaguiaremision;
    }

    public Integer getSecuenciacanotacredito() {
        return secuenciacanotacredito;
    }

    public Integer getSecuenciacanotadebito() {
        return secuenciacanotadebito;
    }

    public Integer getSecuenciacaretencion() {
        return secuenciacaretencion;
    }


    public Long getSecuenciaTransaccion() {
        return secuenciaTransaccion;
    }

    public Boolean getUsaComprobantesElectronicos() {
        return usaComprobantesElectronicos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    protected void inicializarSecuenciasCA() {
        secuenciacafactura = new Integer(1);
        secuenciacaguiaremision = new Integer(1);
        secuenciacanotacredito = new Integer(1);
        secuenciacanotadebito = new Integer(1);
        secuenciacaretencion = new Integer(1);
        secuenciaTransaccion = new Long(1);
        comprobanteRetencionEnPruebas=true;
        facturaEnPruebas=true;
        notaCreditoEnPruebas=true;
        notaDebitoEnPruebas=true;
        guiaRemisionEnPruebas=true;
        comprobanteRetencionEnPruebas=true;
    }

    public boolean isComprobanteRetencionEnPruebas() {
    	
        return comprobanteRetencionEnPruebas;
    }

//    public Integer getCierreKardex() {
//        return cierreKardex;
//    }
//
//    public void setCierreKardex(Integer cierreKardex) {
//        this.cierreKardex = cierreKardex;
//    }

    public boolean isContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    public boolean isFacturaEnPruebas() {
        return facturaEnPruebas;
    }

    public boolean isGuiaRemisionEnPruebas() {
        return guiaRemisionEnPruebas;
    }

    public boolean isNotaCreditoEnPruebas() {
        return notaCreditoEnPruebas;
    }

    public boolean isNotaDebitoEnPruebas() {
        return notaDebitoEnPruebas;
    }

    public boolean isNotificarXcorreo() {
		return notificarXcorreo;
	}

    public boolean isObligadoContabilidad() {
        return obligadoContabilidad;
    }

    public void setAmbienteProduccion(Boolean ambienteProduccion) {
        this.ambienteProduccion = ambienteProduccion;
    }

    public void setArchivop12(byte[] archivop12) {
        this.archivop12 = archivop12;
    }

    public void setComprobanteRetencionEnPruebas(
            boolean comprobanteRetencionEnPruebas) {
        this.comprobanteRetencionEnPruebas = comprobanteRetencionEnPruebas;
    }

    public void setContribuyenteEspecial(boolean contribuyenteEspecial) {
        this.contribuyenteEspecial = contribuyenteEspecial;
    }

    public void setCorreoNotificacionInterna(String correoNotificacionInterna) {
		this.correoNotificacionInterna = correoNotificacionInterna;
	}

    public void setDireccionMatriz(String direccionMatriz) {
        this.direccionMatriz = direccionMatriz;
    }

    public void setFacturaEnPruebas(boolean facturaEnPruebas) {
        this.facturaEnPruebas = facturaEnPruebas;
    }

    public void setGuiaRemisionEnPruebas(boolean guiaRemisionEnPruebas) {
        this.guiaRemisionEnPruebas = guiaRemisionEnPruebas;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public void setNotaCreditoEnPruebas(boolean notaCreditoEnPruebas) {
        this.notaCreditoEnPruebas = notaCreditoEnPruebas;
    }

    public void setNotaDebitoEnPruebas(boolean notaDebitoEnPruebas) {
        this.notaDebitoEnPruebas = notaDebitoEnPruebas;
    }

    public void setNotificarXcorreo(boolean notificarXcorreo) {
		this.notificarXcorreo = notificarXcorreo;
	}

    public void setObligadoContabilidad(boolean obligadoContabilidad) {
        this.obligadoContabilidad = obligadoContabilidad;
    }

    public void setPasswordp12(String passwordp12) {
        this.passwordp12 = passwordp12;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setResolucionContribuyenteEspecial(
            String resolucionContribuyenteEspecial) {
        this.resolucionContribuyenteEspecial = resolucionContribuyenteEspecial;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public void setSecuenciacafactura(Integer secuenciacafactura) {
        this.secuenciacafactura = secuenciacafactura;
    }

    public void setSecuenciacaguiaremision(Integer secuenciacaguiaremision) {
        this.secuenciacaguiaremision = secuenciacaguiaremision;
    }

    public void setSecuenciacanotacredito(Integer secuenciacanotacredito) {
        this.secuenciacanotacredito = secuenciacanotacredito;
    }

    public void setSecuenciacanotadebito(Integer secuenciacanotadebito) {
        this.secuenciacanotadebito = secuenciacanotadebito;
    }

    public void setSecuenciacaretencion(Integer secuenciacaretencion) {
        this.secuenciacaretencion = secuenciacaretencion;
    }

    public void setSecuenciaTransaccion(Long secuenciaTransaccion) {
        this.secuenciaTransaccion = secuenciaTransaccion;
    }

    public void setUsaComprobantesElectronicos(Boolean usaComprobantesElectronicos) {
        this.usaComprobantesElectronicos = usaComprobantesElectronicos;
    }

    public Integer siguienteCAFactura() {
        if (secuenciacafactura == null) {
            secuenciacafactura = 1;
        }
        return secuenciacafactura++;
    }

    public Integer siguienteCAGuiaRemision() {
        if (secuenciacaguiaremision == null) {
            secuenciacaguiaremision = 1;
        }
        return secuenciacaguiaremision++;
    }

    public Integer siguienteCANotaCredito() {
        if (secuenciacanotacredito == null) {
            secuenciacanotacredito = 1;
        }
        return secuenciacanotacredito++;
    }

    public Integer siguienteCANotaDebito() {
        if (secuenciacanotadebito == null) {
            secuenciacanotadebito = 1;
        }
        return secuenciacanotadebito++;
    }

    public Integer siguienteCARetencion() {
        if (secuenciacaretencion == null) {
            secuenciacaretencion = 1;
        }
        return secuenciacaretencion++;
    }

    public String siguienteTransaccion() {
        StringBuilder sb = new StringBuilder();
        int longitud = 8 - secuenciaTransaccion.toString().length();
        for (int i = 0; i > longitud; i++) {
            sb.append("0");
        }
        secuenciaTransaccion++;
        sb.append(secuenciaTransaccion);
        return sb.toString();
    }

	@Override
    public String toString() {
        return "ec.com.vipsoft.erp.abinadi.dominio.Entidad[ id=" + id + " ]";
    }

	@PrePersist
    public void verificarAntesPersistir() {
//        if (cierreKardex == null) {
//            SimpleDateFormat anio = new SimpleDateFormat("yyyy");
//            SimpleDateFormat mes = new SimpleDateFormat("MM");
//            StringBuilder sb = new StringBuilder(anio.format(new Date()));
//            if (Integer.parseInt(mes.format(new Date())) > 6) {
//                sb.append(2);
//            } else {
//                sb.append(1);
//            }
//            cierreKardex = Integer.valueOf(sb.toString());
//        }
    }

}
