//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2015.02.17 a las 07:15:44 PM ECT 
//


package ec.com.vipsoft.sri.comprobanteRetencion._v1_0;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Impuesto de un comprobante de retencion.  Contiene los elementos de cada fila del comprobante.
 * 
 * <p>Clase Java para impuesto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="impuesto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigo" type="{}codigo"/>
 *         &lt;element name="codigoRetencion" type="{}codigoRetencion"/>
 *         &lt;element name="baseImponible" type="{}baseImponible"/>
 *         &lt;element name="porcentajeRetener" type="{}porcentajeRetener"/>
 *         &lt;element name="valorRetenido" type="{}valorRetenido"/>
 *         &lt;element name="codDocSustento" type="{}codDocSustento" minOccurs="0"/>
 *         &lt;element name="numDocSustento" type="{}numDocSustento" minOccurs="0"/>
 *         &lt;element name="fechaEmisionDocSustento" type="{}fechaEmisionDocSustento" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "impuesto", namespace = "", propOrder = {
    "codigo",
    "codigoRetencion",
    "baseImponible",
    "porcentajeRetener",
    "valorRetenido",
    "codDocSustento",
    "numDocSustento",
    "fechaEmisionDocSustento"
})
public class Impuesto {

    @XmlElement(required = true)
    protected String codigo;
    @XmlElement(required = true)
    protected String codigoRetencion;
    @XmlElement(required = true)
    protected BigDecimal baseImponible;
    @XmlElement(required = true)
    protected BigDecimal porcentajeRetener;
    @XmlElement(required = true)
    protected BigDecimal valorRetenido;
    protected String codDocSustento;
    protected String numDocSustento;
    protected String fechaEmisionDocSustento;

    /**
     * Obtiene el valor de la propiedad codigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define el valor de la propiedad codigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoRetencion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoRetencion() {
        return codigoRetencion;
    }

    /**
     * Define el valor de la propiedad codigoRetencion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoRetencion(String value) {
        this.codigoRetencion = value;
    }

    /**
     * Obtiene el valor de la propiedad baseImponible.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    /**
     * Define el valor de la propiedad baseImponible.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBaseImponible(BigDecimal value) {
        this.baseImponible = value;
    }

    /**
     * Obtiene el valor de la propiedad porcentajeRetener.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPorcentajeRetener() {
        return porcentajeRetener;
    }

    /**
     * Define el valor de la propiedad porcentajeRetener.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPorcentajeRetener(BigDecimal value) {
        this.porcentajeRetener = value;
    }

    /**
     * Obtiene el valor de la propiedad valorRetenido.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorRetenido() {
        return valorRetenido;
    }

    /**
     * Define el valor de la propiedad valorRetenido.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorRetenido(BigDecimal value) {
        this.valorRetenido = value;
    }

    /**
     * Obtiene el valor de la propiedad codDocSustento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodDocSustento() {
        return codDocSustento;
    }

    /**
     * Define el valor de la propiedad codDocSustento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodDocSustento(String value) {
        this.codDocSustento = value;
    }

    /**
     * Obtiene el valor de la propiedad numDocSustento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumDocSustento() {
        return numDocSustento;
    }

    /**
     * Define el valor de la propiedad numDocSustento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumDocSustento(String value) {
        this.numDocSustento = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaEmisionDocSustento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaEmisionDocSustento() {
        return fechaEmisionDocSustento;
    }

    /**
     * Define el valor de la propiedad fechaEmisionDocSustento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaEmisionDocSustento(String value) {
        this.fechaEmisionDocSustento = value;
    }

}
