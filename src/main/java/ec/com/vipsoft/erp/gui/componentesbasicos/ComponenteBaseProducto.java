package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ComponenteBaseProducto extends VerticalLayout{

	private static final long serialVersionUID = 4272248237153908223L;
		protected Label labelCodigo;
		protected CampoCodigo campoCodigoProducto;
		protected Label labelDescripcion;
		protected TextField campoDescripcion;
		protected CheckBox pagaIva12;
		protected TextField presentacion;
		protected TextField detalleAdicional;
		protected BotonAnadir botonAnadirBien;
		protected Table tablaProductos;
		
		public ComponenteBaseProducto() {
			super();
			labelCodigo=new Label("Código");
			campoCodigoProducto=new CampoCodigo();
			labelDescripcion=new Label("Descripción");
			campoDescripcion=new TextField();
			pagaIva12=new CheckBox("Iva 12%");
			presentacion=new TextField();
			presentacion.setDescription("describa la presentación");
			presentacion.setWidth("120px");
			presentacion.setInputPrompt("caja");
			detalleAdicional=new TextField();
			detalleAdicional.setWidth("80px");
			detalleAdicional.setDescription("registre detalles adicionales");
			detalleAdicional.setInputPrompt("detalle adicional");
			tablaProductos=new Table();
		}
		
		
}
