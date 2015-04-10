package ec.com.vipsoft.erp.gui.componentesbasicos;

import java.util.Date;

import com.vaadin.ui.DateField;

public class CampoFecha extends DateField {

	public CampoFecha() {
		super();
		setDateFormat("dd/MM/yyyy");	
		setValue(new Date());
		setWidth("140px");
	}
}
