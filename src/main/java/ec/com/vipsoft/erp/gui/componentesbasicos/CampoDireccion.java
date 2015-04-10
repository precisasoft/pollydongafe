package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.ui.TextField;

public class CampoDireccion extends TextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4291893312044093874L;

	public CampoDireccion() {
		super();
		setInputPrompt("Cdla. Guayacanes Mz 158 solar 5");
		setDescription("ingrese la dirección");
		setWidth("270px");
		setNullRepresentation("");
	}
	

}
