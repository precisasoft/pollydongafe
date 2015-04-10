package ec.com.vipsoft.ce.ui;

import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ec.com.vipsoft.ce.backend.managedbean.UserInfo;
import ec.com.vipsoft.ce.services.recepcionComprobantesNeutros.GeneradorGuiaRemisionFacturaCompleta;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonCancelar;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonRegistrar;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoCodigo;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoDireccion;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoNumeroComprobante;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoNumeroIdentificacion;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoRazonSocial;
@CDIView("GR")
public class GuiaRemisionView extends VerticalLayout implements View {

	@EJB
	private GeneradorGuiaRemisionFacturaCompleta generadorGuia;
	private BotonCancelar botonCancelar;
	@Inject
	private UserInfo userInfo;
	private CampoNumeroComprobante numeroDocumento;
	private CampoNumeroIdentificacion identificacionTransportista;
	private ComboBox tipoIdentificacionTransportista;
	private CampoRazonSocial razonSocialTransportista;
	private CampoCodigo placa;
	private DateField fechaInicial;
	private DateField fechaFinal;
	private CampoDireccion direccionDestino;
	private BotonRegistrar botonRegistrar;
	@Override
	public void enter(ViewChangeEvent event) {
		

	}
	public GuiaRemisionView() {
		super();
		botonCancelar=new BotonCancelar();
		numeroDocumento=new CampoNumeroComprobante();
		identificacionTransportista=new CampoNumeroIdentificacion();
		tipoIdentificacionTransportista=new ComboBox();
		tipoIdentificacionTransportista.addItem("04");
		tipoIdentificacionTransportista.addItem("05");
		tipoIdentificacionTransportista.addItem("06");
		tipoIdentificacionTransportista.addItem("07");
		tipoIdentificacionTransportista.addItem("08");
		tipoIdentificacionTransportista.setItemCaption("04", "RUC");
		tipoIdentificacionTransportista.setItemCaption("05", "CEDULA");
		tipoIdentificacionTransportista.setItemCaption("06", "PASAPORTE");
		tipoIdentificacionTransportista.setItemCaption("07", "CONSUMIDOR FINAL");
		tipoIdentificacionTransportista.setItemCaption("08", "IDENTIFICADOR EXTERIOR");		
		tipoIdentificacionTransportista.setNullSelectionAllowed(false);
		tipoIdentificacionTransportista.setValue("04");
		tipoIdentificacionTransportista.setWidth("140px");
		razonSocialTransportista=new CampoRazonSocial();
		razonSocialTransportista.setDescription("ingrese la razón social del transportista");
		placa=new CampoCodigo();
		placa.setInputPrompt("GPR0499");
		fechaInicial=new DateField();
		GregorianCalendar hoy=new GregorianCalendar();
		fechaInicial.setValue(hoy.getTime());
		fechaFinal=new DateField();
		hoy.add(java.util.Calendar.DAY_OF_MONTH, 1);
		
		fechaFinal.setValue(hoy.getTime());
		direccionDestino=new CampoDireccion();
		HorizontalLayout l1=new HorizontalLayout();
		l1.setSpacing(true);
		l1.addComponent(tipoIdentificacionTransportista);
		l1.addComponent(new Label("ID/RUC"));
		l1.addComponent(identificacionTransportista);
		l1.addComponent(new Label("Razón Social"));
		l1.addComponent(razonSocialTransportista);
		l1.addComponent(new Label("Placa"));
		l1.addComponent(placa);
		l1.addComponent(new Label("Doc N°"));
		l1.addComponent(numeroDocumento);
		
		HorizontalLayout l2=new HorizontalLayout();
		l2.setSpacing(true);
		l2.addComponent(new Label("Inicio"));
		l2.addComponent(fechaInicial);
		l2.addComponent(new Label("Fin"));
		l2.addComponent(fechaFinal);
		l2.addComponent(new Label("Dir, Partida"));
		l2.addComponent(direccionDestino);
		botonRegistrar=new BotonRegistrar();
		l2.addComponent(botonRegistrar);
		l2.addComponent(botonCancelar);
		
		setMargin(true);
		setSpacing(true);
		addComponent(l1);
		addComponent(l2);
		setComponentAlignment(l1, Alignment.TOP_CENTER);
		setComponentAlignment(l2, Alignment.TOP_CENTER);
	}

	
	
	@PostConstruct
	public void iniciarEventos(){
		botonCancelar.addClickListener(evento->{
			UI.getCurrent().getNavigator().navigateTo("menu");
		});
		botonRegistrar.addClickListener(evento->{
			String claveAcceso = generadorGuia.generarGuiaRemisionFacturaCompleta(userInfo.getRucEmisor(), numeroDocumento.getValue(), identificacionTransportista.getValue(),(String) tipoIdentificacionTransportista.getValue(), razonSocialTransportista.getValue(), placa.getValue(), fechaInicial.getValue(), fechaFinal.getValue(), direccionDestino.getValue());
			
			Notification.show(claveAcceso, Notification.Type.HUMANIZED_MESSAGE);
		});
	}
}
