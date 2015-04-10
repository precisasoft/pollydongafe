package ec.com.vipsoft.ce.ui;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ec.com.vipsoft.erp.gui.componentesbasicos.BotonBuscar;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonPreferencias;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonSalir;
import ec.com.vipsoft.erp.gui.componentesbasicos.ComboTipoDocumento;
@CDIView("portal")
public class PortalView extends VerticalLayout implements View{

	private static final long serialVersionUID = 196537394702809526L;
	private BotonSalir botonSalir;
	private BotonPreferencias preferencias;
	private Label labelNombreUsuario;
	private ComboTipoDocumento comboTipoDocumento;
	private TextField criterioBusquedaTexto;
	private BotonBuscar botonBusqueda;
	private Grid gridComprobantes;
	public PortalView() {
		super();
		setMargin(true);
		setSpacing(true);
		
		HorizontalLayout layoutsessionPreferencias=new HorizontalLayout();
		layoutsessionPreferencias.setSpacing(true);
		
		labelNombreUsuario=new Label();		
		labelNombreUsuario.setValue("christian valverde");
		preferencias=new BotonPreferencias();
		botonSalir=new BotonSalir();
		comboTipoDocumento=new ComboTipoDocumento();
		Label labelEspacio=new Label("  ");
		labelEspacio.setWidth("100px");
		botonSalir.addClickListener(event -> {
			//SecurityUtils.getSubject().logout();
			UI.getCurrent().getNavigator().navigateTo("login");
			VaadinSession.getCurrent().getSession().invalidate();
		});
		OptionGroup criterioBusqueda=new OptionGroup();
		criterioBusqueda.addItem("porClave");
		criterioBusqueda.addItem("pornumdoc");
		criterioBusqueda.setItemCaption("porClave", "Clave de Acceso");
		criterioBusqueda.setItemCaption("pornumdoc", "Número de Documento");
		criterioBusqueda.setStyleName("horizontal");
		criterioBusquedaTexto=new TextField();
		criterioBusquedaTexto.setWidth("200px");
		botonBusqueda=new BotonBuscar();
		layoutsessionPreferencias.addComponent(labelEspacio);
		Label liconoUsuario=new Label(" ");
		liconoUsuario.setIcon(FontAwesome.USER);
		layoutsessionPreferencias.addComponent(comboTipoDocumento);
		layoutsessionPreferencias.addComponent(criterioBusqueda);
		layoutsessionPreferencias.addComponent(criterioBusquedaTexto);
		layoutsessionPreferencias.addComponent(botonBusqueda);
		layoutsessionPreferencias.addComponent(labelEspacio);
		layoutsessionPreferencias.addComponent(liconoUsuario);
		layoutsessionPreferencias.addComponent(labelNombreUsuario);
		layoutsessionPreferencias.addComponent(preferencias);
		layoutsessionPreferencias.addComponent(botonSalir);
		
		addComponent(layoutsessionPreferencias);
		setComponentAlignment(layoutsessionPreferencias, Alignment.TOP_RIGHT);
		gridComprobantes=new Grid();
		
		gridComprobantes.addColumn("Fecha Emisión");
		gridComprobantes.addColumn("Tipo");
		gridComprobantes.addColumn("Número de  Documento");
		gridComprobantes.addColumn("Clave Acceso");
		gridComprobantes.addColumn("Autorización");
		gridComprobantes.addColumn("Fecha Autorización");
		gridComprobantes.addColumn("XML");
		gridComprobantes.addColumn("PDF ");
		gridComprobantes.setWidth("60%");
		gridComprobantes.setEditorEnabled(true);
		//gridComprobantes.setSizeFull();
		addComponent(gridComprobantes);
		setComponentAlignment(gridComprobantes, Alignment.MIDDLE_CENTER);
	}
	@Override
	public void enter(ViewChangeEvent event) {
//		if(SecurityUtils.getSubject().isAuthenticated()){
//			if(!SecurityUtils.getSubject().hasRole("lector")){
//				UI.getCurrent().getNavigator().navigateTo("login");
//			}
//		}else{
//			UI.getCurrent().getNavigator().navigateTo("login");
//		}
		
	}

}
