package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;

public class BotonSpredSheet extends Button {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4383863008578009241L;

	public BotonSpredSheet() {
		super("");
		setStyleName("friendly");
		setIcon(FontAwesome.TABLE);
	}

}
