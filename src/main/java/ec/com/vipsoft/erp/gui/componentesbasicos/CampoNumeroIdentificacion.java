package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.ui.TextField;

public class CampoNumeroIdentificacion extends TextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1026569421604060990L;

	public CampoNumeroIdentificacion() {
		super();
		setInputPrompt("999999999999.");
		setDescription("ingrese la identificación");
		setWidth("140px");
		setNullRepresentation("");
		setValidationVisible(false);
	}

	
	

}
