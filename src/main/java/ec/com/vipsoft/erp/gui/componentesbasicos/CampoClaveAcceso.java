package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.ui.TextField;

public class CampoClaveAcceso extends TextField {

	private static final long serialVersionUID = -8249695867797213443L;
	public CampoClaveAcceso() {
		super();
		setInputPrompt("1006201501091556833200120010020000000010000001213");
		setMaxLength("1006201501091556833200120010020000000010000001213".length());
		setDescription("ingrese una clave de acceso");
		setWidth("460px");
		
	}
}
