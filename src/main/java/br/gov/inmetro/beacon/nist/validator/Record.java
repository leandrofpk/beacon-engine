package br.gov.inmetro.beacon.nist.validator;

/**
 * An interface for NIST Randomness Beacon records: https://beacon.nist.gov/home
 * 
 * @author David Soroko
 *
 */
public interface Record {

    String getVersion();

    int getFrequency();

    long getTimeStamp();

    String getSeedValue();

    String getPreviousOutputValue();

    String getSignatureValue();

    String getOutputValue();

    String getStatusCode();

}
