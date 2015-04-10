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
public class CampoEstablecimiento extends TextField {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4362791549708124869L;

	public CampoEstablecimiento() {
        super();
        setWidth("60px");
        setMaxLength(3);
        setInputPrompt("001");
        setDescription("ingrese el código de establecimiento");
    }
    
    
}
