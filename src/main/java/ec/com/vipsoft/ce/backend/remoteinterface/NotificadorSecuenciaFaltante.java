package ec.com.vipsoft.ce.backend.remoteinterface;

import java.util.List;

import javax.ejb.Remote;

import ec.com.vipsoft.erp.abinadi.dominio.ComprobanteElectronico.TipoComprobante;
import ec.com.vipsoft.erp.abinadi.dominio.ReporteComprobanteElectronico;

@Remote
public interface NotificadorSecuenciaFaltante {

	public List<ReporteComprobanteElectronico>listarSecuenciasFaltantes(String rucEmisor,String sucursal,String ptoVenta,Long inicio,Long fin,TipoComprobante tipo);
}
