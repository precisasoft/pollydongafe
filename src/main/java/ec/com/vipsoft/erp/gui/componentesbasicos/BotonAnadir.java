package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;

public class BotonAnadir extends Button {
	private static final long serialVersionUID = 6752184450306820413L;

	public BotonAnadir() {
		super("");
		setDescription("haga click para añadir item");
		setStyleName("friendly");
		setIcon(FontAwesome.PLUS);
	}


	

}
