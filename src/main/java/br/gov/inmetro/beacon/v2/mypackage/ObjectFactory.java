
package br.gov.inmetro.beacon.v2.mypackage;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.gov.inmetro.beacon.v2.mypackage package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.gov.inmetro.beacon.v2.mypackage
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ChainType }
     * 
     */
    public ChainType createChainType() {
        return new ChainType();
    }

    /**
     * Create an instance of {@link PulseType }
     * 
     */
    public PulseType createPulseType() {
        return new PulseType();
    }

    /**
     * Create an instance of {@link ErrorType }
     * 
     */
    public ErrorType createErrorType() {
        return new ErrorType();
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link ChainType.Pulse }
     * 
     */
    public ChainType.Pulse createChainTypePulse() {
        return new ChainType.Pulse();
    }

    /**
     * Create an instance of {@link PulseType.External }
     * 
     */
    public PulseType.External createPulseTypeExternal() {
        return new PulseType.External();
    }

    /**
     * Create an instance of {@link PulseType.ListValue }
     * 
     */
    public PulseType.ListValue createPulseTypeListValue() {
        return new PulseType.ListValue();
    }

    /**
     * Create an instance of {@link ErrorType.Description }
     * 
     */
    public ErrorType.Description createErrorTypeDescription() {
        return new ErrorType.Description();
    }

}
