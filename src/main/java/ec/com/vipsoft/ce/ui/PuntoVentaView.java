package ec.com.vipsoft.ce.ui;

import java.awt.Checkbox;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ec.com.vipsoft.ce.backend.managedbean.UserInfo;
import ec.com.vipsoft.ce.backend.service.AdministradorSecuenciasProduccion;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonCancelar;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonRegistrar;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoCodigo;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoSecuencia;
@CDIView("secuencias")
public class PuntoVentaView extends VerticalLayout implements View{

	@EJB
	private AdministradorSecuenciasProduccion administradoSecuencia;
	@Inject
	private UserInfo userInfo;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4434052326030846366L;
	private CampoSecuencia secuenciaFactura;
	private CampoSecuencia secuenciaNC;
	private CampoSecuencia secuenciaND;
	private CampoCodigo codigoEstablecimeinto;
	private CampoCodigo codigoPuntoEmision;
	private CampoSecuencia secuenciaGuiaRemision;
	private CampoSecuencia secuenciaRetencion;
	private BotonCancelar botonCancelar;
	private BotonRegistrar botonFacturaRegistrar;
	private BotonRegistrar botonNCRegistrar;
	private BotonRegistrar botonRegistrarGuia;
	private BotonRegistrar botonRegistrarRetencion;
	private CheckBox facturaEnProduccion;
	private CheckBox ncEnProduccion;
	private CheckBox guiaRemisionEnProduccion;
	private Checkbox retencionEnProduccion;
	private CheckBox reteEnProduccion;
	public PuntoVentaView() {
		super();
		codigoEstablecimeinto=new CampoCodigo();		
		codigoPuntoEmision=new CampoCodigo();
		secuenciaFactura=new CampoSecuencia();
		secuenciaNC=new CampoSecuencia();
		secuenciaND=new CampoSecuencia();
		secuenciaGuiaRemision=new CampoSecuencia();
		secuenciaRetencion=new CampoSecuencia();
		botonFacturaRegistrar=new BotonRegistrar();
		botonCancelar=new BotonCancelar();
		HorizontalLayout l1=new HorizontalLayout();
		l1.setSpacing(true);
		l1.addComponent(new Label("Establecimiento"));
		l1.addComponent(codigoEstablecimeinto);
		l1.addComponent(new Label("Punto de emsión"));
		l1.addComponent(codigoPuntoEmision);
		l1.addComponent(botonCancelar);
		HorizontalLayout l2=new HorizontalLayout();
		l2.setSpacing(true);
		l2.addComponent(new Label("factura"));
		l2.addComponent(secuenciaFactura);
		facturaEnProduccion=new CheckBox("produccion");
		l2.addComponent(facturaEnProduccion);
		l2.addComponent(botonFacturaRegistrar);
		HorizontalLayout l3=new HorizontalLayout();
		l3.setSpacing(true);
		l3.addComponent(new Label("NC"));
		botonNCRegistrar=new BotonRegistrar();
		l3.addComponent(secuenciaNC);
		ncEnProduccion=new CheckBox("En producción");
		l3.addComponent(ncEnProduccion);
		l3.addComponent(botonNCRegistrar);
		HorizontalLayout l4=new HorizontalLayout();
		l4.setSpacing(true);
		l4.addComponent(new Label("Guia"));
		l4.addComponent(secuenciaGuiaRemision);
		guiaRemisionEnProduccion=new CheckBox("En producción");
		l4.addComponent(guiaRemisionEnProduccion);
		botonRegistrarGuia=new BotonRegistrar();
		l4.addComponent(botonRegistrarGuia);
		HorizontalLayout l5=new HorizontalLayout();
		l5.setSpacing(true);
		l5.addComponent(new Label("Retencion"));
		l5.addComponent(secuenciaRetencion);
		reteEnProduccion=new CheckBox("En producción");
		l5.addComponent(reteEnProduccion);		
		botonRegistrarRetencion=new BotonRegistrar();
		l5.addComponent(botonRegistrarRetencion);
		setSpacing(true);setMargin(true);
		addComponent(l1);addComponent(l2);addComponent(l3);addComponent(l4);addComponent(l5);
		  
		}
		
	
	@Override
	public void enter(ViewChangeEvent event) {
		
	}

	@PostConstruct
	public void ponerAcciones(){
		botonCancelar.addClickListener(event->UI.getCurrent().getNavigator().navigateTo("menu"));
		botonFacturaRegistrar.addClickListener(event->{
			administradoSecuencia.estableceSecuenciaFactura(userInfo.getRucEmisor()	, codigoEstablecimeinto.getValue(), codigoPuntoEmision.getValue(), secuenciaFactura.getValue(), facturaEnProduccion.getValue());
			UI.getCurrent().getNavigator().navigateTo("menu");
		});
		botonNCRegistrar.addClickListener(event->{
			administradoSecuencia.estableceSecuenciaNotaCredito(userInfo.getRucEmisor()	, codigoEstablecimeinto.getValue(), codigoPuntoEmision.getValue(), secuenciaNC.getValue(), ncEnProduccion.getValue());
			UI.getCurrent().getNavigator().navigateTo("menu");
		});
		botonRegistrarGuia.addClickListener(event->{
			administradoSecuencia.estableceSecuenciaGuiaRenmision(userInfo.getRucEmisor()	, codigoEstablecimeinto.getValue(), codigoPuntoEmision.getValue(), secuenciaGuiaRemision.getValue(), guiaRemisionEnProduccion.getValue());
			UI.getCurrent().getNavigator().navigateTo("menu");
		});
		botonRegistrarRetencion.addClickListener(event->{
			administradoSecuencia.estableceSecuenciaRetencion(userInfo.getRucEmisor()	, codigoEstablecimeinto.getValue(), codigoPuntoEmision.getValue(), secuenciaRetencion.getValue(), reteEnProduccion.getValue());
			UI.getCurrent().getNavigator().navigateTo("menu");
		});
	}
}
