package ec.com.vipsoft.ce.ui;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ec.com.vipsoft.ce.backend.managedbean.UserInfo;
import ec.com.vipsoft.ce.backend.service.ListadorBienEconomico;
import ec.com.vipsoft.erp.abinadi.dominio.BienEconomico;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonAnadir;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonCancelar;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonRemoverDetalle;
import ec.com.vipsoft.erp.gui.componentesbasicos.HorizontalL;
@CDIView("bien")
public class BienEconomicView extends VerticalLayout implements View{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1541585452641280368L;
	private ComboBox codigoIva;
	private TextField descripcion;
	private TextField codigo;
	private BotonRemoverDetalle botonBorrar;
	private BotonAnadir botonAnadir;
	private Grid grid;
	private BeanItemContainer<BienEconomico> beanItem;
	private BotonCancelar botonCancelar;
	@EJB
	private ListadorBienEconomico listadorBienes;
	@Inject
	private UserInfo userInfo;
	private OptionGroup productoServicio;
	public BienEconomicView() {
		super();
		codigoIva=new ComboBox();
		codigoIva.addItem("0");
		codigoIva.addItem("2");
		codigoIva.addItem("6");
		codigoIva.addItem("7");
		codigoIva.setValue("2");
		codigoIva.setNullSelectionAllowed(false);
		
		
		codigoIva.setItemCaption("0", "0%");
		codigoIva.setItemCaption("2", "12%");
		codigoIva.setItemCaption("6", "no objeto de impuesto");
		codigoIva.setItemCaption("7", "excento");
		codigo=new TextField();
		descripcion=new TextField();
		HorizontalL l1=new HorizontalL();
		l1.addComponent("Código",codigo);
		l1.addComponent("Descripción",descripcion);
		l1.addComponent("IVA",codigoIva);
		botonAnadir=new BotonAnadir();
		botonBorrar=new BotonRemoverDetalle();
		
		productoServicio=new OptionGroup();
		productoServicio.addItem("servicio");
		productoServicio.addItem("producto");
		productoServicio.setItemCaption("servicio", "servicio");
		productoServicio.setItemCaption("producto", "producto");
		productoServicio.setMultiSelect(false);
		productoServicio.setValue("producto");
		productoServicio.setStyleName("horizontal");
		l1.addComponent(productoServicio);
		l1.addComponent(botonAnadir,botonBorrar);
		setMargin(true);
		setSpacing(true);
		setSizeFull();
		addComponent(l1);
		grid=new Grid();
		grid.setSizeFull();
		beanItem=new BeanItemContainer<BienEconomico>(BienEconomico.class);
		grid.setContainerDataSource(beanItem);
		grid.removeColumn("entidad");
		grid.removeColumn("id");
		grid.removeColumn("codigoIce");
		grid.setColumnOrder("codigo","descripcion","codigoIva");
		addComponent(grid);
		setExpandRatio(l1, 1);
		setExpandRatio(grid, 9);
		botonCancelar=new BotonCancelar();
		botonCancelar.addClickListener(event->UI.getCurrent().getNavigator().navigateTo("menu"));
		addComponent(botonCancelar);
		setComponentAlignment(botonCancelar, Alignment.TOP_RIGHT);
	}
	@Override
	public void enter(ViewChangeEvent event) {
	
		
	}
	@PostConstruct
	public void registrarEventos(){
		botonAnadir.addClickListener(event->{
			String escogido=(String) productoServicio.getValue();
			boolean producto=false;
			if(escogido.equalsIgnoreCase("producto")){
				producto=true;
			}
			listadorBienes.registrarBienEconomico(userInfo.getRucEmisor(), codigo.getValue(), descripcion.getValue(), "2",(String)codigoIva.getValue(), producto);
			codigo.setValue("");
			descripcion.setValue("");
			Set<BienEconomico> listardo = listadorBienes.listarBienesDisponibles(userInfo.getRucEmisor());
			beanItem.removeAllItems();
			beanItem.addAll(listardo);
		});
	}
}
