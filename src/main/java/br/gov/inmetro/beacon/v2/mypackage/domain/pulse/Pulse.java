package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import br.gov.inmetro.beacon.v2.mypackage.domain.chain.ChainValueObject;
import lombok.Getter;
import lombok.NonNull;

import java.time.ZonedDateTime;
import java.util.List;

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

    private External external;

    private List<ListValue> listValue;

//    private PulseType.External external;
//
//    private List<PulseType.ListValue> listValue;
//
    private String precommitmentValue;

    private int statusCode;

    private String signatureValue;

    private String outputValue;

    private Pulse(@NonNull String uri, @NonNull String version, int cipherSuite, int period,
                  @NonNull String certificateId, long chainIndex, long pulseIndex,
                  @NonNull ZonedDateTime timeStamp, @NonNull String localRandomValue, @NonNull External external,
                  @NonNull List<ListValue> listValue, @NonNull String precommitmentValue,
                  int statusCode, @NonNull String signatureValue, @NonNull String outputValue) {
        this.uri =  uri +  "/beacon/" + version + "/chain/" + chainIndex + "/pulse/" + pulseIndex;
        this.version = version;
        this.cipherSuite = cipherSuite;
        this.period = period;
        this.certificateId = certificateId;
        this.chainIndex = chainIndex;
        this.pulseIndex = pulseIndex;
        this.timeStamp = timeStamp;
        this.localRandomValue = localRandomValue;
        this.external = external;
        this.listValue = listValue;
        this.precommitmentValue = precommitmentValue;
        this.statusCode = statusCode;
        this.signatureValue = signatureValue;
        this.outputValue = outputValue;
    }

    public static class BuilderRegular{

        private String uri;
        private ChainValueObject chainValueObject;
        private String certificateId;
        private long pulseIndex;
        private ZonedDateTime timeStamp;
        private String localRandomValue;
        private External external;
        private List<ListValue> listValue;
        private String precommitmentValue;
        private int statusCode;
        private String signatureValue;
        private String outputValue;

        public BuilderRegular setUri(String uri){
            this.uri = uri;
            return this;
        }

        public BuilderRegular setChainValueObject(ChainValueObject chainValueObject){
            this.chainValueObject = chainValueObject;
            return this;
        }

        public BuilderRegular setCertificateId(String certificateId){
            this.certificateId = certificateId;
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

        public BuilderRegular setExternal(External external){
            this.external = external;
            return this;
        }

        public BuilderRegular setListValue(List<ListValue>listValue){
            this.listValue = listValue;
            return this;
        }

        public BuilderRegular setPrecommitmentValue(String precommitmentValue){
            this.precommitmentValue = precommitmentValue;
            return this;
        }

        public BuilderRegular setStatusCode (int statusCode){
            this.statusCode = statusCode;
            return this;
        }

        public BuilderRegular setSignatureValue (String signatureValue){
            this.signatureValue = signatureValue;
            return this;
        }

        public BuilderRegular setOutputValue (String outputValue){
            this.outputValue = outputValue;
            return this;
        }

        public Pulse build(){
            return new Pulse(uri, chainValueObject.getVersion(), chainValueObject.getCipherSuite(), chainValueObject.getPeriod(), certificateId,
                    chainValueObject.getChainIndex(), pulseIndex, timeStamp, localRandomValue, external, listValue,
                    precommitmentValue, statusCode, signatureValue, outputValue);
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
                ", external=" + external +
                ", listValue=" + listValue +
                ", precommitmentValue='" + precommitmentValue + '\'' +
                ", statusCode=" + statusCode +
                ", signatureValue='" + signatureValue + '\'' +
                ", outputValue='" + outputValue + '\'' +
                '}';
    }
}
