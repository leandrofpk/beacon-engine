package br.gov.inmetro.beacon.v2.mypackage.domain.externalsrc;

/**
 * Implement this interface to retrieve data from external entropy source
 */
public interface IGetExternalSource {

    String getExternalSource() throws Exception;


}
