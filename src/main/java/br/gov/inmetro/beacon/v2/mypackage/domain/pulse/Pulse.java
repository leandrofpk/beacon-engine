package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import br.gov.inmetro.beacon.v2.mypackage.domain.chain.ChainValueObject;
import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;
import lombok.Getter;
import lombok.NonNull;

import java.security.PublicKey;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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

    public static Pulse BuilderFromEntity(PulseEntity entity) {
        return new Pulse(entity.getUri(), entity.getVersion(), entity.getCipherSuite(), entity.getPeriod(), entity.getCertificateId(),
                    entity.getChainIndex(), entity.getPulseIndex(), entity.getTimeStamp(), entity.getLocalRandomValue(),
                    External.newExternalFromEntity(entity.getExternalEntity()), convertListValuesToPulse(entity), entity.getPrecommitmentValue(),
                    entity.getStatusCode(), entity.getSignatureValue(), entity.getOutputValue());
    }

    private static List<ListValue> convertListValuesToPulse(PulseEntity pulseEntity){
        List<ListValue> listValues = new ArrayList<>();
        pulseEntity.getListValueEntities().forEach(entity -> listValues.add(ListValue.getOneValue(entity.getValue(), entity.getType(), entity.getUri())));
        return listValues;
    }

//    private Pulse(PulseEntity entity){
//        this.uri = entity.getUri();
//        this.version = entity.getVersion();
//        this.cipherSuite = entity.getCipherSuite();
//        this.period = entity.getPeriod();
//        this.certificateId = entity.getCertificateId();
//        this.chainIndex = entity.getChainIndex();
//        this.pulseIndex = entity.getPulseIndex();
//        this.timeStamp = entity.getTimeStamp();
//        this.localRandomValue = entity.getLocalRandomValue();
//        this.external = External.newExternalFromEntity(entity.getExternalEntity());
//        this.listValue = null;
//        this.precommitmentValue = entity.getPrecommitmentValue();
//        this.statusCode = entity.getStatusCode();
//        this.signatureValue = entity.getSignatureValue();
//        this.outputValue =  entity.getOutputValue();
//    }

    public static class Builder {

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

        public Builder setUri(String uri){
            this.uri = uri;
            return this;
        }

        public Builder setChainValueObject(ChainValueObject chainValueObject){
            this.chainValueObject = chainValueObject;
            return this;
        }

        public Builder setCertificateId(String certificateId){
            this.certificateId = certificateId;
            return this;
        }

        public Builder setPulseIndex(long pulseIndex){
            this.pulseIndex = pulseIndex;
            return this;
        }

        public Builder setTimeStamp(ZonedDateTime timeStamp){
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder setLocalRandomValue(String localRandomValue){
            this.localRandomValue = localRandomValue;
            return this;
        }

        public Builder setExternal(External external){
            this.external = external;
            return this;
        }

        public Builder setListValue(List<ListValue>listValue){
            this.listValue = listValue;
            return this;
        }

        public Builder setPrecommitmentValue(String precommitmentValue){
            this.precommitmentValue = precommitmentValue;
            return this;
        }

        public Builder setStatusCode (int statusCode){
            this.statusCode = statusCode;
            return this;
        }

        public Builder setSignatureValue (String signatureValue){
            this.signatureValue = signatureValue;
            return this;
        }

        public Builder setOutputValue (String outputValue){
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
