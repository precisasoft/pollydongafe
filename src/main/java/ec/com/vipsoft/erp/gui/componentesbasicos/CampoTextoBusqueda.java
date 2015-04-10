package ec.com.vipsoft.erp.gui.componentesbasicos;



import com.vaadin.ui.TextField;

public class CampoTextoBusqueda extends TextField {

	private static final long serialVersionUID = 1246735970493233439L;

	public CampoTextoBusqueda() {
		super();
		setInputPrompt("busqueda");
		setDescription("escriba su búsqueda aqui");
		setWidth("180px");
		setNullRepresentation("");
	}


}
