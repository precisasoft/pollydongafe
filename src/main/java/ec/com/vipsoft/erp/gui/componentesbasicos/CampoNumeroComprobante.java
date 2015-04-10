package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.ui.TextField;

public class CampoNumeroComprobante extends TextField {

	private static final long serialVersionUID = 2942959377003045807L;

	public CampoNumeroComprobante() {
		super();
		setInputPrompt("001-001-000000069");
		setDescription("ingrese el N° de comprobante");
		setMaxLength(17);
		setWidth("168px");
		setNullRepresentation("");
		//setRequired(true);
	}
}
