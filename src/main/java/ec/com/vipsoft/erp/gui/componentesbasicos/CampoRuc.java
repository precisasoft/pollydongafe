package ec.com.vipsoft.erp.gui.componentesbasicos;


import com.vaadin.ui.TextField;

public class CampoRuc extends TextField{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5159687686131551026L;

	public CampoRuc() {
		super();
		setInputPrompt("9999999999999");
		setDescription("ingrese un ruc o cédula válido");
		setMaxLength(13);
		setWidth("140px");
		setNullRepresentation("");
//		addValidator(new javax.validation.Validator);
		
	}

}
