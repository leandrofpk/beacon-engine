
package br.gov.inmetro.beacon.v2.mypackage;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * The type for a single pulse.
 * 
 * <p>Classe Java de pulseType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="pulseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uri" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="version">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;maxLength value="32"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="cipherSuite" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="period" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="certificateId" type="{http://csrc.nist.gov/ns/beacon/pulse/2.0}sha512HexString"/>
 *         &lt;element name="chainIndex" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="pulseIndex" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="timeStamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="localRandomValue" type="{http://csrc.nist.gov/ns/beacon/pulse/2.0}sha512HexString"/>
 *         &lt;element name="external" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="sourceId" type="{http://csrc.nist.gov/ns/beacon/pulse/2.0}sha512HexString"/>
 *                   &lt;element name="statusCode" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *                   &lt;element name="value" type="{http://csrc.nist.gov/ns/beacon/pulse/2.0}sha512HexString"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="listValue" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://csrc.nist.gov/ns/beacon/pulse/2.0>sha512HexString">
 *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                 &lt;attribute name="uri" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="precommitmentValue" type="{http://csrc.nist.gov/ns/beacon/pulse/2.0}sha512HexString" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="signatureValue">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;maxLength value="8192"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="outputValue" type="{http://csrc.nist.gov/ns/beacon/pulse/2.0}sha512HexString"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pulseType", namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0", propOrder = {
    "uri",
    "version",
    "cipherSuite",
    "period",
    "certificateId",
    "chainIndex",
    "pulseIndex",
    "timeStamp",
    "localRandomValue",
    "external",
    "listValue",
    "precommitmentValue",
    "statusCode",
    "signatureValue",
    "outputValue"
})
@XmlSeeAlso({
    br.gov.inmetro.beacon.v2.mypackage.ChainType.Pulse.class
})
public class PulseType {

    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String uri;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String version;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0")
    protected int cipherSuite;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0")
    protected int period;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String certificateId;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0")
    protected long chainIndex;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0")
    protected long pulseIndex;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timeStamp;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String localRandomValue;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0")
    protected PulseType.External external;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0", required = true)
    protected List<PulseType.ListValue> listValue;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String precommitmentValue;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0")
    protected int statusCode;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String signatureValue;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String outputValue;

    /**
     * Obtém o valor da propriedade uri.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * Define o valor da propriedade uri.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

    /**
     * Obtém o valor da propriedade version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Define o valor da propriedade version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Obtém o valor da propriedade cipherSuite.
     * 
     */
    public int getCipherSuite() {
        return cipherSuite;
    }

    /**
     * Define o valor da propriedade cipherSuite.
     * 
     */
    public void setCipherSuite(int value) {
        this.cipherSuite = value;
    }

    /**
     * Obtém o valor da propriedade period.
     * 
     */
    public int getPeriod() {
        return period;
    }

    /**
     * Define o valor da propriedade period.
     * 
     */
    public void setPeriod(int value) {
        this.period = value;
    }

    /**
     * Obtém o valor da propriedade certificateId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificateId() {
        return certificateId;
    }

    /**
     * Define o valor da propriedade certificateId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificateId(String value) {
        this.certificateId = value;
    }

    /**
     * Obtém o valor da propriedade chainIndex.
     * 
     */
    public long getChainIndex() {
        return chainIndex;
    }

    /**
     * Define o valor da propriedade chainIndex.
     * 
     */
    public void setChainIndex(long value) {
        this.chainIndex = value;
    }

    /**
     * Obtém o valor da propriedade pulseIndex.
     * 
     */
    public long getPulseIndex() {
        return pulseIndex;
    }

    /**
     * Define o valor da propriedade pulseIndex.
     * 
     */
    public void setPulseIndex(long value) {
        this.pulseIndex = value;
    }

    /**
     * Obtém o valor da propriedade timeStamp.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * Define o valor da propriedade timeStamp.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimeStamp(XMLGregorianCalendar value) {
        this.timeStamp = value;
    }

    /**
     * Obtém o valor da propriedade localRandomValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalRandomValue() {
        return localRandomValue;
    }

    /**
     * Define o valor da propriedade localRandomValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalRandomValue(String value) {
        this.localRandomValue = value;
    }

    /**
     * Obtém o valor da propriedade external.
     * 
     * @return
     *     possible object is
     *     {@link PulseType.External }
     *     
     */
    public PulseType.External getExternal() {
        return external;
    }

    /**
     * Define o valor da propriedade external.
     * 
     * @param value
     *     allowed object is
     *     {@link PulseType.External }
     *     
     */
    public void setExternal(PulseType.External value) {
        this.external = value;
    }

    /**
     * Gets the value of the listValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PulseType.ListValue }
     * 
     * 
     */
    public List<PulseType.ListValue> getListValue() {
        if (listValue == null) {
            listValue = new ArrayList<PulseType.ListValue>();
        }
        return this.listValue;
    }

    /**
     * Obtém o valor da propriedade precommitmentValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrecommitmentValue() {
        return precommitmentValue;
    }

    /**
     * Define o valor da propriedade precommitmentValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrecommitmentValue(String value) {
        this.precommitmentValue = value;
    }

    /**
     * Obtém o valor da propriedade statusCode.
     * 
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Define o valor da propriedade statusCode.
     * 
     */
    public void setStatusCode(int value) {
        this.statusCode = value;
    }

    /**
     * Obtém o valor da propriedade signatureValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignatureValue() {
        return signatureValue;
    }

    /**
     * Define o valor da propriedade signatureValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignatureValue(String value) {
        this.signatureValue = value;
    }

    /**
     * Obtém o valor da propriedade outputValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputValue() {
        return outputValue;
    }

    /**
     * Define o valor da propriedade outputValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputValue(String value) {
        this.outputValue = value;
    }


    /**
     * <p>Classe Java de anonymous complex type.
     * 
     * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="sourceId" type="{http://csrc.nist.gov/ns/beacon/pulse/2.0}sha512HexString"/>
     *         &lt;element name="statusCode" type="{http://www.w3.org/2001/XMLSchema}short"/>
     *         &lt;element name="value" type="{http://csrc.nist.gov/ns/beacon/pulse/2.0}sha512HexString"/>
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
        "sourceId",
        "statusCode",
        "value"
    })
    public static class External {

        @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String sourceId;
        @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0")
        protected short statusCode;
        @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String value;

        /**
         * Obtém o valor da propriedade sourceId.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSourceId() {
            return sourceId;
        }

        /**
         * Define o valor da propriedade sourceId.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSourceId(String value) {
            this.sourceId = value;
        }

        /**
         * Obtém o valor da propriedade statusCode.
         * 
         */
        public short getStatusCode() {
            return statusCode;
        }

        /**
         * Define o valor da propriedade statusCode.
         * 
         */
        public void setStatusCode(short value) {
            this.statusCode = value;
        }

        /**
         * Obtém o valor da propriedade value.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Define o valor da propriedade value.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

    }


    /**
     * <p>Classe Java de anonymous complex type.
     * 
     * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://csrc.nist.gov/ns/beacon/pulse/2.0>sha512HexString">
     *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
     *       &lt;attribute name="uri" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class ListValue {

        @XmlValue
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String value;
        @XmlAttribute(name = "type", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String type;
        @XmlAttribute(name = "uri")
        @XmlSchemaType(name = "anyURI")
        protected String uri;

        /**
         * Obtém o valor da propriedade value.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Define o valor da propriedade value.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Obtém o valor da propriedade type.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * Define o valor da propriedade type.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * Obtém o valor da propriedade uri.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUri() {
            return uri;
        }

        /**
         * Define o valor da propriedade uri.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUri(String value) {
            this.uri = value;
        }

    }

}
