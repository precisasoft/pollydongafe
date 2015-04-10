//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2015.02.17 a las 07:13:10 PM ECT 
//


package ec.com.vipsoft.sri.factura._v1_1_0;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para reembolsos complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="reembolsos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reembolsoDetalle" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="tipoIdentificacionProveedorReembolso" type="{}tipoIdentificacionProveedorReembolso"/>
 *                   &lt;element name="identificacionProveedorReembolso" type="{}identificacionProveedorReembolso"/>
 *                   &lt;element name="codPaisPagoProveedorReembolso" type="{}codPaisPagoProveedorReembolso" minOccurs="0"/>
 *                   &lt;element name="tipoProveedorReembolso" type="{}tipoProveedorReembolso"/>
 *                   &lt;element name="codDocReembolso" type="{}codDocReembolso"/>
 *                   &lt;element name="estabDocReembolso" type="{}estabDocReembolso"/>
 *                   &lt;element name="ptoEmiDocReembolso" type="{}ptoEmiDocReembolso"/>
 *                   &lt;element name="secuencialDocReembolso" type="{}secuencialDocReembolso"/>
 *                   &lt;element name="fechaEmisionDocReembolso" type="{}fechaEmisionDocReembolso"/>
 *                   &lt;element name="numeroautorizacionDocReemb" type="{}numeroautorizacionDocReemb"/>
 *                   &lt;element name="detalleImpuestos" type="{}detalleImpuestos"/>
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
@XmlType(name = "reembolsos", namespace = "", propOrder = {
    "reembolsoDetalle"
})
public class Reembolsos {

    @XmlElement(required = true)
    protected List<Reembolsos.ReembolsoDetalle> reembolsoDetalle;

    /**
     * Gets the value of the reembolsoDetalle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reembolsoDetalle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReembolsoDetalle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Reembolsos.ReembolsoDetalle }
     * 
     * 
     */
    public List<Reembolsos.ReembolsoDetalle> getReembolsoDetalle() {
        if (reembolsoDetalle == null) {
            reembolsoDetalle = new ArrayList<Reembolsos.ReembolsoDetalle>();
        }
        return this.reembolsoDetalle;
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
     *         &lt;element name="tipoIdentificacionProveedorReembolso" type="{}tipoIdentificacionProveedorReembolso"/>
     *         &lt;element name="identificacionProveedorReembolso" type="{}identificacionProveedorReembolso"/>
     *         &lt;element name="codPaisPagoProveedorReembolso" type="{}codPaisPagoProveedorReembolso" minOccurs="0"/>
     *         &lt;element name="tipoProveedorReembolso" type="{}tipoProveedorReembolso"/>
     *         &lt;element name="codDocReembolso" type="{}codDocReembolso"/>
     *         &lt;element name="estabDocReembolso" type="{}estabDocReembolso"/>
     *         &lt;element name="ptoEmiDocReembolso" type="{}ptoEmiDocReembolso"/>
     *         &lt;element name="secuencialDocReembolso" type="{}secuencialDocReembolso"/>
     *         &lt;element name="fechaEmisionDocReembolso" type="{}fechaEmisionDocReembolso"/>
     *         &lt;element name="numeroautorizacionDocReemb" type="{}numeroautorizacionDocReemb"/>
     *         &lt;element name="detalleImpuestos" type="{}detalleImpuestos"/>
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
        "tipoIdentificacionProveedorReembolso",
        "identificacionProveedorReembolso",
        "codPaisPagoProveedorReembolso",
        "tipoProveedorReembolso",
        "codDocReembolso",
        "estabDocReembolso",
        "ptoEmiDocReembolso",
        "secuencialDocReembolso",
        "fechaEmisionDocReembolso",
        "numeroautorizacionDocReemb",
        "detalleImpuestos"
    })
    public static class ReembolsoDetalle {

        @XmlElement(required = true)
        protected String tipoIdentificacionProveedorReembolso;
        @XmlElement(required = true)
        protected String identificacionProveedorReembolso;
        protected String codPaisPagoProveedorReembolso;
        @XmlElement(required = true)
        protected String tipoProveedorReembolso;
        @XmlElement(required = true)
        protected String codDocReembolso;
        @XmlElement(required = true)
        protected String estabDocReembolso;
        @XmlElement(required = true)
        protected String ptoEmiDocReembolso;
        @XmlElement(required = true)
        protected String secuencialDocReembolso;
        @XmlElement(required = true)
        protected String fechaEmisionDocReembolso;
        @XmlElement(required = true)
        protected String numeroautorizacionDocReemb;
        @XmlElement(required = true)
        protected DetalleImpuestos detalleImpuestos;

        /**
         * Obtiene el valor de la propiedad tipoIdentificacionProveedorReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTipoIdentificacionProveedorReembolso() {
            return tipoIdentificacionProveedorReembolso;
        }

        /**
         * Define el valor de la propiedad tipoIdentificacionProveedorReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTipoIdentificacionProveedorReembolso(String value) {
            this.tipoIdentificacionProveedorReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad identificacionProveedorReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdentificacionProveedorReembolso() {
            return identificacionProveedorReembolso;
        }

        /**
         * Define el valor de la propiedad identificacionProveedorReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdentificacionProveedorReembolso(String value) {
            this.identificacionProveedorReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad codPaisPagoProveedorReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodPaisPagoProveedorReembolso() {
            return codPaisPagoProveedorReembolso;
        }

        /**
         * Define el valor de la propiedad codPaisPagoProveedorReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodPaisPagoProveedorReembolso(String value) {
            this.codPaisPagoProveedorReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad tipoProveedorReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTipoProveedorReembolso() {
            return tipoProveedorReembolso;
        }

        /**
         * Define el valor de la propiedad tipoProveedorReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTipoProveedorReembolso(String value) {
            this.tipoProveedorReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad codDocReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodDocReembolso() {
            return codDocReembolso;
        }

        /**
         * Define el valor de la propiedad codDocReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodDocReembolso(String value) {
            this.codDocReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad estabDocReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEstabDocReembolso() {
            return estabDocReembolso;
        }

        /**
         * Define el valor de la propiedad estabDocReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEstabDocReembolso(String value) {
            this.estabDocReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad ptoEmiDocReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPtoEmiDocReembolso() {
            return ptoEmiDocReembolso;
        }

        /**
         * Define el valor de la propiedad ptoEmiDocReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPtoEmiDocReembolso(String value) {
            this.ptoEmiDocReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad secuencialDocReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSecuencialDocReembolso() {
            return secuencialDocReembolso;
        }

        /**
         * Define el valor de la propiedad secuencialDocReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSecuencialDocReembolso(String value) {
            this.secuencialDocReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad fechaEmisionDocReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFechaEmisionDocReembolso() {
            return fechaEmisionDocReembolso;
        }

        /**
         * Define el valor de la propiedad fechaEmisionDocReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFechaEmisionDocReembolso(String value) {
            this.fechaEmisionDocReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad numeroautorizacionDocReemb.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNumeroautorizacionDocReemb() {
            return numeroautorizacionDocReemb;
        }

        /**
         * Define el valor de la propiedad numeroautorizacionDocReemb.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNumeroautorizacionDocReemb(String value) {
            this.numeroautorizacionDocReemb = value;
        }

        /**
         * Obtiene el valor de la propiedad detalleImpuestos.
         * 
         * @return
         *     possible object is
         *     {@link DetalleImpuestos }
         *     
         */
        public DetalleImpuestos getDetalleImpuestos() {
            return detalleImpuestos;
        }

        /**
         * Define el valor de la propiedad detalleImpuestos.
         * 
         * @param value
         *     allowed object is
         *     {@link DetalleImpuestos }
         *     
         */
        public void setDetalleImpuestos(DetalleImpuestos value) {
            this.detalleImpuestos = value;
        }

    }

}
