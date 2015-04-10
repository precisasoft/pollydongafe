/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.vipsoft.erp.gui.componentesbasicos;

import com.vaadin.ui.TextField;

/**
 *
 * @author chrisvv
 */
public class CampoPuntoEmision extends TextField {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7022912981867585734L;

	public CampoPuntoEmision() {
        super();
        setWidth("60px");
        setMaxLength(3);
        setInputPrompt("001");
        setDescription("ingrese el cpodigo de punto de emisión");
    }
    
    
}
