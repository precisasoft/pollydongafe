package ec.com.vipsoft.erp.abinadi.procesos;

import java.util.ArrayList;
import java.util.List;

public class RespuestaRecepcionDocumento {
	private static final long serialVersionUID = 1L;
	private String estado;
	private List<DetalleRecepcion> detalle = new ArrayList<DetalleRecepcion>();
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public List<DetalleRecepcion> getDetalle() {
		return detalle;
	}
	public void setDetalle(List<DetalleRecepcion> detalle) {
		this.detalle = detalle;
	}
	

}
