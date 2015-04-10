/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.vipsoft.ce.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import ec.com.vipsoft.ce.backend.managedbean.UserInfo;
import ec.com.vipsoft.ce.backend.service.ListadorBienEconomico;
import ec.com.vipsoft.ce.comprobantesNeutros.FacturaBinding;
import ec.com.vipsoft.ce.comprobantesNeutros.FacturaDetalleBinding;
import ec.com.vipsoft.ce.services.recepcionComprobantesNeutros.ReceptorFacturaNeutra;
import ec.com.vipsoft.ce.utils.UtilClaveAcceso;
import ec.com.vipsoft.erp.abinadi.dominio.BienEconomico;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonAnadir;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonBuscar;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonCancelar;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonRegistrar;
import ec.com.vipsoft.erp.gui.componentesbasicos.BotonRemoverDetalle;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoCantidad;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoCodigo;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoDinero;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoDireccion;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoFecha;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoNumeroComprobante;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoNumeroIdentificacion;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoRazonSocial;

/**
 *
 * @author chrisvv
 */
@CDIView("FACTURA")
public class FacturaView extends VerticalLayout implements View{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3811283001931905044L;
	private BeanItemContainer<FacturaDetalleBinding> beanContainerDetalles;
    private BeanContainer beanContainerInfoAdicional;
    private BeanContainer beanContainertotales;
    private TextField bienSeleccionado;
    private BotonAnadir botonAnadirDetalle;
    private BotonAnadir botonAnadirInfoAdiconal;
    private BotonBuscar botonBuscarDetalle;
    private BotonCancelar botonCancelar;
    private BotonRemoverDetalle botonEliminarDetalle;
    private BotonRegistrar botonRegistrar;
    private CampoCantidad cantidad;
    private TextField codigoEstablecimientoDestino;
    private TextField codigoP;
    private CampoDinero descuento;
    private CampoDireccion direccion;
    private CampoFecha fechaEmision;
    private TextField formaPago;
	private TextField informacion_adicional;
    //private 
    //private 
	@EJB
	private ReceptorFacturaNeutra recepcionFactura;
	@EJB
	private ListadorBienEconomico listadorBienes;
	
	@Inject
	private UserInfo userInfo;
	private TextField lote;
	private TextField nombreInfoAdicional;
	private CampoNumeroComprobante numeroGuiaRemision;
	private TextField ordenCompra;

    private CampoRazonSocial razonSocialBeneficiario;
    private CampoNumeroIdentificacion rucBeneficiario;
    private Grid tablaDetalles;
    private ComboBox tipoFactura;
    private ComboBox tipoIdentificacion;
    private CampoDinero valorUnitario;
    private ComboBox comboPaisDestino;
    private Label subtotal;
    private Label iva12;
    private Label ice;
    private Label total;
    @Inject
    private UtilClaveAcceso utilClaveAcceso;
	private Button botonSeleccionaDesdeVentanar;
	private TextField tfiltro;
    protected void actualizarTablaTotales(){
    	BigDecimal _subtotal=BigDecimal.ZERO;
    	BigDecimal _iva12=BigDecimal.ZERO;
    	BigDecimal _ice=BigDecimal.ZERO;
    	BigDecimal _total=BigDecimal.ZERO;
    	 for (FacturaDetalleBinding itemIds : beanContainerDetalles.getItemIds()){
    		 _iva12=_iva12.add(itemIds.getIva12());
    		 _ice=_ice.add(itemIds.getIce());
    		 _total=_total.add(itemIds.getValorTotal());
    	 }
    	 iva12.setValue("IVA 12% $"+_iva12.setScale(2, RoundingMode.HALF_UP));
    	 ice.setValue("ICE $"+_ice.setScale(2, RoundingMode.HALF_UP));
    	 total.setValue("TOTAL $"+_total.setScale(2, RoundingMode.HALF_UP));
    }
    public void construirGui(){
        HorizontalLayout l1=new HorizontalLayout();
        l1.setSpacing(true);
        tipoFactura=new ComboBox();
        tipoFactura.addItem("regular");
        tipoFactura.setItemCaption("regular", "REGULAR");
        tipoFactura.addItem("exportacion");
        tipoFactura.setItemCaption("exportacion", "EXPORTACION");
        tipoFactura.addItem("reembolso");
        tipoFactura.setItemCaption("reembolso", "REEMBOLSO");
        tipoFactura.setNullSelectionAllowed(false);
        tipoFactura.setValue("regular");
        
        tipoIdentificacion=new ComboBox();
        
        tipoIdentificacion.addItem("05");
        tipoIdentificacion.setItemCaption("05", "CEDULA");
        tipoIdentificacion.addItem("06");
        tipoIdentificacion.setItemCaption("06", "PASAPORTE");
        tipoIdentificacion.addItem("07");
        tipoIdentificacion.setItemCaption("07", "FINAL");
        tipoIdentificacion.addItem("08");
        tipoIdentificacion.setItemCaption("08", "ID EXTERIOR");
        tipoIdentificacion.addItem("04");
        tipoIdentificacion.setItemCaption("04", "RUC");
        tipoIdentificacion.setNullSelectionAllowed(false);
        tipoIdentificacion.setValue("04");
        
      
        l1.addComponent(tipoIdentificacion);
        rucBeneficiario=new CampoNumeroIdentificacion();
        l1.addComponent(rucBeneficiario);
        Label lrazonSocial=new Label("R. Social");
        l1.addComponent(lrazonSocial);
        razonSocialBeneficiario=new CampoRazonSocial();
        l1.addComponent(razonSocialBeneficiario);
        Label ldireccion=new Label("Dir.");
        l1.addComponent(ldireccion);
        direccion=new CampoDireccion();
        l1.addComponent(direccion);
        fechaEmision=new CampoFecha();
        l1.addComponent(fechaEmision);
        //////////////////////////////////////////
        HorizontalLayout l2=new HorizontalLayout();
        l2.setSpacing(true);
        Label lordenCompra=new Label("OC");        
        setMargin(true);
        l2.addComponent(lordenCompra);
        ordenCompra=new TextField();
        ordenCompra.setWidth("70px");
        l2.addComponent(ordenCompra);
        Label lvencimiento=new Label("Cond. Pago");
        l2.addComponent(lvencimiento);
        formaPago=new CampoCodigo();
        formaPago.setWidth("100px");
        l2.addComponent(formaPago);
        Label lguia=new Label("Guía Remisión");
        l2.addComponent(lguia);
        numeroGuiaRemision=new CampoNumeroComprobante();
        l2.addComponent(numeroGuiaRemision);
        Label lsucursalCliente=new Label("Sucursal cliente");
        l2.addComponent(lsucursalCliente);
        codigoEstablecimientoDestino=new CampoCodigo();
        codigoEstablecimientoDestino.setInputPrompt("001");
        codigoEstablecimientoDestino.setWidth("70px");
        l2.addComponent(codigoEstablecimientoDestino);
        
        
        ////////////////////////////////
        HorizontalLayout l3=new HorizontalLayout();
        l3.setSpacing(true);
        botonBuscarDetalle=new BotonBuscar();
        l3.addComponent(botonBuscarDetalle);
        codigoP=new TextField();
        codigoP.setWidth("80px");
        l3.addComponent(codigoP);
        bienSeleccionado=new TextField();
        bienSeleccionado.setWidth("150px");
        l3.addComponent(bienSeleccionado);
        Label lcantidad=new Label("Cant.");
        l3.addComponent(lcantidad);
        cantidad=new CampoCantidad();
        l3.addComponent(cantidad);
        Label lvalorU=new Label("V. Unitario");
        l3.addComponent(lvalorU);
        valorUnitario=new CampoDinero();
        l3.addComponent(valorUnitario);
        Label ldescuento=new Label("Dcto.");
        l3.addComponent(ldescuento);
        descuento=new CampoDinero();
        l3.addComponent(descuento);
        Label labelLote=new Label("lote");
        l3.addComponent(labelLote);
        lote=new TextField();
        lote.setInputPrompt("lote 323, expira 2016-05-22");
        lote.setWidth("165px");
        botonAnadirDetalle=new BotonAnadir();
        l3.addComponent(lote);
        l3.addComponent(botonAnadirDetalle);
        botonEliminarDetalle=new BotonRemoverDetalle();
        l3.addComponent(botonEliminarDetalle);
        botonRegistrar=new BotonRegistrar();
        botonCancelar = new BotonCancelar();
        //l3.addComponent(botonRegistrar);
        //l3.addComponent(botonCancelar);
        ////////////////////////////////////////
        HorizontalLayout l4=new HorizontalLayout();
        l4.setSizeFull();
        
        tablaDetalles=new Grid();
        beanContainerDetalles=new BeanItemContainer<FacturaDetalleBinding>(FacturaDetalleBinding.class);
        tablaDetalles.setContainerDataSource(beanContainerDetalles);
     //   tablaDetalles.setEditorEnabled(true);
        
        
//        tablaDetalles.setPageLength(5);
//        tablaDetalles.setSelectable(true);
//        tablaDetalles.setMultiSelect(false);
        tablaDetalles.setWidth("100%");
        tablaDetalles.setReadOnly(true);
        tablaDetalles.removeColumn("infoAdicional3");
        l4.addComponent(tablaDetalles);
        l4.setComponentAlignment(tablaDetalles, Alignment.MIDDLE_CENTER);
        ///////////////////////////////////////////////////////7
        HorizontalLayout l5=new HorizontalLayout();
       
        l5.setSpacing(true);
        l5.addComponent(tipoFactura);
        Label lcountry=new Label("PAIS DETINO");
        l5.addComponent(lcountry);
        comboPaisDestino=construirComboPaises();
        l5.addComponent(comboPaisDestino);
        HorizontalLayout l6=new HorizontalLayout();
        l6.setSpacing(true);
        subtotal=new Label("Subtotal $0.00");
        iva12=new Label("IVA12%  $0.00");
        ice=new Label("ICE $0.00");
        total=new Label("TOTAL $0.00");
        l6.addComponent(subtotal);
        l6.addComponent(iva12);
        l6.addComponent(ice);
        l6.addComponent(total);
        l6.addComponent(botonRegistrar);
        l6.addComponent(botonCancelar);
        l5.addComponent(l6);
//        l5.addComponent(botonRegistrar);
//        l5.addComponent(botonCancelar);
        
      
        
//        l5.setSizeFull();
//        VerticalLayout v1=new VerticalLayout();
//        Label linfo=new Label("Info");
//        HorizontalLayout vl1=new HorizontalLayout();
//        vl1.setSpacing(true);
//        vl1.addComponent(linfo);
//       
//        v1.addComponent(vl1);
     
        
//        VerticalLayout v2=new VerticalLayout();
//        v2.setSizeFull();
//    
//        HorizontalLayout v2l1=new HorizontalLayout();
//        v2l1.addComponent(botonRegistrar);
//        v2l1.addComponent(botonCancelar);
//        v2.addComponent(v2l1);
//        v2.setComponentAlignment(v2l1, Alignment.TOP_RIGHT);
//        l5.addComponent(v1);
//        l5.addComponent(v2);
                
        //////////////////////////////////////////////
        setSpacing(true);
        addComponent(l1);
        addComponent(l2);
        addComponent(l3);
        addComponent(l4);
        addComponent(l5);
        setComponentAlignment(l1, Alignment.MIDDLE_CENTER);
        setComponentAlignment(l2, Alignment.MIDDLE_CENTER);
        setComponentAlignment(l3, Alignment.MIDDLE_CENTER);
        setComponentAlignment(l4, Alignment.MIDDLE_CENTER);
      //  setComponentAlignment(l5, Alignment.MIDDLE_CENTER);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
       if(SecurityUtils.getSubject().isAuthenticated()){
    	   if(!SecurityUtils.getSubject().hasRole("operador")){
    		   UI.getCurrent().getNavigator().navigateTo("login");
    	   }
       }else{
    	   UI.getCurrent().getNavigator().navigateTo("login");
       }
          
       
    }

    private void iniciarEventos() {
    	
    	tipoFactura.addValueChangeListener(event -> {
    		tipoFactura.setReadOnly(true);
    		String _eltipoFActura=(String) tipoFactura.getValue();
    		if(_eltipoFActura.equalsIgnoreCase("exportacion")){
    			comboPaisDestino.setReadOnly(false);
    			comboPaisDestino.setEnabled(true);
    		}else{
    			comboPaisDestino.setReadOnly(true);
    			comboPaisDestino.setEnabled(false);
    		}
    		
    	});
        botonCancelar.addClickListener(event -> UI.getCurrent().getNavigator().navigateTo("menu"));
        botonAnadirDetalle.addClickListener(event->{
        	FacturaDetalleBinding fd=new FacturaDetalleBinding();
        	fd.setCodigo(codigoP.getValue());
        	fd.setDescripcion(bienSeleccionado.getValue());
        	fd.setCantidad(new BigDecimal(cantidad.getValue()));
        	fd.setValorUnitario(new BigDecimal(valorUnitario.getValue()));
        	
        	if(lote.getValue().length()>0){
        		String texto=lote.getValue();
        		StringTokenizer st=new StringTokenizer(texto,";");
        		if(st.hasMoreTokens()){        			
        			fd.setInfoAdicional1(st.nextToken().toString());
        		}
        		if(st.hasMoreTokens()){
        			fd.setInfoAdicional2(st.nextToken());        			
        		}
        	}
        	fd.setDescuento(new BigDecimal(descuento.getValue()));
        	if(!tipoFactura.getValue().toString().equalsIgnoreCase("regular")){
        		fd.setCodigoIVA("7");
        		fd.setIva12(new BigDecimal("0.00"));
        	}else{
        		fd.setCodigoIVA("2");        	        		
        	}
        	fd.calcularIva();
        	fd.calcularValorTotal();
        	beanContainerDetalles.addBean(fd);
        	actualizarTablaTotales();
        	
        });
        botonEliminarDetalle.addClickListener(event ->{ 
        	beanContainerDetalles.removeItem(tablaDetalles.getSelectedRow());
        	actualizarTablaTotales();        
        });

        botonRegistrar.addClickListener(event->{
        	FacturaBinding factura=new FacturaBinding();
        	String codigoEstablecimiento=userInfo.getCodigoEstablecimiento();
        	if(codigoEstablecimiento==null){
        		codigoEstablecimiento="002";
        	}
        	factura.setCodigoEstablecimiento(codigoEstablecimiento);
        	String puntoEmision=userInfo.getPuntoEmision();
        	if(puntoEmision==null){
        		puntoEmision="002";
        	}
        	factura.setCodigoPuntoVenta(puntoEmision);
        	String rucEmisor=userInfo.getRucEmisor();
        	if(rucEmisor==null){
        		rucEmisor="1791739477001";
        	}
        	factura.setRucEmisor(rucEmisor);
        	String _elTipoFactura=(String) tipoFactura.getValue();
        	factura.setTipoFactura(_elTipoFactura);
        	if(_elTipoFactura.equalsIgnoreCase("exportacion")){
        		factura.setCodigoPaisDestino((String) comboPaisDestino.getValue());
        	}
        	factura.setIdentificacionBeneficiario(rucBeneficiario.getValue());
        	factura.setRazonSocialBeneficiario(razonSocialBeneficiario.getValue());
        	factura.setDireccionBeneficiario(direccion.getValue());
        	factura.setFechaEmision(fechaEmision.getValue());
        	factura.setFormaPago(formaPago.getValue());
        	factura.setOrdenCompra(ordenCompra.getValue());
        	if(codigoEstablecimientoDestino.getValue()!=null){
        		factura.setSucursalCliente(codigoEstablecimientoDestino.getValue());	
        	}
        	////////////////////////////
        	BigDecimal _subtotal=BigDecimal.ZERO;
        	BigDecimal _iva12=BigDecimal.ZERO;
        	BigDecimal _ice=BigDecimal.ZERO;
        	BigDecimal _total=BigDecimal.ZERO;
        	 for (FacturaDetalleBinding itemIds : beanContainerDetalles.getItemIds()){
        		 _iva12=_iva12.add(itemIds.getIva12());
        		 _ice=_ice.add(itemIds.getIce());
        		 _total=_total.add(itemIds.getValorTotal());
        		 factura.getDetalles().add(itemIds);        		
        	 } 
        	 String numeroAcceso=recepcionFactura.recibirFactura(factura);
        	 String numeroDoc=utilClaveAcceso.obtenerCodigoEstablecimiento(numeroAcceso)+"-"+utilClaveAcceso.obtenerCodigoPuntoEmision(numeroAcceso)+"-"+utilClaveAcceso.obtenerSecuanciaDocumento(numeroAcceso);
        	 Notification.show(numeroDoc, numeroAcceso,Type.HUMANIZED_MESSAGE);
        	 
        	 
        });
        botonBuscarDetalle.addClickListener(event->{
        	Table gridBusqueda=new Table();
        	BeanItemContainer<BienEconomico>beaitem=new BeanItemContainer<BienEconomico>(BienEconomico.class);
        	gridBusqueda.setContainerDataSource(beaitem);
        	gridBusqueda.setVisibleColumns(new String[]{"codigo","descripcion"});
        	gridBusqueda.setWidth("200px");
        	 tfiltro=new TextField();
        	botonSeleccionaDesdeVentanar = new Button("seleccionar");
        	
        	beaitem.addAll(listadorBienes.listarBienesDisponibles(userInfo.getRucEmisor()));
        	
        	HorizontalLayout lbus=new HorizontalLayout();
        	lbus.setSpacing(true);
        	lbus.addComponent(new Label("código"));
        	lbus.addComponent(tfiltro);
        	lbus.addComponent(botonSeleccionaDesdeVentanar);
        	
        	
        	
        	Window ventana=new Window();
        	VerticalLayout layoutventana=new VerticalLayout();
        	layoutventana.setSpacing(true);
        	layoutventana.setMargin(true);
        	layoutventana.addComponent(lbus);
        	layoutventana.addComponent(gridBusqueda);
        	ventana.setContent(layoutventana);
            ventana.center();
            UI.getCurrent().addWindow(ventana);
            
        	
        	
        });
    }
    
    
    public ComboBox construirComboPaises(){
    	ComboBox retorno=new ComboBox();
    	retorno.addItem("074");    	
    	retorno.setItemCaption("074", "BOUVET ISLAND");
    	
    	retorno.addItem("101");
    	retorno.setItemCaption("101", "ARGENTINA");
    	
    	retorno.addItem("102");
    	retorno.setItemCaption("102", "BOLIVIA");
    	retorno.addItem("103");
    	retorno.setItemCaption("103", "BRASIL");
    	retorno.addItem("104");
    	retorno.setItemCaption("104", "CANADA");
    	retorno.addItem("105");
    	retorno.setItemCaption("105", "COLOMBIA");
    	retorno.addItem("106");
    	retorno.setItemCaption("106", "COSTA RICA");
    	retorno.addItem("107");
    	retorno.setItemCaption("107", "CUBA");
    	retorno.addItem("108");
    	retorno.setItemCaption("108", "CHILE");
    	retorno.addItem("109");
    	retorno.setItemCaption("109", "ANGILA");
    	retorno.addItem("110");
    	retorno.setItemCaption("110", "ESTADOS UNIDOS");
    	return retorno;
    }
    
    @PostConstruct
    public void postconstructor(){
       System.out.println("Hola");
        construirGui();
        iniciarEventos();
    }
}
