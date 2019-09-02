
package br.gov.inmetro.beacon.engine;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de anonymous complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="error" type="{http://csrc.nist.gov/ns/beacon/pulse/2.0}errorType"/>
 *         &lt;element name="pulse" type="{http://csrc.nist.gov/ns/beacon/pulse/2.0}pulseType" maxOccurs="unbounded"/>
 *         &lt;element name="chain" type="{http://csrc.nist.gov/ns/beacon/pulse/2.0}chainType"/>
 *         &lt;element name="skipList" type="{http://csrc.nist.gov/ns/beacon/pulse/2.0}chainType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "error",
    "pulse",
    "chain",
    "skipList"
})
@XmlRootElement(name = "response", namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0")
public class Response {

    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0")
    protected ErrorType error;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0")
    protected List<PulseType> pulse;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0")
    protected ChainType chain;
    @XmlElement(namespace = "http://csrc.nist.gov/ns/beacon/pulse/2.0")
    protected ChainType skipList;

    /**
     * Obtém o valor da propriedade error.
     * 
     * @return
     *     possible object is
     *     {@link ErrorType }
     *     
     */
    public ErrorType getError() {
        return error;
    }

    /**
     * Define o valor da propriedade error.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorType }
     *     
     */
    public void setError(ErrorType value) {
        this.error = value;
    }

    /**
     * Gets the value of the pulse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pulse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPulse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PulseType }
     * 
     * 
     */
    public List<PulseType> getPulse() {
        if (pulse == null) {
            pulse = new ArrayList<PulseType>();
        }
        return this.pulse;
    }

    /**
     * Obtém o valor da propriedade chain.
     * 
     * @return
     *     possible object is
     *     {@link ChainType }
     *     
     */
    public ChainType getChain() {
        return chain;
    }

    /**
     * Define o valor da propriedade chain.
     * 
     * @param value
     *     allowed object is
     *     {@link ChainType }
     *     
     */
    public void setChain(ChainType value) {
        this.chain = value;
    }

    /**
     * Obtém o valor da propriedade skipList.
     * 
     * @return
     *     possible object is
     *     {@link ChainType }
     *     
     */
    public ChainType getSkipList() {
        return skipList;
    }

    /**
     * Define o valor da propriedade skipList.
     * 
     * @param value
     *     allowed object is
     *     {@link ChainType }
     *     
     */
    public void setSkipList(ChainType value) {
        this.skipList = value;
    }

}
