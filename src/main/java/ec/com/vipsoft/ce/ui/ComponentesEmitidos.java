package ec.com.vipsoft.ce.ui;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.EJB;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;

import ec.com.vipsoft.ce.backend.managedbean.UserInfo;
import ec.com.vipsoft.ce.backend.service.ListarComprobantesEmitidos;
import ec.com.vipsoft.cryptografia.CryptoUtil;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonBuscar;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonCancelar;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonSpredSheet;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoNumeroComprobante;
import ec.com.vipsoft.erp.gui.componentesbasicos.HorizontalL;

@CDIView("comprobantes")
public class ComponentesEmitidos extends VerticalLayout implements View{

	@EJB
	private ListarComprobantesEmitidos listadoComprobantesEmitidos;
	@Inject 
	private UserInfo userInfo;
	private static final long serialVersionUID = 5820107472515714341L;
	private Grid grid;
	private BeanItemContainer<ComprobanteRideXmlBean>beanItemContainer;
	private BotonCancelar botonCancelar;
	private ComboBox tipoDocumento;
	private DateField fechaInicial;
	private DateField fechaFinal;
	private OptionGroup produccionoPruebas;
	private TextField numeroComprobante;
	private ComboBox aprobadosRechazadosCombo;
	private BotonBuscar botonBuscar;
	private BotonSpredSheet botonReporteXLS;
	
	@Inject
	private CryptoUtil cryptoUtil;
	@Override
	public void enter(ViewChangeEvent event) {
	//	actualizarVista();
		
	}
	public ComponentesEmitidos() {
		super();
		
		produccionoPruebas=new OptionGroup();
		produccionoPruebas.addItems("produccion");
		produccionoPruebas.addItems("pruebas");
		produccionoPruebas.setItemCaption("produccion", "PRODUCCION");
		produccionoPruebas.setItemCaption("pruebas", "PRUEBAS");
		produccionoPruebas.setMultiSelect(false);
		produccionoPruebas.setValue("produccion");
		//produccionoPruebas.setStyleName("horizontal");
		beanItemContainer=new BeanItemContainer<ComprobanteRideXmlBean>(ComprobanteRideXmlBean.class);	
		grid=new Grid(beanItemContainer);	
		grid.setColumnOrder("fecha","numeroDocumento","autorizacion","monto","nota");
		grid.removeColumn("tipo");
		grid.removeColumn("claveAcceso");
		grid.getColumn("numeroDocumento").setRenderer(new HtmlRenderer());		
		grid.getColumn("autorizacion").setRenderer(new HtmlRenderer());
		grid.getColumn("numeroDocumento").setHeaderCaption("Número");
		grid.getColumn("autorizacion").setHeaderCaption("Autorización");
		//Grid.Column columnaClaveAcceso=grid.getColumn("claveAcceso");
		Grid.Column columnaNumero=grid.getColumn("numeroDocumento");
		Grid.Column columnautorizacion=grid.getColumn("autorizacion");
		Grid.Column columnanota=grid.getColumn("nota");
		Grid.Column colunafecha=grid.getColumn("fecha");
		Grid.Column columnaMonto=grid.getColumn("monto");
		//columnaClaveAcceso.setExpandRatio(2);
		columnautorizacion.setExpandRatio(2);
		columnaNumero.setExpandRatio(1);
		columnanota.setExpandRatio(1);
		colunafecha.setExpandRatio(1);
		columnaMonto.setExpandRatio(1);
		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.NONE);
		
		setMargin(true);
		setSpacing(true);
		setSizeFull();
		HorizontalL l1=new HorizontalL();
		tipoDocumento=new ComboBox();
		tipoDocumento.addItem("FACTURA");
		tipoDocumento.addItem("NC");
		tipoDocumento.addItem("GR");
		tipoDocumento.addItem("CR");
		tipoDocumento.setItemCaption("FACTURA", "FACTURA");
		tipoDocumento.setItemCaption("NC","NOTA DE CRÉDITO");
		tipoDocumento.setItemCaption("GR", "GUÍA DE REMISIÓN");
		tipoDocumento.setItemCaption("CR","COMPROBANTE RETENCIÓN");
		tipoDocumento.setNullSelectionAllowed(false);
		tipoDocumento.setValue("FACTURA");
		botonCancelar=new BotonCancelar();
		botonCancelar.addClickListener(event->{
			UI.getCurrent().getNavigator().navigateTo("menu");
			
		});
		numeroComprobante=new CampoNumeroComprobante();
		fechaInicial=new DateField();
		fechaInicial.setDateFormat("dd-MM-yyyy");
		fechaInicial.setValue(new Date());
		fechaFinal=new DateField();
		fechaFinal.setDateFormat("dd-MM-yyyy");
		fechaFinal.setValue(new Date());
		l1.addComponent(tipoDocumento,numeroComprobante);						
		l1.addComponent("fecha",fechaInicial);
		l1.addComponent(fechaFinal);
		l1.addComponent(produccionoPruebas);
	
		botonBuscar=new BotonBuscar();
		botonBuscar.addClickListener(event->{
			String rucEmisor=null;
			
			
			//rucEmisor = cryptoUtil.decrypt(userInfo.getRucEmisor());
			rucEmisor=userInfo.getRucEmisor();
			
			beanItemContainer.removeAllItems();
			String tipoComprobante=(String)tipoDocumento.getValue();
			String numeroDocumento_buscar=numeroComprobante.getValue();
			Set<ComprobanteEmitido>encontrados=new HashSet<>();
			String seleccionado=(String)produccionoPruebas.getValue();
			boolean enPruebas=false;
			if(seleccionado.equalsIgnoreCase("pruebas")){
				enPruebas=true;
			}
			if((numeroDocumento_buscar!=null)&&(numeroDocumento_buscar.length()>0)){
				encontrados.addAll(listadoComprobantesEmitidos.buscarComprobnate(rucEmisor,tipoComprobante,numeroDocumento_buscar,enPruebas));
				if(encontrados.isEmpty()){
					Notification.show("No Encontrado", "no se ha encontrado ningún comprobante ", Notification.Type.ERROR_MESSAGE);					
				}
			}else{
				encontrados.addAll(listadoComprobantesEmitidos.buscarComprobnate(rucEmisor, tipoComprobante, fechaInicial.getValue(),fechaFinal.getValue(),enPruebas));
			}
			
			for(ComprobanteEmitido c:encontrados){											
				ComprobanteRideXmlBean bean=new ComprobanteRideXmlBean();
				StringBuilder sbca=new StringBuilder("<a href='");
				sbca.append(VaadinServlet.getCurrent().getServletContext().getContextPath());
				sbca.append("/VisorRide?claveAcceso=");
				sbca.append(c.getClaveAcceso());
				sbca.append("' target='_blank'>");
				sbca.append(c.getNumeroDocumento());
				sbca.append("</a>");
				//bean.setClaveAcceso(sbca.toString());			
				bean.setNumeroDocumento(sbca.toString());
				StringBuilder sba=new StringBuilder("<a href='");
				sba.append(VaadinServlet.getCurrent().getServletContext().getContextPath());
				sba.append("/VisorAutorizacion?claveAcceso=");
				sba.append(c.getClaveAcceso());
				sba.append("' download >");
				sba.append(c.getNumeroAutorizacion());			
				sba.append("</a>");
				bean.setAutorizacion(sba.toString());
				bean.setFecha(c.getFechaEmision());
				bean.setNota(c.getNota());					
				bean.setMonto(c.getMonto());
				beanItemContainer.addBean(bean);
			}
			grid.setContainerDataSource(beanItemContainer);
			
		});
		l1.addComponent(botonBuscar);
		botonReporteXLS=new BotonSpredSheet();
		l1.addComponent(botonReporteXLS);
		l1.addComponent(botonCancelar);
		
		l1.setComponentAlignment(botonCancelar, Alignment.TOP_RIGHT);
		addComponent(l1);
		addComponent(grid);
		setComponentAlignment(l1, Alignment.TOP_RIGHT);
		setExpandRatio(l1, 1);
		setExpandRatio(grid, 9);
	}
	@PostConstruct
	public void actualizarVista(){
		//System.out.println("llamado postconstruct");
//		Long ultimo=0l;
		
		String tipoComprobante="FACTURA";
		Set<ComprobanteEmitido>encontrados=new HashSet<>();
		String rucEmisor=null;
	
			rucEmisor = userInfo.getRucEmisor();
	
		encontrados.addAll(listadoComprobantesEmitidos.buscarComprobnate(rucEmisor, tipoComprobante, fechaInicial.getValue(),fechaFinal.getValue(),false));
					
		for(ComprobanteEmitido c:encontrados){											
			ComprobanteRideXmlBean bean=new ComprobanteRideXmlBean();
			StringBuilder sbca=new StringBuilder("<a href='");
			sbca.append(VaadinServlet.getCurrent().getServletContext().getContextPath());
			sbca.append("/VisorRide?claveAcceso=");
			sbca.append(c.getClaveAcceso());
			sbca.append("' target='_blank'>");
			sbca.append(c.getNumeroDocumento());
			sbca.append("</a>");
			//bean.setClaveAcceso(sbca.toString());			
			bean.setNumeroDocumento(sbca.toString());
			StringBuilder sba=new StringBuilder("<a href='");
			sba.append(VaadinServlet.getCurrent().getServletContext().getContextPath());
			sba.append("/VisorAutorizacion?claveAcceso=");
			sba.append(c.getClaveAcceso());
			sba.append("' download >");
			sba.append(c.getNumeroAutorizacion());			
			sba.append("</a>");
			bean.setAutorizacion(sba.toString());
			bean.setFecha(c.getFechaEmision());
			bean.setNota(c.getNota());					
			bean.setMonto(c.getMonto());
			beanItemContainer.addBean(bean);
		}
		grid.setContainerDataSource(beanItemContainer);
	}
	

	

}
