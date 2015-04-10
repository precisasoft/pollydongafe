package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.ui.TextField;

public class CampoDinero extends TextField {
	private static final long serialVersionUID = 6149183853903188298L;

	public CampoDinero() {
		super();
		setWidth("90px");
		setNullRepresentation("");
		setConverter(new ConversorBigDecimal());
	}


}
