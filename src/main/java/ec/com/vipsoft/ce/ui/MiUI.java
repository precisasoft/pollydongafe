package ec.com.vipsoft.ce.ui;

import javax.inject.Inject;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("mytheme")
@SuppressWarnings("serial")
@CDIUI("")
public class MiUI extends UI {

	@Inject
	private CDIViewProvider viewProvider;
	@Override
	protected void init(VaadinRequest request) {
		Responsive.makeResponsive(this);
		Navigator navigator=new Navigator(this, this);
		navigator.addProvider(viewProvider);
		navigator.setErrorView(ErrorView.class);
		setNavigator(navigator);
		navigator.navigateTo("login");
	}

}
