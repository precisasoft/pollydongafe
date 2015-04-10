//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2015.02.17 a las 07:12:34 PM ECT 
//


package ec.com.vipsoft.sri.guiaremision._v1_1_0;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Detalle de una guia de remision.  Contiene los elementos de cada fila de la guia.
 * 
 * <p>Clase Java para detalle complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="detalle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoInterno" type="{}codigoInterno" minOccurs="0"/>
 *         &lt;element name="codigoAdicional" type="{}codigoAdicional" minOccurs="0"/>
 *         &lt;element name="descripcion" type="{}descripcion"/>
 *         &lt;element name="cantidad" type="{}cantidad"/>
 *         &lt;element name="detallesAdicionales" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="detAdicional" maxOccurs="3" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="nombre">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;minLength value="1"/>
 *                                 &lt;maxLength value="300"/>
 *                                 &lt;pattern value="[^\n]*"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="valor">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;pattern value="[^\n]*"/>
 *                                 &lt;minLength value="1"/>
 *                                 &lt;maxLength value="300"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
@XmlType(name = "detalle", namespace = "", propOrder = {
    "codigoInterno",
    "codigoAdicional",
    "descripcion",
    "cantidad",
    "detallesAdicionales"
})
public class Detalle {

    protected String codigoInterno;
    protected String codigoAdicional;
    @XmlElement(required = true)
    protected String descripcion;
    @XmlElement(required = true)
    protected BigDecimal cantidad;
    protected Detalle.DetallesAdicionales detallesAdicionales;

    /**
     * Obtiene el valor de la propiedad codigoInterno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoInterno() {
        return codigoInterno;
    }

    /**
     * Define el valor de la propiedad codigoInterno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoInterno(String value) {
        this.codigoInterno = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoAdicional.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoAdicional() {
        return codigoAdicional;
    }

    /**
     * Define el valor de la propiedad codigoAdicional.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoAdicional(String value) {
        this.codigoAdicional = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Define el valor de la propiedad descripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Obtiene el valor de la propiedad cantidad.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * Define el valor de la propiedad cantidad.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCantidad(BigDecimal value) {
        this.cantidad = value;
    }

    /**
     * Obtiene el valor de la propiedad detallesAdicionales.
     * 
     * @return
     *     possible object is
     *     {@link Detalle.DetallesAdicionales }
     *     
     */
    public Detalle.DetallesAdicionales getDetallesAdicionales() {
    	if(detallesAdicionales==null){
    		detallesAdicionales=new DetallesAdicionales();
    	}
        return detallesAdicionales;
    }

    /**
     * Define el valor de la propiedad detallesAdicionales.
     * 
     * @param value
     *     allowed object is
     *     {@link Detalle.DetallesAdicionales }
     *     
     */
    public void setDetallesAdicionales(Detalle.DetallesAdicionales value) {
        this.detallesAdicionales = value;
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
     *         &lt;element name="detAdicional" maxOccurs="3" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="nombre">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;minLength value="1"/>
     *                       &lt;maxLength value="300"/>
     *                       &lt;pattern value="[^\n]*"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *                 &lt;attribute name="valor">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;pattern value="[^\n]*"/>
     *                       &lt;minLength value="1"/>
     *                       &lt;maxLength value="300"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
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
        "detAdicional"
    })
    public static class DetallesAdicionales {

        protected List<Detalle.DetallesAdicionales.DetAdicional> detAdicional;

        /**
         * Gets the value of the detAdicional property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the detAdicional property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDetAdicional().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Detalle.DetallesAdicionales.DetAdicional }
         * 
         * 
         */
        public List<Detalle.DetallesAdicionales.DetAdicional> getDetAdicional() {
            if (detAdicional == null) {
                detAdicional = new ArrayList<Detalle.DetallesAdicionales.DetAdicional>();
            }
            return this.detAdicional;
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
         *       &lt;attribute name="nombre">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;minLength value="1"/>
         *             &lt;maxLength value="300"/>
         *             &lt;pattern value="[^\n]*"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="valor">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;pattern value="[^\n]*"/>
         *             &lt;minLength value="1"/>
         *             &lt;maxLength value="300"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class DetAdicional {

            @XmlAttribute(name = "nombre")
            protected String nombre;
            @XmlAttribute(name = "valor")
            protected String valor;

            /**
             * Obtiene el valor de la propiedad nombre.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNombre() {
                return nombre;
            }

            /**
             * Define el valor de la propiedad nombre.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNombre(String value) {
                this.nombre = value;
            }

            /**
             * Obtiene el valor de la propiedad valor.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValor() {
                return valor;
            }

            /**
             * Define el valor de la propiedad valor.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValor(String value) {
                this.valor = value;
            }

        }

    }

}
