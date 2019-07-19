package br.gov.inmetro.beacon.v2.mypackage.domain;

import java.time.LocalDateTime;

public class Pulse {

    private String uri;

    private String version;

    private int cipherSuite;

    private int period;

    private String certificateId;

    private long chainIndex;

    private long pulseIndex;

    private LocalDateTime timeStamp;

    private String localRandomValue;

//    private PulseType.External external;
//
//    private List<PulseType.ListValue> listValue;
//
//    private String precommitmentValue;
//
//    private int statusCode;
//
//    private String signatureValue;
//
//    private String outputValue;

    private Pulse(String uri, String version, int cipherSuite, int period, String certificateId,
                  long chainIndex, long pulseIndex, LocalDateTime timeStamp, String localRandomValue) {
        this.uri = uri;
        this.version = version;
        this.cipherSuite = cipherSuite;
        this.period = period;
        this.certificateId = certificateId;
        this.chainIndex = chainIndex;
        this.pulseIndex = pulseIndex;
        this.timeStamp = timeStamp;
        this.localRandomValue = localRandomValue;

    }

    public static class Builder{

        private String uri;
        private String version;
        private int cipherSuite;
        private int period;
        private String certificateId;
        private long chainIndex;
        private long pulseIndex;
        private LocalDateTime timeStamp;
        private String localRandomValue;

        public Builder setUri(String uri){
            this.uri = uri;
            return this;
        }

        public Builder setVersion(String version){
            this.version = version;
            return this;
        }

        public Builder setCipherSuite(int cipherSuite){
            this.cipherSuite = cipherSuite;
            return this;
        }

        public Builder setPeriod(int period){
            this.period = period;
            return this;
        }

        public Builder setCertificateId(String certificateId){
            this.certificateId = certificateId;
            return this;
        }

        public Builder setChainIndex(long chainIndex){
            this.chainIndex = chainIndex;
            return this;
        }

        public Builder setPulseIndex(long pulseIndex){
            this.pulseIndex = pulseIndex;
            return this;
        }

        public Builder setTimeStamp(LocalDateTime timeStamp){
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder setLocalRandomValue(String localRandomValue){
            this.localRandomValue = localRandomValue;
            return this;
        }

        public Pulse build(){
            return new Pulse(uri, version, cipherSuite, period, certificateId,
                    chainIndex, pulseIndex, timeStamp, localRandomValue);
        }

    }

    @Override
    public String toString() {
        return "Pulse{" +
                "uri='" + uri + '\'' +
                ", version='" + version + '\'' +
                ", cipherSuite=" + cipherSuite +
                ", period=" + period +
                ", certificateId='" + certificateId + '\'' +
                ", chainIndex=" + chainIndex +
                ", pulseIndex=" + pulseIndex +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
