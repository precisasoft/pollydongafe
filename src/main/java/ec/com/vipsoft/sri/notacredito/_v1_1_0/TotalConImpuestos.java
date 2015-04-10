//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2015.02.17 a las 07:13:44 PM ECT 
//


package ec.com.vipsoft.sri.notacredito._v1_1_0;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="totalImpuesto" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="codigo" type="{}codigo"/>
 *                   &lt;element name="codigoPorcentaje" type="{}codigoPorcentaje"/>
 *                   &lt;element name="baseImponible" type="{}baseImponible"/>
 *                   &lt;element name="valor" type="{}valor"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "totalImpuesto"
})
@XmlRootElement(name = "totalConImpuestos", namespace = "")
public class TotalConImpuestos {

    @XmlElement(required = true)
    protected List<TotalConImpuestos.TotalImpuesto> totalImpuesto;

    /**
     * Gets the value of the totalImpuesto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the totalImpuesto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTotalImpuesto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TotalConImpuestos.TotalImpuesto }
     * 
     * 
     */
    public List<TotalConImpuestos.TotalImpuesto> getTotalImpuesto() {
        if (totalImpuesto == null) {
            totalImpuesto = new ArrayList<TotalConImpuestos.TotalImpuesto>();
        }
        return this.totalImpuesto;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="codigo" type="{}codigo"/>
     *         &lt;element name="codigoPorcentaje" type="{}codigoPorcentaje"/>
     *         &lt;element name="baseImponible" type="{}baseImponible"/>
     *         &lt;element name="valor" type="{}valor"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "codigo",
        "codigoPorcentaje",
        "baseImponible",
        "valor"
    })
    public static class TotalImpuesto {

        @XmlElement(required = true)
        protected String codigo;
        @XmlElement(required = true)
        protected String codigoPorcentaje;
        @XmlElement(required = true)
        protected BigDecimal baseImponible;
        @XmlElement(required = true)
        protected BigDecimal valor;

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
         * Obtiene el valor de la propiedad codigoPorcentaje.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodigoPorcentaje() {
            return codigoPorcentaje;
        }

        /**
         * Define el valor de la propiedad codigoPorcentaje.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodigoPorcentaje(String value) {
            this.codigoPorcentaje = value;
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
         * Obtiene el valor de la propiedad valor.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getValor() {
            return valor;
        }

        /**
         * Define el valor de la propiedad valor.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setValor(BigDecimal value) {
            this.valor = value;
        }

    }

}
