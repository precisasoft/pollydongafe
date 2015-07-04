package ec.com.vipsoft.ce.backend.remoteinterface;

import javax.ejb.Remote;

import ec.com.vipsoft.erp.abinadi.dominio.ReporteComprobanteElectronico;

@Remote
public interface ConsultaComprobantesEmitido {

	public ReporteComprobanteElectronico consultaFactura(String rucEmisorEncriptado,String establecimiento_puntoventa_secuencia);
	public ReporteComprobanteElectronico consultaNC(String rucEmisorEncriptado,String establecimiento_puntoventa_secuencia);
	public ReporteComprobanteElectronico consultaGuiaRemision(String rucEmisorEncriptado,String establecimiento_puntoventa_secuencia);
	public ReporteComprobanteElectronico consultaComprobanteRetencion(String rucEmisorEncriptado,String establecimiento_puntoventa_secuencia);
}
