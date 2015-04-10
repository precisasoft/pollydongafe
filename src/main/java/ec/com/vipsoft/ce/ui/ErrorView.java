package ec.com.vipsoft.ce.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ErrorView extends VerticalLayout implements View {

	public ErrorView() {
		super();
		setMargin(true);
		setSpacing(true);
		Label lerror=new Label("Upps .....Ha habido un error ,,,!");
		addComponent(lerror);
		setComponentAlignment(lerror, Alignment.MIDDLE_CENTER);
		
		
	}
	@Override
	public void enter(ViewChangeEvent event) {
	

	}

}
