package ec.com.vipsoft.ce.ui;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import ec.com.vipsoft.ce.backend.managedbean.UserInfo;
import ec.com.vipsoft.ce.backend.service.RegistradorDemografia;
import ec.com.vipsoft.erp.abinadi.dominio.DemografiaCliente;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonAnadir;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonBuscar;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonCancelar;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoCorreoElectronico;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoDireccion;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoNumeroIdentificacion;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoRazonSocial;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoRuc;
import ec.com.vipsoft.erp.gui.componentesbasicos.HorizontalL;
@CDIView("gestionNotificacion")
public class GestionNotificacion extends VerticalLayout implements View {
	
	@Inject
	private UserInfo userInfo;
	@EJB
	private RegistradorDemografia gestorDemog;
	private DemografiaCliente nuevoDato;
	private static final long serialVersionUID = -2498807362711439289L;
	private CampoRuc id;
	private CampoRazonSocial razonSocial;
	private CampoDireccion direccion;
	private CampoCorreoElectronico correoElectronico;
	private BotonAnadir botonAnadir;
	private BotonBuscar botonBuscar;
	private Grid grid;
	private BeanItemContainer<DemografiaCliente> beanItemC;
	private BotonCancelar botonCancelar;
private FieldGroup fg;
private BeanItem<DemografiaCliente> beanItem;
private ComboBox tipoIdentificacion;
	public GestionNotificacion() {
		super();
		setMargin(true);
		setSpacing(true);
		setSizeFull();
		HorizontalL h1=new HorizontalL();
		id=new CampoRuc();
		razonSocial=new CampoRazonSocial();
		razonSocial.setWidth("320px");
		direccion=new CampoDireccion();
		direccion.setWidth("450px");
		correoElectronico=new CampoCorreoElectronico();
		correoElectronico.setWidth("260px");
		
		tipoIdentificacion=new ComboBox();
		tipoIdentificacion.addItem("04");
		tipoIdentificacion.addItem("05");
		tipoIdentificacion.addItem("06");
		tipoIdentificacion.addItem("07");
		tipoIdentificacion.addItem("08");
		tipoIdentificacion.setItemCaption("04", "RUC");
		tipoIdentificacion.setItemCaption("05", "CEDULA");
		tipoIdentificacion.setItemCaption("06", "PASAPORTE");
		tipoIdentificacion.setItemCaption("07", "CONSUMIDOR FINAL");
		tipoIdentificacion.setItemCaption("08", "IDENTIFICADOR EXTERIOR");		
		tipoIdentificacion.setNullSelectionAllowed(false);
		tipoIdentificacion.setValue("04");
		tipoIdentificacion.setWidth("140px");
		
		
	
		h1.addComponent(tipoIdentificacion,id);
		h1.addComponent("Razón Social",razonSocial);
		//h1.addComponent("Dirección",direccion);
		h1.addComponent("Email",correoElectronico);
		botonAnadir=new BotonAnadir();
		botonBuscar=new BotonBuscar();
		
		addComponent(h1);
		setComponentAlignment(h1, Alignment.TOP_CENTER);		
		HorizontalL h2=new HorizontalL();
		h2.addComponent("direccion",direccion);
		h2.addComponent(botonAnadir,botonBuscar);
		addComponent(h2);
		
		grid=new Grid();
		beanItemC=new BeanItemContainer<DemografiaCliente>(DemografiaCliente.class);
		grid.setContainerDataSource(beanItemC);
//		DemografiaCliente d=new DemografiaCliente();
//		beanItemC.addBean(d);
		grid.removeColumn("entidadEmisora");
		grid.getColumn("id").setHeaderCaption("Identificacion");
		grid.setColumnOrder("razonSocial","identificacion","direccion","correoElectronico");
		grid.setSizeFull();
		grid.removeColumn("tipoIdentificacion");
		grid.removeColumn("id");
		botonCancelar=new BotonCancelar();
		
		addComponent(grid);
		addComponent(botonCancelar);
		setComponentAlignment(botonCancelar, Alignment.BOTTOM_RIGHT);
		setComponentAlignment(h2, Alignment.TOP_CENTER);
		setExpandRatio(h1, 1);
		setExpandRatio(h2, 1);
		setExpandRatio(grid, 8);
		setExpandRatio(botonCancelar, 1);
		nuevoDato=new DemografiaCliente();
		beanItem=new BeanItem<DemografiaCliente>(nuevoDato);		
		fg=new FieldGroup();
		fg.setItemDataSource(beanItem);
		
		
	}
	@Override
	public void enter(ViewChangeEvent event) {
	//	inicialiarNuevoDato();
		llenarDatos();
	}
//	private void inicialiarNuevoDato(){
//		nuevoDato=new DemografiaCliente();
//		beanItem=new BeanItem<DemografiaCliente>(nuevoDato);		
//		fg=new FieldGroup();
//		fg.setItemDataSource(beanItem);
//	}
	
	public void llenarDatos(){
		beanItemC.removeAllItems();
		beanItemC.addAll(gestorDemog.listarClientes(userInfo.getRucEmisor()));
	}
	
	@PostConstruct
	public void establecerEventos(){
		llenarDatos();
		botonCancelar.addClickListener(event->{
			EventosComunes.alMenuPrincipal();
		});
		botonAnadir.addClickListener(event->{
			
			try{
				DemografiaCliente n=new DemografiaCliente();
				n.setCorreoElectronico(correoElectronico.getValue());
				n.setDireccion(direccion.getValue().toUpperCase());
				n.setRazonSocial(razonSocial.getValue().toUpperCase());
				n.setTipoIdentificacion((String)tipoIdentificacion.getValue());
				n.setIdentificacion(id.getValue());				
				beanItemC.addBean(n);
				gestorDemog.registrarActualizaCliente(n, userInfo.getRucEmisor());
				
			}catch(Exception e){
				e.printStackTrace();
			}
		});
	}
}
