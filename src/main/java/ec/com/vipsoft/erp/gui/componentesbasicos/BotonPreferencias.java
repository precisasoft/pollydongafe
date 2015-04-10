package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;

public class BotonPreferencias extends Button {

	private static final long serialVersionUID = 7690702203749971984L;
	public BotonPreferencias() {
		super("preferencias");
		setIcon(FontAwesome.WRENCH);
		setDescription("cambia las preferencias");
	}
}
