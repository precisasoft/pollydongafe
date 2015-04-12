package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.ui.TextField;

public class CampoCorreoElectronico extends TextField{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7932325731902324322L;

	public CampoCorreoElectronico() {
		super();
		setDescription("ingrese un correo electrónico válido");
		setInputPrompt("pepito@hotmail.com");
		setWidth("180px");
	}

}
