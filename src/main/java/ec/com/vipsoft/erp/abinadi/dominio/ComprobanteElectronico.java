package ec.com.vipsoft.erp.abinadi.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * este entidad se usa para generar tabla que se muestra en el portal web 
 * del cliente.
 * @author chrisvv
 *
 */
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"claveAcceso"})})
public class ComprobanteElectronico implements Serializable,Comparable<ComprobanteElectronico>{

	public enum TipoComprobante {
		factura, guiaRemision, notaCredito, notaDebito, retencion;
	}

	private static final long serialVersionUID = -7861134113150414156L;
	@Column(columnDefinition="boolean default false")
	private boolean autorizado;
	private String claveAcceso;
	private String codigoError;		
	@JoinColumn(name="caid")
	@ManyToOne(cascade=CascadeType.PERSIST)
	private ComprobanteAutorizado comprobanteAutorizado;     
	@JoinColumn(name="df")
	@ManyToOne(cascade=CascadeType.PERSIST)
	private DocumentoFirmado documentoFirmado;
	private boolean enPruebas;
	@ManyToOne(fetch = FetchType.EAGER)
	private Entidad entidadEmisora;
	private boolean enviado;
	@Column(name="estb")
	private String establecimiento;
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_a")
	private Date fechaAutorizacion;
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_e")
	private Date fechaEnvio;
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_r")
	private Date fechaRegistro;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="beneficiario")
	private String identificacionBeneficiario;
	@Column(name="error")
	private String mensajeError;
	@Column(name = "nbc"  , columnDefinition="boolean default false")
	protected boolean notificadoBeneficiario;
	@Column(name = "nci")
	protected boolean notificadoCorreoInterno;
    @Column(name="autorizacion")
	private String numeroAutorizacion;
    private String processId;
	@Column(name="ptoemision")
	private String puntoEMision;
	private String secuencia;
	private Integer reintentos;
    @Enumerated(EnumType.ORDINAL)
	private TipoComprobante tipo;
    @Column(name="acs")
    private boolean autorizacionConsultadoAlSRI;
    private String correoElectronico;
    @Column(columnDefinition="decimal(20,2) default 0")
    private BigDecimal monto;
    private Boolean encriptado;
    
    public Boolean getEncriptado() {
		return encriptado;
	}
	public void setEncriptado(Boolean encriptado) {
		this.encriptado = encriptado;
	}
	public boolean isAutorizacionConsultadoAlSRI() {
		return autorizacionConsultadoAlSRI;
	}
	public void setAutorizacionConsultadoAlSRI(boolean autorizacionConsultadoAlSRI) {
		this.autorizacionConsultadoAlSRI = autorizacionConsultadoAlSRI;
	}
	public ComprobanteElectronico() {
			super();
			autorizacionConsultadoAlSRI=false;
			autorizado=false;
		}
	public String getClaveAcceso() {
		return claveAcceso;
	}
	public String getCodigoError() {
		return codigoError;
	}
	
	public ComprobanteAutorizado getComprobanteAutorizado() {
		return comprobanteAutorizado;
	}
	
	public DocumentoFirmado getDocumentoFirmado() {
		return documentoFirmado;
	}
	
	public Entidad getEntidadEmisora() {
		return entidadEmisora;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	
	public Long getId() {
		return id;
	}

	
	public String getIdentificacionBeneficiario() {
		return identificacionBeneficiario;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public String getNumeroAutorizacion() {
		return numeroAutorizacion;
	}

	public String getProcessId() {
		return processId;
	}

	public String getPuntoEMision() {
		return puntoEMision;
	}

	public String getSecuencia() {
		return secuencia;
	}

	public TipoComprobante getTipo() {
		return tipo;
	}

	public boolean isAutorizado() {
		return autorizado;
	}

	public boolean isEnPruebas() {
		return enPruebas;
	}

	public boolean isEnviado() {
		return enviado;
	}

	public boolean isNotificadoBeneficiario() {
		return notificadoBeneficiario;
	}

	public boolean isNotificadoCorreoInterno() {
		return notificadoCorreoInterno;
	}

	public void setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}

	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public void setComprobanteAutorizado(ComprobanteAutorizado comprobanteAutorizado) {
		this.comprobanteAutorizado = comprobanteAutorizado;
	}

	public void setDocumentoFirmado(DocumentoFirmado documentoFirmado) {
		this.documentoFirmado = documentoFirmado;
	}

	public void setEnPruebas(boolean enPruebas) {
		this.enPruebas = enPruebas;
	}

	

	public void setEntidadEmisora(Entidad entidadEmisora) {
		this.entidadEmisora = entidadEmisora;
	}

	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	

	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIdentificacionBeneficiario(String identificacionBeneficiario) {
		this.identificacionBeneficiario = identificacionBeneficiario;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public void setNotificadoBeneficiario(boolean notificadoBeneficiario) {
		this.notificadoBeneficiario = notificadoBeneficiario;
	}

	public void setNotificadoCorreoInterno(boolean notificadoCorreoInterno) {
		this.notificadoCorreoInterno = notificadoCorreoInterno;
	}

	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setPuntoEMision(String puntoEMision) {
		this.puntoEMision = puntoEMision;
	}

	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}

	public void setTipo(TipoComprobante tipo) {
		this.tipo = tipo;
	}
	public Integer getReintentos() {
		return reintentos;
	}
	public void setReintentos(Integer reintentos) {
		this.reintentos = reintentos;
	}
	@PrePersist
	public void antesPersistir(){
		fechaRegistro=new Date();
		notificadoBeneficiario=false;
		notificadoCorreoInterno=false;
	}
	@Override
	public int compareTo(ComprobanteElectronico o) {
		int retorno=claveAcceso.compareTo(o.claveAcceso);
		return retorno;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	
}
