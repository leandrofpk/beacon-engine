package br.gov.inmetro.beacon.v2.mypackage.domain.externalsrc;

public class ExternalSourceDefinition {

    private String externalSourceDescription;

    /**
     * Set in hours
      */
    private int intendedUpdateFrequency;

    private String intendedUpdateMoment;

    private boolean repeatUntilNewAvailableValue;

    private boolean localHashing;

    private String defaultURLForAccess;

    private String recommendedSamplingTrials;

    private String fallBackOptions;


}
