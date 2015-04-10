package ec.com.vipsoft.ce.comprobantesNeutros;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
//import java.util.HashMap;
//import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class FacturaDetalleBinding implements Serializable{
	
	private String infoAdicional1;
	private String infoAdicional2;
	private String infoAdicional3;
	private static final long serialVersionUID = -9123527831919165669L;
	@Min(value=0,message="el valor no puede ser menor a 0")	
	protected BigDecimal cantidad;
	@NotNull(message="este campo no debe ser null")
	@Size(max=25)
	private String codigo;
	@Size(max=25)
	private String codigoAlterno;
	@NotNull(message="este campo no debe ser null")
	@Size(max=300)
	protected String descripcion;
	@NotNull(message="este campo no debe ser null")
	@Min(value=0,message="el valor no puede ser menor a 0")
	protected BigDecimal descuento;
	@NotNull(message="este campo no debe ser null")
	@Min(value=0,message="el valor no puede ser menor a 0")
	protected BigDecimal ice;
	
	//protected Map<String,String>infoAdicional;
	@NotNull(message="este campo no debe ser null")
	@Min(value=0,message="el valor no puede ser menor a 0")	
	protected BigDecimal iva12;
	@NotNull(message="este campo no debe ser null")
	@Min(value=0,message="el valor no puede ser menor a 0")
	protected BigDecimal valorTotal;
	@NotNull(message="este campo no debe ser null")
	@Min(value=0,message="el valor no puede ser menor a 0")
	protected BigDecimal valorUnitario;
        @Pattern(regexp = "[0-7]{1}",message = "el codigo de impuesto iva es invalido")
        protected String codigoIVA;
	public FacturaDetalleBinding() {
		super();
		codigoIVA="2";
	}

    public String getCodigoIVA() {
        return codigoIVA;
    }

    public void setCodigoIVA(String codigoIVA) {
        this.codigoIVA = codigoIVA;
    }
	
	public BigDecimal getCantidad() {
		if(cantidad==null){
			cantidad=new BigDecimal("0.00");
		}
		return cantidad.setScale(2, RoundingMode.HALF_UP);
	}
	public String getCodigo() {
		return codigo.replace("\r", "").replace("\n", "");
	}
	public String getCodigoAlterno() {
		if(codigoAlterno!=null){
			return codigoAlterno.replace("\r", "").replace("\n", "");	
		}else{
			return null;
		}
		
	}
	public String getDescripcion() {
		if(descripcion!=null){
			return descripcion.replace("\r", "").replace("\n", "");	
		}else{
			return null;
		}
		
	}
	public BigDecimal getDescuento() {
		if(descuento==null){
			descuento=new BigDecimal("0.00");
		}
		return descuento.setScale(2, RoundingMode.HALF_UP);
	}
	public BigDecimal getIce() {
		if(ice==null){
			ice=new BigDecimal("0.00");
		}
		return ice.setScale(2, RoundingMode.HALF_UP);
	}
//	public Map<String, String> getInfoAdicional() {
//		return infoAdicional;
//	}
	public BigDecimal getIva12() {
		if(iva12==null){
			iva12=new BigDecimal("0.00");
		}
		return iva12.setScale(2, RoundingMode.HALF_UP);
	}
	public BigDecimal getValorTotal() {
		if(valorTotal==null){
			valorTotal=new BigDecimal("0.00");
		}
		return valorTotal.setScale(2, RoundingMode.HALF_UP);
	}
	public BigDecimal getValorUnitario() {
		if(valorUnitario==null){
			valorUnitario=new BigDecimal("0.00");
		}
		return valorUnitario.setScale(2, RoundingMode.HALF_UP);
	}
	public void setCantidad(BigDecimal cantidad) {
		
		this.cantidad = cantidad;
	}
	public void setCodigo(String codigo) {
		this.codigo = sinCRLF(codigo);
	}
	public void setCodigoAlterno(String codigoAlterno) {
		this.codigoAlterno = codigoAlterno;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = sinCRLF(descripcion);
	}
	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}
	public void setIce(BigDecimal ice) {
		this.ice = ice;
	}
//	public void setInfoAdicional(Map<String, String> infoAdicional) {
//		this.infoAdicional = infoAdicional;
//	}
	public void setIva12(BigDecimal iva12) {
		this.iva12 = iva12;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	protected String sinCRLF(String contenido){
		return contenido.replace("\r", "").replace("\n", "");		
	}
//	public void anadirInfoAdicional(@Size(max=300,min=1)String clave,@Size(max=300,min=1)String valor){
//		if(infoAdicional.size()<15){
//			String _clave=sinCRLF(clave);
//			String _valor=sinCRLF(valor);
//			if((_clave.length()>0)&&(_valor.length()>0)){
//				infoAdicional.put(_clave, _valor);	
//			}				
//		}
//		
//	}

    public void calcularValorTotal() {		
		if((cantidad!=null)&&(valorUnitario!=null)){
			valorTotal=cantidad.multiply(valorUnitario);						
			if(descuento!=null){
				valorTotal=valorTotal.subtract(descuento);
			}
			valorTotal=valorTotal.add(iva12);
		}
		
	}
	public BigDecimal calculaBaeImponible() {
		BigDecimal baseImponible=BigDecimal.ZERO;
		//if(codigoIVA.equalsIgnoreCase("2")){
			baseImponible=cantidad.multiply(valorUnitario);
			baseImponible=baseImponible.subtract(getDescuento());
		//}
		return baseImponible.setScale(2, RoundingMode.HALF_UP);
	}

	public void calcularIva() {
		BigDecimal baseImponible=calculaBaeImponible();
		if(codigoIVA.equalsIgnoreCase("2")){
			iva12=baseImponible.multiply(new BigDecimal("0.12"));	
		}else{
			iva12=new BigDecimal("0.00");
		}
				
	}

	public String getInfoAdicional1() {
		return infoAdicional1;
	}

	public void setInfoAdicional1(String infoAdicional1) {
		this.infoAdicional1 = infoAdicional1;
	}

	public String getInfoAdicional2() {
		return infoAdicional2;
	}

	public void setInfoAdicional2(String infoAdicional2) {
		this.infoAdicional2 = infoAdicional2;
	}

	public String getInfoAdicional3() {
		return infoAdicional3;
	}

	public void setInfoAdicional3(String infoAdicional3) {
		this.infoAdicional3 = infoAdicional3;
	}
	
}
