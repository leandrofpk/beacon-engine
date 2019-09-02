package br.gov.inmetro.beacon.engine.domain.externalsrc;

public class ExternalSourceDefinition {

    private String sourceId;

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
