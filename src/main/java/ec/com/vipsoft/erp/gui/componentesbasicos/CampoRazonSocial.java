package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.ui.TextField;

public class CampoRazonSocial extends TextField{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6101928286629601549L;

	public CampoRazonSocial() {
		super();
		setInputPrompt("Roberto Gomez Bolaños");
		setDescription("Ingrese la razón social");
		setWidth("270px");
		setNullRepresentation("");
	}

}
