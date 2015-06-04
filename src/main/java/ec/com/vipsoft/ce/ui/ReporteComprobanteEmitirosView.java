package ec.com.vipsoft.ce.ui;

import java.util.Date;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.DateField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ec.com.vipsoft.erp.gui.componentesbasicos.BotonBuscar;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonCancelar;
import ec.com.vipsoft.erp.gui.componentesbasicos.HorizontalL;

@CDIView("reportecomprobantesemitidos")
public class ReporteComprobanteEmitirosView extends VerticalLayout implements View {
	private static final long serialVersionUID = 7730899055065010743L;
	private BotonBuscar botonBuscar;
	private BotonCancelar botonCancelar;
	public ReporteComprobanteEmitirosView() {
		HorizontalL layout=new HorizontalL();
		layout.setSpacing(true);
		DateField fechaInicio=new DateField();
		fechaInicio.setDateFormat("dd/MM/yyyy");
		fechaInicio.setValue(new Date());
		DateField fechaFina=new DateField();
		fechaFina.setDateFormat("dd/MM/yyyy");
		fechaFina.setValue(new Date());
		layout.addComponent("fecha inicial", fechaInicio);
		layout.addComponent("fecha final", fechaFina);
		botonBuscar=new BotonBuscar();
		layout.addComponent(botonBuscar);
		botonCancelar=new BotonCancelar();
		layout.addComponent(botonCancelar);
		botonCancelar.addClickListener(event->UI.getCurrent().getNavigator().navigateTo("menu"));
		BrowserWindowOpener browswop=new BrowserWindowOpener("http://www.google.com");
		browswop.extend(botonBuscar);
		setMargin(true);
		setSpacing(true);
		addComponent(layout);
		setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
		
	}
	@Override
	public void enter(ViewChangeEvent event) {

	}

}
