package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.ui.TextField;

public class CampoSecuencia extends TextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5603532335146184760L;

	public CampoSecuencia() {
		super();
		setInputPrompt("000000122");
		setDescription("ingrese la secuencia del comprobante");
		setWidth("120px");
		setMaxLength(9);
	}
}
