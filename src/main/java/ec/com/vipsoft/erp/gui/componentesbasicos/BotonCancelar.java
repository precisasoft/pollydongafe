package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;

public class BotonCancelar extends Button{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4132189797986312957L;

	public BotonCancelar() {
		super("cancelar");
		setIcon(FontAwesome.UNDO);
		setDescription("haga click para cancelar");
		setStyleName("danger");
		
	}
	
}
