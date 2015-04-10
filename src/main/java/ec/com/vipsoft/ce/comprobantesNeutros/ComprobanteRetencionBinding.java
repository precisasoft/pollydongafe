package ec.com.vipsoft.ce.comprobantesNeutros;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

public class ComprobanteRetencionBinding extends BaseComprobanteElectronicoBinding {

	private static final long serialVersionUID = 142973095115731544L;
	@Valid
	private SortedSet<ComprobanteRetencionDetalleBinding>detalles;
	@Pattern(regexp="[0-9]{2}/[0-9]{4}")
	private String periodo;	
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
	protected Date periodoFiscal;
	// propieades utiles para poder hacer el databinding mas facil
	protected String codigoDocumento;
	protected String numeroDocumento;
	protected Date fechaDocumento;
	protected String tipoImpuesto;
	protected String codigoImpuesto;
	protected BigDecimal baseImponible;
	protected BigDecimal porcentajeRetencion;

	protected SortedSet<ImpuestoRetencion> impuestos;
	public ComprobanteRetencionBinding() {
		super();
		detalles=new TreeSet<>();
	}
	public SortedSet<ComprobanteRetencionDetalleBinding> getDetalles() {
		return detalles;
	}
	public String getPeriodo() {
		if(periodo==null){
			setFechaPeriodoFiscal(new Date());
		}
		return periodo.replace("\r", "").replace("\n", "");
	}
	public void setDetalles(SortedSet<ComprobanteRetencionDetalleBinding> detalles) {
		this.detalles = detalles;
	}	
	public void setFechaPeriodoFiscal(Date fecha){
		SimpleDateFormat sdf=new SimpleDateFormat("MM/yyyy");
		periodo=sdf.format(fecha);
	}
	public void setPeriodo(String periodo) {
		this.periodo = sinCRLF(periodo);
	}
	public void anadirNuevoDetalle() {
		if ((baseImponible.doubleValue() > 0)	&& (porcentajeRetencion.doubleValue() > 0)) {
			ImpuestoRetencion nimpuesto = new ImpuestoRetencion();
			nimpuesto.setBaseImponible(baseImponible);
			nimpuesto.setCodigo(tipoImpuesto);
			nimpuesto.setNumeroDocumento(numeroDocumento);
			nimpuesto.setCodigoRetention(codigoImpuesto);
			nimpuesto.setFechaEmisionDocumentoSustento(fechaEmision);
			if (codigoDocumento.equals("01")) {
				nimpuesto.setTipoComprobante(TipoComprobante.factura);
			}
			if (codigoDocumento.equalsIgnoreCase("04")) {
				nimpuesto.setTipoComprobante(TipoComprobante.Nc);
			}
			if (codigoDocumento.equalsIgnoreCase("05")) {
				nimpuesto.setTipoComprobante(TipoComprobante.Nd);
			}
			if (codigoImpuesto.equalsIgnoreCase("1")) {
				nimpuesto.setPorcentajeRetencion(new BigDecimal(30));
			}
			if (codigoImpuesto.equalsIgnoreCase("2")) {
				nimpuesto.setPorcentajeRetencion(new BigDecimal(70));
			}
			if (codigoImpuesto.equalsIgnoreCase("3")) {
				nimpuesto.setPorcentajeRetencion(new BigDecimal(100));
			}
			if (codigoImpuesto.equalsIgnoreCase("4580")) {
				nimpuesto.setPorcentajeRetencion(new BigDecimal(5));
			}
			nimpuesto.setPorcentajeRetencion(porcentajeRetencion);
			nimpuesto.actualizarValorRetenido();
			impuestos.add(nimpuesto);
		}

	}
	public SimpleDateFormat getSdf() {
		return sdf;
	}
	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}
	public Date getPeriodoFiscal() {
		return periodoFiscal;
	}
	public void setPeriodoFiscal(Date periodoFiscal) {
		this.periodoFiscal = periodoFiscal;
	}
	public String getCodigoDocumento() {
		return codigoDocumento;
	}
	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public Date getFechaDocumento() {
		return fechaDocumento;
	}
	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}
	public String getTipoImpuesto() {
		return tipoImpuesto;
	}
	public void setTipoImpuesto(String tipoImpuesto) {
		this.tipoImpuesto = tipoImpuesto;
	}
	public String getCodigoImpuesto() {
		return codigoImpuesto;
	}
	public void setCodigoImpuesto(String codigoImpuesto) {
		this.codigoImpuesto = codigoImpuesto;
	}
	public BigDecimal getBaseImponible() {
		return baseImponible;
	}
	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}
	public BigDecimal getPorcentajeRetencion() {
		return porcentajeRetencion;
	}
	public void setPorcentajeRetencion(BigDecimal porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}
	public SortedSet<ImpuestoRetencion> getImpuestos() {
		if(impuestos==null){
			impuestos=new TreeSet<ImpuestoRetencion>();
		}
		return impuestos;
	}
	public void setImpuestos(SortedSet<ImpuestoRetencion> impuestos) {
		this.impuestos = impuestos;
	}


}
