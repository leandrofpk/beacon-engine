package br.gov.inmetro.beacon.v1.domain.service;

import lombok.Setter;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Setter
public class RecordNew {

    private String version;
    private int frequency;
    private long timeStamp;
    private String seedValue;
    private String previousOutputValue;
    private String signatureValue;
    private String outputValue;
    private String statusCode;


    public String getVersion() {
        return this.version;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public byte[] getFrequencyAsBytes() {
        return ByteBuffer.allocate(4).putInt(getFrequency()).array();
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public byte[] getTimeStampAsBytes(){
        return ByteBuffer.allocate(8).putLong(getTimeStamp()).array();
    }

    public String getSeed() {
        return this.seedValue;
    }

    public byte[] getSeedAsBytes() {
        return ByteUtils.fromHexString(getSeed());
    }

    public String getPreviousOutput() {
        return this.previousOutputValue;
    }

    public byte[] getPreviousOutputAsBytes() {
        return ByteUtils.fromHexString(getPreviousOutput());
    }

    public String getSignature() {
        return this.signatureValue;
    }

    public byte[] getSignatureAsBytes() {
        return ByteUtils.fromHexString(getSignature());
    }

    public String getOutput() {
        return this.outputValue;
    }

    public byte[] getOutputAsBytes() {
        return ByteUtils.fromHexString(getOutput());
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public byte[] getStatusCodeAsBytes() {
        return ByteBuffer.allocate(4).putInt(Integer.parseInt(getStatusCode())).array();
    }

    public byte[] getRecordInBytes() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(2048); // should be enough
        baos.write(this.getVersion().getBytes(StandardCharsets.US_ASCII));
        baos.write(this.getFrequencyAsBytes());
        baos.write(this.getTimeStampAsBytes());
        baos.write(this.getSeedAsBytes());
        baos.write(this.getPreviousOutputAsBytes());
        baos.write(this.getStatusCodeAsBytes());

        return baos.toByteArray();
    }

}
