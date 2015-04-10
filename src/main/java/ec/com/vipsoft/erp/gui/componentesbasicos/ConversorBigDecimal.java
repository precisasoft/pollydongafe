package ec.com.vipsoft.erp.gui.componentesbasicos;

import java.math.BigDecimal;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class ConversorBigDecimal implements Converter<String,BigDecimal> {

	private static final long serialVersionUID = 2899821328847059496L;

	@Override
	public BigDecimal convertToModel(String value,Class<? extends BigDecimal> targetType, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
		String nuevoString=value.replace(",", ".");
		BigDecimal retorno;
		if(nuevoString.length()>0){
			try{
				retorno=new BigDecimal(nuevoString);
			}catch(NumberFormatException e){
				retorno=BigDecimal.ZERO;
			}
		}else{
			retorno=BigDecimal.ZERO;
		}
		return retorno;
	}

	@Override
	public String convertToPresentation(BigDecimal value,Class<? extends String> targetType, Locale locale)	throws com.vaadin.data.util.converter.Converter.ConversionException {
		// TODO Auto-generated method stub
		return String.valueOf(value);
	}

	@Override
	public Class<BigDecimal> getModelType() {
		// TODO Auto-generated method stub
		return BigDecimal.class;
	}

	@Override
	public Class<String> getPresentationType() {
		// TODO Auto-generated method stub
		return String.class;
	}

}
