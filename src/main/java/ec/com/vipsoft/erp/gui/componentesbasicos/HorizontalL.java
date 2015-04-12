package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class HorizontalL extends HorizontalLayout {

	private static final long serialVersionUID = -482123725490767768L;
	public HorizontalL() {
		super();
		setSpacing(true);
	}
	public void addComponent(String rotulo){
		addComponent(new Label(rotulo));
	}
	public void addComponent(String rotulo,Component componente){
		addComponent(new Label(rotulo));
		addComponent(componente);
	}
	public void addComponent(Component rotulo,Component componente){
		addComponent(rotulo);
		addComponent(componente);
	}
}
