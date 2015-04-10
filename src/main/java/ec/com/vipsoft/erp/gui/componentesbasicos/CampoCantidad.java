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
public class CampoCantidad extends TextField {

    public CampoCantidad() {
        super();
        setInputPrompt("1.00");
        setDescription("ingrese la cantidad");
        setWidth("70px");
        setValue("1.00");
        setMaxLength(10);
    }
    public void establecerLimpoXDefecto(){
        setValue("1.00");        
    }
    
    
}
