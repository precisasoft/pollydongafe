package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;

public class BotonBuscar extends Button {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3940053910705572164L;

	public BotonBuscar() {
		super("...");
		setStyleName("friendly");
		setIcon(FontAwesome.SEARCH);
	}

}
