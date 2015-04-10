/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.vipsoft.ce.ui;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author chrisvv
 */
public class TotalesFactura implements Serializable{
    private String campo;
    private BigDecimal monto;

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
}
