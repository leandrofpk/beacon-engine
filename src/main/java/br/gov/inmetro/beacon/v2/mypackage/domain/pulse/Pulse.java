package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import br.gov.inmetro.beacon.v2.mypackage.domain.chain.ChainValueObject;
import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;
import br.gov.inmetro.beacon.v2.mypackage.infra.util.suite0.CipherSuiteZero;
import lombok.Getter;
import lombok.NonNull;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

        private final CipherSuiteZero sha512Util = new CipherSuiteZero();


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

            return new Pulse(uri, chainValueObject.getVersion(), chainValueObject.getCipherSuite(), chainValueObject.getPeriod(), certificateId,
                    chainValueObject.getChainIndex(), pulseIndex, timeStamp, localRandomValue, external, listValue,
                    precommitmentValue, statusCode, signatureValue, outputValue);
        }

        private void calcSignAndOutputValue() {
            try {

                CipherSuiteZero sha512Util = new CipherSuiteZero();
                ByteArrayOutputStream byteArrayOutputStream = byteSerializeFields();

                String digest = sha512Util.getDigest(byteArrayOutputStream.toByteArray());

                this.signatureValue = sha512Util.signBytes15(digest, privateKey);

                //outputvalue
                byteArrayOutputStream.write(getSignatureValueAsByte());
                this.outputValue = sha512Util.getDigest(byteArrayOutputStream.toByteArray());

            } catch (Exception e){
                e.printStackTrace();
            }

        }

        private ByteArrayOutputStream byteSerializeFields()  {

            final ByteArrayOutputStream baos = new ByteArrayOutputStream(4096); // should be enough
            try {
                baos.write(getUriAsByte());
                baos.write(getVersionAsByte());
                baos.write(getCipherSuiteAsByte());
                baos.write(getPeriodAsByte());
                baos.write(getCertifiedIdAsByte());
                baos.write(getChainIndexAsByte());
                baos.write(getPulseIndexAsByte());
                baos.write(getTimeStampAsByte());
                baos.write(getLocalRandomValueAsByte());
                baos.write(external.getSourceIdAsByte());
                baos.write(external.getStatusCodeAsByte());
                baos.write(external.getValueAsByte());
                baos.write(getPreviousRandOutAsByte());
                baos.write(getHourRandOutAsByte());
                baos.write(getDayRandOutAsByte());
                baos.write(getMOnthRandOutAsByte());
                baos.write(getYearRandOutAsByte());
                baos.write(getPrecommitmentValueAsByte());
                baos.write(getStatusCodeAsByte());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return baos;
        }

        // TODO Conferir tamanho
        public byte[] getUriAsByte(){
            return uri.getBytes(StandardCharsets.UTF_8);
        }

        public byte[] getVersionAsByte(){
            return chainValueObject.getVersion().getBytes(StandardCharsets.UTF_8);
        }

        public byte[] getCipherSuiteAsByte(){
            return ByteBuffer.allocate(4).putInt(chainValueObject.getCipherSuite()).array();
        }

        public byte[] getPeriodAsByte(){
            return ByteBuffer.allocate(4).putInt(chainValueObject.getPeriod()).array();
        }

        // TODO Conferir
        public byte[] getCertifiedIdAsByte() {
            return ByteUtils.fromHexString(certificateId);
        }

        public byte[] getChainIndexAsByte(){
            return ByteBuffer.allocate(8).putLong(chainValueObject.getChainIndex()).array();
        }

        public byte[] getPulseIndexAsByte(){
            return ByteBuffer.allocate(8).putLong(pulseIndex).array();
        }

        public byte[] getTimeStampAsByte(){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
            String format = timeStamp.withZoneSameInstant((ZoneOffset.UTC).normalized()).format(dateTimeFormatter);
            return format.getBytes(StandardCharsets.UTF_8);
        }

        public byte[] getLocalRandomValueAsByte(){
            return ByteUtils.fromHexString(localRandomValue);
        }

        public byte[] getPreviousRandOutAsByte(){
            return ByteUtils.fromHexString(listValue.get(0).getValue());
        }

        public byte[] getHourRandOutAsByte(){
            return ByteUtils.fromHexString(listValue.get(1).getValue());
        }

        public byte[] getDayRandOutAsByte(){
            return ByteUtils.fromHexString(listValue.get(2).getValue());
        }

        public byte[] getMOnthRandOutAsByte(){
            return ByteUtils.fromHexString(listValue.get(3).getValue());
        }

        public byte[] getYearRandOutAsByte(){
            return ByteUtils.fromHexString(listValue.get(4).getValue());
        }

        public byte[] getPrecommitmentValueAsByte(){
            return ByteUtils.fromHexString(this.precommitmentValue);
        }

        public byte[] getStatusCodeAsByte(){
            return ByteBuffer.allocate(4).putInt(this.statusCode).array();
        }

        public byte[] getSignatureValueAsByte(){
            return ByteUtils.fromHexString(this.signatureValue);
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
