package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;

public class BotonSalir extends Button{
	
	private static final long serialVersionUID = 8655296329715980937L;

	public BotonSalir() {
		super("Salir");
		setIcon(FontAwesome.SIGN_OUT);
		setDescription("haga click para salir del sistema");
	}

}
