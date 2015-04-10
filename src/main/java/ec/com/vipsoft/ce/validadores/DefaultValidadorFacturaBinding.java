package ec.com.vipsoft.ce.validadores;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ec.com.vipsoft.ce.comprobantesNeutros.FacturaBinding;
import ec.com.vipsoft.ce.comprobantesNeutros.FacturaDetalleBinding;

public class DefaultValidadorFacturaBinding implements ConstraintValidator<FacturaBindingValida,FacturaBinding> {

	@Override
	public void initialize(FacturaBindingValida arg0) {	
		
	}

	@Override
	public boolean isValid(FacturaBinding factura, ConstraintValidatorContext arg1) {
		boolean retorno=true;
		if(factura.getDetalles().isEmpty()){
			retorno=false;
		}else{
			//verificamos el total es la suma de los totales
			BigDecimal sumatoriaTotal=BigDecimal.ZERO;
			BigDecimal sumaDescuentos=BigDecimal.ZERO;
			for(FacturaDetalleBinding detalle:factura.getDetalles()){
				sumatoriaTotal=sumatoriaTotal.add(detalle.getValorTotal());
				sumaDescuentos=sumaDescuentos.add(detalle.getDescuento());
			}
			if(sumaDescuentos.doubleValue()>sumatoriaTotal.doubleValue()){
				retorno=false;
			}
			if(!sumatoriaTotal.toString().equalsIgnoreCase(factura.getTotal().toEngineeringString())){
				retorno=false;
			}			
		}
		if(factura.isDebeGenerarGuiaRemision()){
			if(factura.getIdTransportista()==null){
				retorno=false;
			}
			if(factura.getRazonSocialTransportista()==null){
				retorno=false;
			}
			if(factura.getPlaca()==null){
				retorno=false;
			}
		}
		return retorno;
	}

	
	
}
