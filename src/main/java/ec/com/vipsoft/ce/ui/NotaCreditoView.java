package ec.com.vipsoft.ce.ui;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ec.com.vipsoft.ce.backend.managedbean.UserInfo;
import ec.com.vipsoft.ce.backend.service.LectorDetallesFacturaAprobada;
import ec.com.vipsoft.ce.comprobantesNeutros.FacturaDetalleBinding;
import ec.com.vipsoft.ce.comprobantesNeutros.NotaCreditoBinding;
import ec.com.vipsoft.ce.services.recepcionComprobantesNeutros.ReceptorNotaCreditoNeutra;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonBuscar;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonCancelar;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonRegistrar;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoNumeroComprobante;

@CDIView("NC")
public class NotaCreditoView extends VerticalLayout implements View {

	private static final long serialVersionUID = 2987308144303720303L;
	private CampoNumeroComprobante numeroFactura;
	private BotonBuscar botonCargarFactura;
	private Grid gridDetalles;
	private BotonRegistrar botonRegistrar;
	private BotonCancelar botonCancelar;
	private TextField motivo;
	private BeanItemContainer<FacturaDetalleBinding> beanContainerDetalles;
	@Inject
	private UserInfo userInfo;
	@Inject
	private UtilClaveAcceso utilClaveAcceso;
	@EJB
	private LectorDetallesFacturaAprobada lectorDatos;
	@EJB
	private ReceptorNotaCreditoNeutra receptorFactura;
	

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	public NotaCreditoView() {
		super();
		setSpacing(true);
		setMargin(true);
		setSizeFull();
		numeroFactura=new CampoNumeroComprobante();
		botonCargarFactura=new BotonBuscar();
		gridDetalles=new Grid();
		gridDetalles.setSizeFull();
		gridDetalles.setSelectionMode(SelectionMode.MULTI);
		beanContainerDetalles=new BeanItemContainer<FacturaDetalleBinding>(FacturaDetalleBinding.class);
		gridDetalles.setContainerDataSource(beanContainerDetalles);
		gridDetalles.setEditorEnabled(true);
		HorizontalLayout l1=new HorizontalLayout();
		l1.setSpacing(true);
		l1.addComponent(new Label("Motivo"));
		motivo=new TextField();
		motivo.setWidth("200px");
		l1.addComponent(motivo);
		l1.addComponent(new Label("Factura N°"));
		l1.addComponent(numeroFactura);
		l1.addComponent(botonCargarFactura);
		addComponent(l1);
		addComponent(gridDetalles);
		HorizontalLayout l2=new HorizontalLayout();
		l2.setSpacing(true);
		l2.addComponent(new Label("Total acreditado $0.00"));
		botonRegistrar=new BotonRegistrar();
		botonCancelar=new BotonCancelar();
		l2.addComponent(botonRegistrar);
		l2.addComponent(botonCancelar);
		addComponent(l2);
		setComponentAlignment(gridDetalles, Alignment.TOP_CENTER);
		setComponentAlignment(l2, Alignment.TOP_RIGHT);
		setExpandRatio(l1, 1);
		setExpandRatio(gridDetalles, 8);
		setExpandRatio(l2, 1);
	}

	@PostConstruct
	public void iniciarEventos(){
		botonCancelar.addClickListener(event->UI.getCurrent().getNavigator().navigateTo("menu"));
		botonCargarFactura.addClickListener(event->{
			beanContainerDetalles.removeAllItems();
			beanContainerDetalles.addAll(lectorDatos.leerDetallesDeFactura(userInfo.getRucEmisor(), numeroFactura.getValue()));
			numeroFactura.setReadOnly(true);			
		});
		botonRegistrar.addClickListener(event->{
			NotaCreditoBinding notaCreditoBinding=new NotaCreditoBinding();
			notaCreditoBinding.setRucEmisor(userInfo.getRucEmisor());
			notaCreditoBinding.setCodigoEstablecimiento(userInfo.getCodigoEstablecimiento());
			notaCreditoBinding.setCodigoPuntoVenta(userInfo.getPuntoEmision());
			notaCreditoBinding.setNumeroFacturaAModificar(numeroFactura.getValue());
			notaCreditoBinding.setMotivoAModificar(motivo.getValue());
			Collection<Object> selectedRows = gridDetalles.getSelectedRows();
			for(Object row:selectedRows){
				FacturaDetalleBinding fdb=(FacturaDetalleBinding)row;
				notaCreditoBinding.getDetallesAModficar().add(fdb);
			}
			String claveAcceso = receptorFactura.procesarNotaCredito(notaCreditoBinding);
			Notification.show(claveAcceso, Notification.Type.HUMANIZED_MESSAGE);
			UI.getCurrent().getNavigator().navigateTo("comprobantes");
			
			
		});
	}
}
