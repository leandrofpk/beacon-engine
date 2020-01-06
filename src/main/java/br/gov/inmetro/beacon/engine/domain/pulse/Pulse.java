package br.gov.inmetro.beacon.engine.domain.pulse;

import br.gov.inmetro.beacon.engine.domain.chain.ChainValueObject;
import br.gov.inmetro.beacon.engine.infra.PulseEntity;
import br.gov.inmetro.beacon.library.ciphersuite.suite0.CipherSuiteBuilder;
import br.gov.inmetro.beacon.library.ciphersuite.suite0.ICipherSuite;
import lombok.Getter;
import lombok.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.gov.inmetro.beacon.engine.infra.util.DateUtil.getTimeStampFormated;
import static br.gov.inmetro.beacon.library.serialization.ByteSerializationFieldsUtil.*;

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
                  int statusCode, String signatureValue, String outputValue) {

        this.uri =  uri;
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
        private PrivateKey privateKey;

        private final ICipherSuite sha512Util = CipherSuiteBuilder.build(0);

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
            this.localRandomValue = sha512Util.getDigest(localRandomValue);
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
            this.precommitmentValue = sha512Util.getDigest(precommitmentValue);
            return this;
        }

        public Builder setStatusCode (int statusCode){
            this.statusCode = statusCode;
            return this;
        }

        public Builder setPrivateKey (PrivateKey privateKey){
            this.privateKey = privateKey;
            return this;
        }

        public Pulse build() {

            calcSignAndOutputValue();

            return new Pulse(uri, chainValueObject.getVersionPulse(), chainValueObject.getCipherSuite(), chainValueObject.getPeriod(), certificateId,
                    chainValueObject.getChainIndex(), pulseIndex, timeStamp, localRandomValue, external, listValue,
                    precommitmentValue, statusCode, signatureValue, outputValue);
        }

        private void calcSignAndOutputValue() {
            try {
                ByteArrayOutputStream byteArrayOutputStream =  byteSerializeSignatureInput();
                this.signatureValue = sha512Util.signPkcs15(privateKey, byteArrayOutputStream.toByteArray());

                //outputvalue
                byteArrayOutputStream.write(byteSerializeSig(this.signatureValue));
                this.outputValue = sha512Util.getDigest(byteArrayOutputStream.toByteArray());

            } catch (Exception e){
                e.printStackTrace();
            }

        }

        private ByteArrayOutputStream byteSerializeSignatureInput()  {

            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                // arrumar um lugar pra isso depois
                baos.write(byteSerializeString(uri));
                baos.write(byteSerializeString(chainValueObject.getVersionPulse()));
                baos.write(encode4(chainValueObject.getCipherSuite()));
                baos.write(encode4(chainValueObject.getPeriod()));
                baos.write(byteSerializeHash(certificateId));
                baos.write(encode8(chainValueObject.getChainIndex()));
                baos.write(encode8(pulseIndex));
                baos.write(byteSerializeString(getTimeStampFormated(this.timeStamp)));
                baos.write(byteSerializeHash(localRandomValue));
                baos.write(byteSerializeHash(external.getSourceId()));
                baos.write(encode4(external.getStatusCode()));
                baos.write(byteSerializeHash(external.getValue()));
                baos.write(byteSerializeHash(listValue.get(0).getValue()));
                baos.write(byteSerializeHash(listValue.get(1).getValue()));
                baos.write(byteSerializeHash(listValue.get(2).getValue()));
                baos.write(byteSerializeHash(listValue.get(3).getValue()));
                baos.write(byteSerializeHash(listValue.get(4).getValue()));
                baos.write(byteSerializeHash(precommitmentValue));
                baos.write(encode4(statusCode));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return baos;
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
