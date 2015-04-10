package ec.com.vipsoft.ce.ui;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ec.com.vipsoft.erp.gui.componentesbasicos.BotonSalir;

@CDIView("menu")
public class MenuView extends VerticalLayout implements View{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5079435157736277851L;
	private Button botonFactura;
	private BotonSalir botonSalir;
	private Button botonComprobanteRetencion;
	private Button botonGuiaRemision;
	private Button secuencias;

	public MenuView() {
		super();
		setMargin(true);
		setSpacing(true);
		botonFactura=new Button("FACTURACIÓN");
		botonFactura.setIcon(FontAwesome.USD);
		botonFactura.addClickListener(event->{
			UI.getCurrent().getNavigator().navigateTo("FACTURA");
		});
		botonComprobanteRetencion=new Button("COMPROBANTE DE RETENCIÓN");
		botonGuiaRemision=new Button("GUÍA DE REMISIÓN");
		botonGuiaRemision.setIcon(FontAwesome.CAR);
		botonGuiaRemision.addClickListener(event->UI.getCurrent().getNavigator().navigateTo("GR"));
		botonComprobanteRetencion.setIcon(FontAwesome.ARROW_CIRCLE_LEFT);
		botonComprobanteRetencion.addClickListener(event->{
			UI.getCurrent().getNavigator().navigateTo("CR");
		});
		
		addComponent(botonFactura);
		addComponent(botonGuiaRemision);
		addComponent(botonComprobanteRetencion);
		botonSalir=new BotonSalir();
		botonSalir.addClickListener(event -> {			
			//SecurityUtils.getSubject().logout();
		//
			
			UI.getCurrent().getNavigator().navigateTo("login");
			VaadinSession.getCurrent().getSession().invalidate();
		});
		
		Button botonComprobantes=new Button("comprobantes");
		botonComprobantes.addClickListener(event -> UI.getCurrent().getNavigator().navigateTo("comprobantes"));
		Button botonNC=new Button("NC");
		botonNC.addClickListener(event->{UI.getCurrent().getNavigator().navigateTo("NC");});
		addComponent(botonNC);
		addComponent(botonComprobantes);
		secuencias=new Button("Secuencias/produccion");
		secuencias.addClickListener(event->UI.getCurrent().getNavigator().navigateTo("secuencias"));
		addComponent(secuencias);
		addComponent(botonSalir);	
	}

	@Override
	public void enter(ViewChangeEvent event) {
	
//		if(SecurityUtils.getSubject().isAuthenticated()){
//			if(!SecurityUtils.getSubject().hasRole("admin")){
//				UI.getCurrent().getNavigator().navigateTo("portal");	
//			}
//		}else{
//			//UI.getCurrent().getNavigator().navigateTo("login");
//		}
	}
	

}
