/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.vipsoft.erp.abinadi.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 *
 * @author chrisvv
 */
@Entity
public class PuntoVenta implements Serializable {

    public PuntoVenta() {
    	secuenciaFacturacion=1L;
    	secuenciaGuiaRemision=1L;
    	secuenciaNotaCredito=1L;
    	secuenciaNotaDebito=1L;
    	secuenciaRetencion=1L;
    }
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    protected Long secuenciaFacturacion;
    protected Long secuenciaNotaDebito;
    protected Long secuenciaNotaCredito;
    protected Long secuenciaGuiaRemision;
    protected Long secuenciaRetencion;
    protected String codigoPuntoVenta;
    protected boolean defaultPuntoVentaElectronico;
    @ManyToOne
    protected Establecimiento establecimiento;
    
    
    
    public Establecimiento getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}

	//relaciona al usuario con el punto de venta-
    protected String usuarioPorDefecto;
    

   
   
    public String getUsuarioPorDefecto() {
		return usuarioPorDefecto;
	}

	public void setUsuarioPorDefecto(String usuarioPorDefecto) {
		this.usuarioPorDefecto = usuarioPorDefecto;
	}

	public Long getSecuenciaNotaDebito() {
		return secuenciaNotaDebito;
	}

	public void setSecuenciaNotaDebito(Long secuenciaNotaDebito) {
		this.secuenciaNotaDebito = secuenciaNotaDebito;
	}

	public Long getSecuenciaNotaCredito() {
		return secuenciaNotaCredito;
	}

	public void setSecuenciaNotaCredito(Long secuenciaNotaCredito) {
		this.secuenciaNotaCredito = secuenciaNotaCredito;
	}

	public Long getSecuenciaGuiaRemision() {
		return secuenciaGuiaRemision;
	}

	public void setSecuenciaGuiaRemision(Long secuenciaGuiaRemision) {
		this.secuenciaGuiaRemision = secuenciaGuiaRemision;
	}

	public Long getSecuenciaRetencion() {
		return secuenciaRetencion;
	}

	public void setSecuenciaRetencion(Long secuenciaRetencion) {
		this.secuenciaRetencion = secuenciaRetencion;
	}

	public Long getSecuenciaFacturacion() {
        return secuenciaFacturacion;
    }

    public void setSecuenciaFacturacion(Long secuenciaFacturacion) {
        this.secuenciaFacturacion = secuenciaFacturacion;
    }

    public String getCodigoPuntoVenta() {
        return codigoPuntoVenta;
    }

    public void setCodigoPuntoVenta(String codigoPuntoVenta) {
        this.codigoPuntoVenta = codigoPuntoVenta;
    }


    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PuntoVenta)) {
            return false;
        }
        PuntoVenta other = (PuntoVenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.vipsoft.erp.abinadi.dominio.PuntoVenta[ id=" + id + " ]";
    }
    public long siguienteSecuenciaFactura() {
    	if(secuenciaFacturacion==null){
    		secuenciaFacturacion=0L;
    	}
		return secuenciaFacturacion++;
	}
	public long siguienteSecuenciaNotaDebito(){
		if(secuenciaNotaDebito==null){
			secuenciaNotaDebito=0L;
		}
		return secuenciaNotaDebito++;
	}
	public long siguienteSecuenciaNotaCredito(){
		if(secuenciaNotaCredito==null){
			secuenciaNotaCredito=0L;
		}
		return secuenciaNotaCredito++;
	}
	public long siguienteSecuenciaGuiaRemision(){
		if(secuenciaGuiaRemision==null){
			secuenciaGuiaRemision=0L;
		}
		return secuenciaGuiaRemision++;
	}
	public long siguienteSecuenciaRetencion(){
		if(secuenciaRetencion==null){
			secuenciaRetencion=0L;
		}
		return secuenciaRetencion++;
	}

	public boolean isDefaultPuntoVentaElectronico() {
		return defaultPuntoVentaElectronico;
	}

	public void setDefaultPuntoVentaElectronico(boolean defaultPuntoVentaElectronico) {
		this.defaultPuntoVentaElectronico = defaultPuntoVentaElectronico;
	}
	
}
