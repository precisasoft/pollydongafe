package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.ui.ComboBox;

public class ComboTipoDocumento extends ComboBox {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -7715054076683142629L;
	public ComboTipoDocumento() {
		super();
		addItem("Factura");
		addItem("Nota de crédito");
		addItem("Nota de dédito");
		addItem("Guía de Remisión");
		addItem("Comprobante de Retención");
		setNullSelectionAllowed(false);
		select("Factura");
		
	}
	

}
