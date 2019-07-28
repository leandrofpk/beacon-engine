package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class Pulse {

    private String uri;

    private String version;

    private int cipherSuite;

    private int period;

    private String certificateId;

    private long chainIndex;

    private long pulseIndex;

    private ZonedDateTime timeStamp;

    private String localRandomValue;

//    private PulseType.External external;
//
//    private List<PulseType.ListValue> listValue;
//
    private String precommitmentValue;
//
//    private int statusCode;
//
//    private String signatureValue;
//
//    private String outputValue;

    private Pulse(String uri, String version, int cipherSuite, int period, String certificateId,
                  long chainIndex, long pulseIndex, ZonedDateTime timeStamp,
                  String localRandomValue, String precommitmentValue) {
        this.uri = uri;
        this.version = version;
        this.cipherSuite = cipherSuite;
        this.period = period;
        this.certificateId = certificateId;
        this.chainIndex = chainIndex;
        this.pulseIndex = pulseIndex;
        this.timeStamp = timeStamp;
        this.localRandomValue = localRandomValue;
        this.precommitmentValue = precommitmentValue;

    }

    public static class BuilderRegular{

        private String uri;
        private String version;
        private int cipherSuite;
        private int period;
        private String certificateId;
        private long chainIndex;
        private long pulseIndex;
        private ZonedDateTime timeStamp;
        private String localRandomValue;
        private String precommitmentValue;

        public BuilderRegular setUri(String uri){
            this.uri = uri;
            return this;
        }

        public BuilderRegular setVersion(String version){
            this.version = version;
            return this;
        }

        public BuilderRegular setCipherSuite(int cipherSuite){
            this.cipherSuite = cipherSuite;
            return this;
        }

        public BuilderRegular setPeriod(int period){
            this.period = period;
            return this;
        }

        public BuilderRegular setCertificateId(String certificateId){
            this.certificateId = certificateId;
            return this;
        }

        public BuilderRegular setChainIndex(long chainIndex){
            this.chainIndex = chainIndex;
            return this;
        }

        public BuilderRegular setPulseIndex(long pulseIndex){
            this.pulseIndex = pulseIndex;
            return this;
        }

        public BuilderRegular setTimeStamp(ZonedDateTime timeStamp){
            this.timeStamp = timeStamp;
            return this;
        }

        public BuilderRegular setLocalRandomValue(String localRandomValue){
            this.localRandomValue = localRandomValue;
            return this;
        }

        public BuilderRegular setPrecommitmentValue(String precommitmentValue){
            this.precommitmentValue = precommitmentValue;
            return this;
        }

        public Pulse build(){
            return new Pulse(uri, version, cipherSuite, period, certificateId,
                    chainIndex, pulseIndex, timeStamp, localRandomValue,
                    precommitmentValue);
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
                ", localRandomValue='" + localRandomValue + '\'' +
                ", precommitmentValue='" + precommitmentValue + '\'' +
                '}';
    }
}
