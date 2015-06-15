package ec.com.vipsoft.ce.ui;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ec.com.vipsoft.ce.backend.managedbean.UserInfo;
import ec.com.vipsoft.ce.backend.service.ImportadorComprobanteElectronico;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonCancelar;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonRegistrar;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoClaveAcceso;
import ec.com.vipsoft.erp.gui.componentesbasicos.HorizontalL;

@CDIView("importar")
public class ImportadorComprobanteUI extends VerticalLayout implements View {

	private static final long serialVersionUID = -2322505750446653928L;
	@EJB
	private ImportadorComprobanteElectronico importadorComprobante;
	@Inject
	private UserInfo userInfo;
	public ImportadorComprobanteUI() {
		super();
		l1=new HorizontalL();
		claveAcceso=new CampoClaveAcceso();
		botonRegistrar=new BotonRegistrar();
		botonCancelar=new BotonCancelar();
		l1.addComponent("claveAcceso",claveAcceso);
		l1.addComponent(botonRegistrar,botonCancelar);
		setMargin(true);		
		setSpacing(true);
		addComponent(l1);
	}
	public void enter(ViewChangeEvent event) {
		

	}
	@PostConstruct
	public void acciones(){
		botonCancelar.addClickListener(event->UI.getCurrent().getNavigator().navigateTo("menu"));
		botonRegistrar.addClickListener(event->{
			String numeroAutorizacion = importadorComprobante.importarComprobantePorClaveAcceso(userInfo.getRucEmisor(), claveAcceso.getValue());
			Notification.show(numeroAutorizacion, Notification.Type.HUMANIZED_MESSAGE);
		});
	}
	
	private HorizontalL l1;
	private CampoClaveAcceso claveAcceso;
	private BotonRegistrar botonRegistrar;
	private BotonCancelar botonCancelar;

}
