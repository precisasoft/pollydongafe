/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.vipsoft.ce.ui;

import java.io.Serializable;

/**
 *
 * @author chrisvv
 */
public class InfoAdicionalB implements Serializable{
    
	private static final long serialVersionUID = -8874189215469276210L;
	private String nombre;
    private String valor;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    
}
