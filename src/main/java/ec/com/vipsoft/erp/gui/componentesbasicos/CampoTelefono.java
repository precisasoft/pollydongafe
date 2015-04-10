package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.ui.TextField;

public class CampoTelefono extends TextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4225016981307428286L;

	public CampoTelefono() {
		super();
		setInputPrompt("04-2610680");
		setDescription("ingrese el número telefónico");
		setNullRepresentation("");
	}

}
