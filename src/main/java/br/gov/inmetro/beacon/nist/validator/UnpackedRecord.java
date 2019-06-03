package br.gov.inmetro.beacon.nist.validator;

import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.nio.ByteBuffer;

/**
 * A Record implementation that adds various xxxAsBytes() methods to the Record interface
 * 
 * @author David Soroko
 *
 */
public class UnpackedRecord implements Record {

    private Record record;

    public UnpackedRecord(final Record record) {
        this.record = record;
    }

    @Override
    public String getVersion() {
        return record.getVersion();
    }

    @Override
    public int getFrequency() {
        return record.getFrequency();
    }

    public byte[] getFrequencyAsBytes() {
        return ByteBuffer.allocate(4).putInt(getFrequency()).array();
    }

    @Override
    public long getTimeStamp() {
        return record.getTimeStamp();
    }

    public byte[] getTimeStampAsBytes(){
        return ByteBuffer.allocate(8).putLong(getTimeStamp()).array();
    }

    @Override
    public String getSeedValue() {
        return record.getSeedValue();
    }

    public byte[] getSeedAsBytes() {
        return ByteUtils.fromHexString(getSeedValue());
    }

    @Override
    public String getPreviousOutputValue() {
        return record.getPreviousOutputValue();
    }

    public byte[] getPreviousOutputAsBytes() {
        return ByteUtils.fromHexString(getPreviousOutputValue());
    }

    @Override
    public String getSignatureValue() {
        return record.getSignatureValue();
    }

    public byte[] getSignatureAsBytes() {
        return ByteUtils.fromHexString(getSignatureValue());
    }

    @Override
    public String getOutputValue() {
        return record.getOutputValue();
    }

    public byte[] getOutputAsBytes() {
        return ByteUtils.fromHexString(getOutputValue());
    }

    @Override
    public String getStatusCode() {
        return record.getStatusCode();
    }

    public byte[] getStatusCodeAsBytes() {
        return ByteBuffer.allocate(4).putInt(Integer.parseInt(getStatusCode())).array();
    }



}
