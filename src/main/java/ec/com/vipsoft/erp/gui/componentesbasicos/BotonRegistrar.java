package ec.com.vipsoft.erp.gui.componentesbasicos;


import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;

public class BotonRegistrar extends Button{

	private static final long serialVersionUID = 717168691179908933L;
	public BotonRegistrar() {
		super("registrar");
		setIcon(FontAwesome.SAVE);
		setDescription("haga click aqui para proceder");
		setStyleName("primary");		
	}

}
