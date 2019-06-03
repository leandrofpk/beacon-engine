
package br.gov.inmetro.beacon.nist.validator.marshalling;

import br.gov.inmetro.beacon.nist.validator.Record;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A type for a single record.
 *
 * <p>Classe Java de recordType complex type.
 *
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 *
 * <pre>
 * &lt;complexType name="recordType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="frequency" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="timeStamp" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="seedValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="previousOutputValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signatureValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outputValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="statusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recordType", propOrder = {
        "version",
        "frequency",
        "timeStamp",
        "seedValue",
        "previousOutputValue",
        "signatureValue",
        "outputValue",
        "statusCode"
})
public class RecordType implements Record{

    @XmlElement(required = true)
    protected String version;
    protected int frequency;
    protected long timeStamp;
    @XmlElement(required = true)
    protected String seedValue;
    @XmlElement(required = true)
    protected String previousOutputValue;
    @XmlElement(required = true)
    protected String signatureValue;
    @XmlElement(required = true)
    protected String outputValue;
    @XmlElement(required = true)
    protected String statusCode;

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
     * Obtém o valor da propriedade frequency.
     *
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Define o valor da propriedade frequency.
     *
     */
    public void setFrequency(int value) {
        this.frequency = value;
    }

    /**
     * Obtém o valor da propriedade timeStamp.
     *
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * Define o valor da propriedade timeStamp.
     *
     */
    public void setTimeStamp(long value) {
        this.timeStamp = value;
    }

    /**
     * Obtém o valor da propriedade seedValue.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSeedValue() {
        return seedValue;
    }

    /**
     * Define o valor da propriedade seedValue.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSeedValue(String value) {
        this.seedValue = value;
    }

    /**
     * Obtém o valor da propriedade previousOutputValue.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPreviousOutputValue() {
        return previousOutputValue;
    }

    /**
     * Define o valor da propriedade previousOutputValue.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPreviousOutputValue(String value) {
        this.previousOutputValue = value;
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
     * Obtém o valor da propriedade statusCode.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Define o valor da propriedade statusCode.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setStatusCode(String value) {
        this.statusCode = value;
    }

}