package ec.com.vipsoft.ce.ui;

import java.util.Date;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ec.com.vipsoft.erp.gui.componentesbasicos.BotonAnadir;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoCodigo;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoDireccion;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoNumeroComprobante;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoNumeroIdentificacion;
import ec.com.vipsoft.erp.gui.componentesbasicos.CampoRazonSocial;

@CDIView("GROLD")
public class GuiaRemisionOldView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5903813651874204788L;
	private CampoNumeroIdentificacion identificacionTransportista;
	private CampoRazonSocial razonSocialTransportista;
	private TextField placa;
	private CampoDireccion puntoDePartida;
	private CheckBox checkUsarDireccionSucursal;
	private DateField fechaInicioTransporte;
	private DateField fechaFinTransporte;
	private CampoNumeroIdentificacion identificacionDestinatario;
	private CampoRazonSocial razonSocialDestinatario;
	private CampoDireccion direccionDestinatario;
	private TextField documentoAduanero;
	private ComboBox comboTipoDocumento;
	private CampoNumeroComprobante numeroDocumento;
	private CampoCodigo campoCodigoSucursalDestino;
	private TextField motivo;
	private TextField campoAutorizacion;
	private DateField fechaEmisionDocumentoSustento;
	private CampoCodigo codigoInterno;
	private TextField descripcionItem;
	private CampoCodigo cantidad;
	private TextField infoAdicionalDetalle;
	private BotonAnadir botonAndirDetalle;

	public GuiaRemisionOldView() {
		super();
		identificacionTransportista=new CampoNumeroIdentificacion();
		razonSocialTransportista=new CampoRazonSocial();
		placa=new TextField();
		placa.setWidth("80px");
		placa.setInputPrompt("GPR0499");
		placa.setDescription("placa del vehiculo que transporta");
		puntoDePartida=new CampoDireccion();
		checkUsarDireccionSucursal=new CheckBox("Salida desde sucursal");		
		checkUsarDireccionSucursal.setDescription("haga click para usar la misma dirección que la sucursal actual");		
		checkUsarDireccionSucursal.addValueChangeListener(event -> {
			if(checkUsarDireccionSucursal.getValue()){
				puntoDePartida.setValue("");
				puntoDePartida.setEnabled(false);
			}else{
				puntoDePartida.setEnabled(true);
			}
			
		});
		checkUsarDireccionSucursal.setValue(true);
		fechaInicioTransporte=new DateField();
		fechaInicioTransporte.setValue(new Date());
		fechaFinTransporte=new DateField();
		fechaFinTransporte.setValue(new Date());
		HorizontalLayout l1=new HorizontalLayout();
		l1.setSpacing(true);
		Label lidentificacion=new Label("RUC/ID");
		l1.addComponent(lidentificacion);
		l1.addComponent(identificacionTransportista);
		Label lrazonsocialt=new Label("Razón Social");
		l1.addComponent(lrazonsocialt);
		l1.addComponent(razonSocialTransportista);
		Label lplaca=new Label("Placa");
		l1.addComponent(lplaca);
		l1.addComponent(placa);
		l1.addComponent(checkUsarDireccionSucursal);
		l1.setComponentAlignment(checkUsarDireccionSucursal, Alignment.MIDDLE_CENTER);
		
		HorizontalLayout l2=new HorizontalLayout();
		Label ldireccion=new Label("Dirección salida");
		l2.setSpacing(true);
		l2.addComponent(ldireccion);
		l2.addComponent(puntoDePartida);
		Label lfsaldia=new Label("Fecha Salida");
		l2.addComponent(lfsaldia);
		l2.addComponent(fechaInicioTransporte);
		Label lfllegada=new Label("Fecha Llegada");
		l2.addComponent(lfllegada);
		l2.addComponent(fechaFinTransporte);
		/////////////////////////////////////////////////////////777
		VerticalLayout l3=new VerticalLayout();
		l3.setSpacing(true);
		//l3.setMargin(true);
		HorizontalLayout l4=new HorizontalLayout();
//		l4.setSpacing(true);
		identificacionDestinatario=new CampoNumeroIdentificacion();
		identificacionDestinatario.setDescription("ingrese la identificación del destinatario");
		razonSocialDestinatario=new CampoRazonSocial();
		razonSocialDestinatario.setWidth("140px");
		razonSocialDestinatario.setDescription("razón social del destinatario");		
		direccionDestinatario=new CampoDireccion();
		direccionDestinatario.setWidth("150px");
		Label lidentidestin=new Label("RUC/ID");
		l4.addComponent(lidentidestin);
		l4.addComponent(identificacionDestinatario);
		Label lrazosodes=new Label("Razón Social");
		l4.addComponent(lrazosodes);
		l4.addComponent(razonSocialDestinatario);
		
		Label ldireccionDestino=new Label("dirección");
		Label lsucursal=new Label("CodSur.");
		l4.addComponent(lsucursal);
		campoCodigoSucursalDestino=new CampoCodigo();
		campoCodigoSucursalDestino.setDescription("codigo de sucursal destino");
		campoCodigoSucursalDestino.setInputPrompt("001");
		campoCodigoSucursalDestino.setWidth("45px");
		l4.addComponent(campoCodigoSucursalDestino);		
		l4.addComponent(ldireccionDestino);
		l4.addComponent(direccionDestinatario);
		Label lmotivo=new Label("Motivo");
		motivo=new TextField();
		motivo.setWidth("80px");
		l4.addComponent(lmotivo);
		l4.addComponent(motivo);
		HorizontalLayout l5=new HorizontalLayout();
		l5.setSpacing(true);
		
		Label ldocuAdu=new Label("Doc. Aduanero");
		l4.addComponent(ldocuAdu);
		documentoAduanero=new TextField();
		documentoAduanero.setWidth("80px");
		l4.addComponent(documentoAduanero);
		comboTipoDocumento=new ComboBox();
		comboTipoDocumento.addItem("01");
		comboTipoDocumento.addItem("04");
		comboTipoDocumento.addItem("05");	
		comboTipoDocumento.addItem("07");
		comboTipoDocumento.setItemCaption("01", "FACTURA");
		comboTipoDocumento.setItemCaption("04", "NC");
		comboTipoDocumento.setItemCaption("05", "ND");
		comboTipoDocumento.setItemCaption("07", "CR");
		comboTipoDocumento.setValue("01");
		comboTipoDocumento.setNullSelectionAllowed(false);
		comboTipoDocumento.setNewItemsAllowed(false);
		comboTipoDocumento.setWidth("85px");
		Label ltipoDoc=new Label("Documento");
		l5.addComponent(ltipoDoc);
		l5.addComponent(comboTipoDocumento);
		Label numeroDoc=new Label("N°");
		l5.addComponent(numeroDoc);
		numeroDocumento=new CampoNumeroComprobante();
		l5.addComponent(numeroDocumento);
		
		
		l5.addComponent(new Label("Autorización"));
		campoAutorizacion=new TextField();
		campoAutorizacion.setDescription("N° de autorización del comprobante motivo de traslado");
		campoAutorizacion.setWidth("355px");
		campoAutorizacion.setInputPrompt("1803201512183217917394770010236516581");
		campoAutorizacion.setMaxLength("1803201512183217917394770010236516581".length());
		Label lfechaEmisionDocS=new Label("F. Emisión");
		fechaEmisionDocumentoSustento=new DateField();
		fechaEmisionDocumentoSustento.setValue(new Date());
		
		l5.addComponent(campoAutorizacion);
		l5.addComponent(lfechaEmisionDocS);
		l5.addComponent(fechaEmisionDocumentoSustento);
		
		HorizontalLayout l6=new HorizontalLayout();
		l6.setSpacing(true);
		Label lcodigoInterno=new Label("Código");
		l6.addComponent(lcodigoInterno);
		codigoInterno=new CampoCodigo();
		l6.addComponent(codigoInterno);
		Label ldescripcion=new Label("Descripcion");
		l6.addComponent(ldescripcion);
		descripcionItem=new TextField();
		descripcionItem.setWidth("200px");
		l6.addComponent(descripcionItem);
		Label lcantid=new Label("Cant.");
		l6.addComponent(lcantid);
		cantidad=new CampoCodigo();
		l6.addComponent(cantidad);
		Label linfoAdi=new Label("inf. Adicional");
		infoAdicionalDetalle=new TextField();
		l6.addComponent(linfoAdi);
		l6.addComponent(infoAdicionalDetalle);
		botonAndirDetalle=new BotonAnadir();
		l6.addComponent(botonAndirDetalle);
		
		
		
		
		
		
		l3.addComponent(l4);
		l3.addComponent(l5);
		l3.addComponent(l6);
		l3.setComponentAlignment(l4, Alignment.TOP_CENTER);
		l3.setComponentAlignment(l5, Alignment.TOP_CENTER);
		l3.setComponentAlignment(l6, Alignment.TOP_CENTER);
		
		setMargin(true);
		setSpacing(true);		
		addComponent(l1);
		addComponent(l2);
		addComponent(l3);
		setComponentAlignment(l1, Alignment.TOP_CENTER);
		setComponentAlignment(l2, Alignment.TOP_CENTER);
		setComponentAlignment(l3, Alignment.TOP_CENTER);
		
	}
	@Override
	public void enter(ViewChangeEvent event) {
		

	}
	

}
