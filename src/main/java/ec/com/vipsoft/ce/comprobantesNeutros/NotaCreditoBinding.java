package ec.com.vipsoft.ce.comprobantesNeutros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class NotaCreditoBinding extends BaseComprobanteElectronicoBinding implements Serializable{
	
	private static final long serialVersionUID = -6034087979743676659L;
	private String numeroFacturaAModificar;
	private String motivoAModificar;
	private List<FacturaDetalleBinding> detallesAModficar;
	public String getNumeroFacturaAModificar() {
		return numeroFacturaAModificar;
	}
	public void setNumeroFacturaAModificar(String numeroFacturaAModificar) {
		this.numeroFacturaAModificar = numeroFacturaAModificar;
	}
	public String getMotivoAModificar() {
		return motivoAModificar;
	}
	public void setMotivoAModificar(String motivoAModificar) {
		this.motivoAModificar = motivoAModificar;
	}
	public List<FacturaDetalleBinding> getDetallesAModficar() {
		if(detallesAModficar==null){
			detallesAModficar=new ArrayList<FacturaDetalleBinding>();
		}
		return detallesAModficar;
	}
	public void setDetallesAModficar(List<FacturaDetalleBinding> detallesAModficar) {
		this.detallesAModficar = detallesAModficar;
	}
	

}
