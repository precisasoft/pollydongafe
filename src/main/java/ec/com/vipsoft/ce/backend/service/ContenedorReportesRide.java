package ec.com.vipsoft.ce.backend.service;

import java.io.Serializable;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import net.sf.jasperreports.engine.JasperReport;

@Singleton
@Startup
public class ContenedorReportesRide implements Serializable{

	private static final long serialVersionUID = 7191867833399382806L;
	private JasperReport rideFactura;
	private JasperReport rideRetencion;
	private JasperReport rideNotaCredito;
	private JasperReport rideNotaDebito;
	private JasperReport rideGuiaRemision;
	private JasperReport rideFacturaSinLogo;
	private JasperReport rideRetencionSinLogo;
	private JasperReport rideNotaCreditoSinLogo;
	private JasperReport rideNotaDebitoSinLogo;
	private JasperReport rideGuiaRemisionSinLogo;
	public JasperReport getRideFactura() {
		return rideFactura;
	}
	public void setRideFactura(JasperReport rideFactura) {
		this.rideFactura = rideFactura;
	}
	public JasperReport getRideRetencion() {
		return rideRetencion;
	}
	public void setRideRetencion(JasperReport rideRetencion) {
		this.rideRetencion = rideRetencion;
	}
	public JasperReport getRideNotaCredito() {
		return rideNotaCredito;
	}
	public void setRideNotaCredito(JasperReport rideNotaCredito) {
		this.rideNotaCredito = rideNotaCredito;
	}
	public JasperReport getRideNotaDebito() {
		return rideNotaDebito;
	}
	public void setRideNotaDebito(JasperReport rideNotaDebito) {
		this.rideNotaDebito = rideNotaDebito;
	}
	public JasperReport getRideGuiaRemision() {
		if(rideGuiaRemision==null){
			if(rideGuiaRemisionSinLogo!=null){
				rideGuiaRemision=rideGuiaRemisionSinLogo;
			}
		}
		return rideGuiaRemision;
	}
	public void setRideGuiaRemision(JasperReport rideGuiaRemision) {
		this.rideGuiaRemision = rideGuiaRemision;
	}
	public JasperReport getRideFacturaSinLogo() {
		return rideFacturaSinLogo;
	}
	public void setRideFacturaSinLogo(JasperReport rideFacturaSinLogo) {
		this.rideFacturaSinLogo = rideFacturaSinLogo;
	}
	public JasperReport getRideRetencionSinLogo() {
		return rideRetencionSinLogo;
	}
	public void setRideRetencionSinLogo(JasperReport rideRetencionSinLogo) {
		this.rideRetencionSinLogo = rideRetencionSinLogo;
	}
	public JasperReport getRideNotaCreditoSinLogo() {
		return rideNotaCreditoSinLogo;
	}
	public void setRideNotaCreditoSinLogo(JasperReport rideNotaCreditoSinLogo) {
		this.rideNotaCreditoSinLogo = rideNotaCreditoSinLogo;
	}
	public JasperReport getRideNotaDebitoSinLogo() {
		return rideNotaDebitoSinLogo;
	}
	public void setRideNotaDebitoSinLogo(JasperReport rideNotaDebitoSinLogo) {
		this.rideNotaDebitoSinLogo = rideNotaDebitoSinLogo;
	}
	public JasperReport getRideGuiaRemisionSinLogo() {
		return rideGuiaRemisionSinLogo;
	}
	public void setRideGuiaRemisionSinLogo(JasperReport rideGuiaRemisionSinLogo) {
		this.rideGuiaRemisionSinLogo = rideGuiaRemisionSinLogo;
	}
	

}
