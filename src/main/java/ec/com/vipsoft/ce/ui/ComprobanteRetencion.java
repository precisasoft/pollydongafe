package ec.com.vipsoft.ce.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ec.com.vipsoft.ce.backend.managedbean.UserInfo;
import ec.com.vipsoft.ce.comprobantesNeutros.ComprobanteRetencionBinding;
import ec.com.vipsoft.ce.comprobantesNeutros.ImpuestoRetencion;
import ec.com.vipsoft.ce.services.recepcionComprobantesNeutros.ReceptorComprobanteRetencionNeutra;
import ec.com.vipsoft.ce.utils.LlenadorNumeroComprobante;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonAnadir;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonCancelar;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonRegistrar;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoDinero;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoNumeroComprobante;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoNumeroIdentificacion;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoRazonSocial;

@CDIView("CR")
public class ComprobanteRetencion extends VerticalLayout implements View {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1722007230448590172L;
	private CampoNumeroIdentificacion identificacionBeneficiario;
	private CampoRazonSocial razonSocialBeneficiario;
	private DateField fechaComprobante;
	private ComboBox tipoDocumento;
	private CampoNumeroComprobante camponumeroFactura;
	private CampoDinero campoBaseImponible;
	private ComboBox comboImpuesto;
	private TextField comboCodigoImpuesto;
	private BotonAnadir botnAnadirDetalle;
	private Table tablaDetalles;
	private TextField campoPorcentajeARetener;
	private ComprobanteRetencionBinding comprobante;
	private ImpuestoRetencion impuestoRetencion;
	private BigDecimal _baseImponible=new BigDecimal("0.00");	
	private List<ImpuestoRetencion>detalles;
	private BeanItem<ComprobanteRetencionBinding>bi;
	@Inject
	private LlenadorNumeroComprobante llenadorNumeroDocumento;
	@Inject 
	private UserInfo userInfo;
//	@Inject
//	private ConversorComprobanteToString conversorComprobanteEnString;
	private BotonRegistrar botonRegistrar;
	private BotonCancelar botonCancelar;
	@EJB
	private ReceptorComprobanteRetencionNeutra receptorComprobanteRetencionNeutra;
	@Inject
	private UtilClaveAcceso utilClaveAcceso;
	public ComprobanteRetencion() {
		super();
		detalles=new ArrayList<ImpuestoRetencion>();
		comprobante=new ComprobanteRetencionBinding();
		
		
		//fg.setItemDataSource(itemDataSource);
	}
	@Override
	public void enter(ViewChangeEvent event) {
		if(userInfo.getCodigoEstablecimiento()==null){
			Notification.show("ERROR", "Ud no tiene asignado un establecimiento y/o punto de emisión", Type.ERROR_MESSAGE);
			botonRegistrar.setEnabled(false);
		}

	}

	@PostConstruct
	public void iniciar(){
		camponumeroFactura=new CampoNumeroComprobante();
		razonSocialBeneficiario=new CampoRazonSocial();
		fechaComprobante=new DateField();
		fechaComprobante.setDateFormat("dd/MM/yyyy");	
		fechaComprobante.setValue(new Date());
		fechaComprobante.setWidth("140px");
		tipoDocumento=new ComboBox();
		tipoDocumento.setNullSelectionAllowed(false);
		tipoDocumento.addItem("01");
		tipoDocumento.addItem("04");
		tipoDocumento.addItem("05");		
		tipoDocumento.setItemCaption("01", "FACTURA");
		tipoDocumento.setItemCaption("04", "NOTA CREDITO");
		tipoDocumento.setItemCaption("05", "NOTA DEBITO");	
		tipoDocumento.setWidth("130px");
		identificacionBeneficiario=new CampoNumeroIdentificacion();
		Label lidentificacion=new Label("RUC/ID");
		HorizontalLayout l1=new HorizontalLayout();
		l1.setSpacing(true);
		l1.addComponent(lidentificacion);
		l1.addComponent(identificacionBeneficiario);
		Label lrazonsocial=new Label("Razón Social");
		l1.addComponent(lrazonsocial);
		l1.addComponent(razonSocialBeneficiario);
		Label ltpodoc=new Label("Doc");
		l1.addComponent(ltpodoc);
		l1.addComponent(tipoDocumento);
		Label lnumdoc=new Label("Num.");
		l1.addComponent(lnumdoc);
		l1.addComponent(camponumeroFactura);
		fechaComprobante.setDescription("determine la fecha de emisión del comprobante a retener");
		Label lperiodo=new Label("F. Doc.");
		l1.addComponent(lperiodo);
		l1.addComponent(fechaComprobante);
		
		campoBaseImponible=new CampoDinero();
		
		///////////////////////////////////////////////////////////
		HorizontalLayout l2=new HorizontalLayout();
		l2.setSpacing(true);
		comboImpuesto=new ComboBox();
		comboImpuesto.setNullSelectionAllowed(false);
		comboImpuesto.addItem("1");
		comboImpuesto.addItem("2");
		comboImpuesto.addItem("6");
		comboImpuesto.setItemCaption("1", "RENTA");
		comboImpuesto.setItemCaption("2", "IVA");
		comboImpuesto.setItemCaption("6", "ISD");
		Label ltipoImpuest=new Label("Tipo");
		l2.addComponent(ltipoImpuest);
		l2.addComponent(comboImpuesto);
		Label lcodigoRe=new Label("Cód.");
		l2.addComponent(lcodigoRe);
		comboCodigoImpuesto=new TextField();
		comboCodigoImpuesto.setWidth("50px");
		
		
		l2.addComponent(comboCodigoImpuesto);
		Label lporcentajeARetener=new Label("% a retener");
		l2.addComponent(lporcentajeARetener);
		campoPorcentajeARetener=new TextField();
		campoPorcentajeARetener.setWidth("65px");
		l2.addComponent(campoPorcentajeARetener);
		Label lbaseImponible=new Label("Base Imponible");
		l2.addComponent(lbaseImponible);
		
		campoBaseImponible=new CampoDinero();
		
	//	campoBaseImponible.setPropertyDataSource(opbaseImponible);
		l2.addComponent(campoBaseImponible);
		botnAnadirDetalle=new BotonAnadir();
		l2.addComponent(botnAnadirDetalle);
		
		HorizontalLayout l3=new HorizontalLayout();
		l3.setWidth("100%");
		tablaDetalles=new Table();
		tablaDetalles.setWidth("100%");
		tablaDetalles.setPageLength(4);
		//BeanItemContainer<ImpuestoRetencion>bicImpuestoRetencion=new BeanItemContainer<ImpuestoRetencion>(ImpuestoRetencion.class);		
	//	bicImpuestoRetencion.addAll(detalles);
	//	tablaDetalles.setContainerDataSource(bicImpuestoRetencion);
		
	//	tablaDetalles.setVisibleColumns(new Object[]{"codigo","baseImponible","porcentajeRetencion","valorRetenido"});
		l3.addComponent(tablaDetalles);
		HorizontalLayout l4=new HorizontalLayout();
		l4.setSpacing(true);
		botonRegistrar=new BotonRegistrar();
		botonCancelar=new BotonCancelar();
		l4.addComponent(botonRegistrar);
		l4.addComponent(botonCancelar);
		setMargin(true);
		setSpacing(true);
		addComponent(l1);
		addComponent(l2);
		addComponent(l3);
		addComponent(l4);
		setComponentAlignment(l1, Alignment.MIDDLE_CENTER);
		setComponentAlignment(l2, Alignment.MIDDLE_CENTER);
		setComponentAlignment(l3, Alignment.MIDDLE_CENTER);
		setComponentAlignment(l4, Alignment.MIDDLE_RIGHT);
		 Responsive.makeResponsive(l1);
		 Responsive.makeResponsive(l2);
		 Responsive.makeResponsive(l3);
		 Responsive.makeResponsive(this);	
	//	 campoBaseImponible.setPropertyDataSource(op_baseImponible);
		 
		 
		 bi=new BeanItem<ComprobanteRetencionBinding>(comprobante);		 
		 FieldGroup fg=new FieldGroup();
		 fg.setItemDataSource(bi);
		 fg.bind(identificacionBeneficiario,"identificacionBeneficiario");
		 fg.bind(razonSocialBeneficiario, "razonSocialBeneficiario");
		 fg.bind(tipoDocumento,"codigoDocumento");
		 fg.bind(camponumeroFactura, "numeroDocumento");
		 fg.bind(fechaComprobante, "fechaDocumento");
		 fg.bind(comboCodigoImpuesto, "codigoImpuesto");
		 fg.bind(comboImpuesto, "tipoImpuesto");
		 fg.bind(campoBaseImponible, "baseImponible");
		 fg.bind(campoPorcentajeARetener, "porcentajeRetencion");
		 
		BeanItemContainer<ImpuestoRetencion>biir=new BeanItemContainer<ImpuestoRetencion>(ImpuestoRetencion.class) ;
		biir.addAll(comprobante.getImpuestos());
		tablaDetalles.setContainerDataSource(biir);
		tablaDetalles.setSelectable(true);
		tablaDetalles.setMultiSelect(false);
		botnAnadirDetalle.addClickListener(event -> {
			try {
				
				camponumeroFactura.setValue(llenadorNumeroDocumento.llenarNumeroDocumento(camponumeroFactura.getValue()));
		
				fg.commit();
				comprobante.anadirNuevoDetalle();
				biir.removeAllItems();
				biir.addAll(comprobante.getImpuestos());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
		});
		botonCancelar.addClickListener(event -> {UI.getCurrent().getNavigator().navigateTo("menu");});
		botonRegistrar.addClickListener(event->{
			{
				try {
					camponumeroFactura.setValue(llenadorNumeroDocumento.llenarNumeroDocumento(camponumeroFactura.getValue()));
					fg.commit();
					if(((identificacionBeneficiario.getValue()!=null)&&(identificacionBeneficiario.getValue().length()>0))&&(razonSocialBeneficiario.getValue()!=null)&&(razonSocialBeneficiario.getValue().length()>0)){					
						if(!comprobante.getImpuestos().isEmpty()){							
							comprobante.getInfoTributaria().setDireccionMatriz(userInfo.getDireccionMatriz());
							comprobante.getInfoTributaria().setEstablecimiento(userInfo.getCodigoEstablecimiento());
							comprobante.getInfoTributaria().setPuntoEmision(userInfo.getPuntoEmision());
							comprobante.getInfoTributaria().setNombreComercial(userInfo.getNombreComercial());
							comprobante.getInfoTributaria().setRazonSocialEmisor(userInfo.getRazonSocialEmisor());
							comprobante.getInfoTributaria().setDireccionMatriz(userInfo.getDireccionMatriz());			
							comprobante.getInfoTributaria().setRucEmisor(userInfo.getRucEmisor());
							comprobante.setPeriodoFiscal(fechaComprobante.getValue());
							String claveAcceso=receptorComprobanteRetencionNeutra.receptarComprobanteRetencion(comprobante);
							StringBuilder sbnumerodocumento=new StringBuilder();
							sbnumerodocumento.append(utilClaveAcceso.obtenerCodigoEstablecimiento(claveAcceso));
							sbnumerodocumento.append("-");
							sbnumerodocumento.append(utilClaveAcceso.obtenerCodigoPuntoEmision(claveAcceso));
							sbnumerodocumento.append("-").append(utilClaveAcceso.obtenerSecuanciaDocumento(claveAcceso));
							Notification.show("Resultado", "Documento "+sbnumerodocumento.toString()+"  clave de acceso "+claveAcceso,Notification.TYPE_HUMANIZED_MESSAGE);
						}
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});				 		 
	}
}
