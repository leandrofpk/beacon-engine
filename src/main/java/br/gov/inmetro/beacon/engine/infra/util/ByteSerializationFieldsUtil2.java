package br.gov.inmetro.beacon.engine.infra.util;

import br.gov.inmetro.beacon.engine.application.PulseDto;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static br.gov.inmetro.beacon.engine.infra.util.DateUtil.getTimeStampFormated;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ByteSerializationFieldsUtil2 {

    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    public ByteSerializationFieldsUtil2(PulseDto pulse) throws IOException {
        byteSerializeString(pulse.getUri());
        byteSerializeString(pulse.getVersion());
        encode4(pulse.getCipherSuite());
        encode4(pulse.getPeriod());
        byteSerializeHash(pulse.getCertificateId());
        encode8(pulse.getChainIndex());
        encode8(pulse.getPulseIndex());
        byteSerializeString(getTimeStampFormated(pulse.getTimeStamp()));
        byteSerializeHash(pulse.getLocalRandomValue());
        byteSerializeHash(pulse.getExternal().getSourceId());
        encode4(pulse.getExternal().getStatusCode());
        byteSerializeHash(pulse.getExternal().getValue());
        byteSerializeHash(pulse.getListValues().get(0).getValue());
        byteSerializeHash(pulse.getListValues().get(1).getValue());
        byteSerializeHash(pulse.getListValues().get(2).getValue());
        byteSerializeHash(pulse.getListValues().get(3).getValue());
        byteSerializeHash(pulse.getListValues().get(4).getValue());
        byteSerializeHash(pulse.getPrecommitmentValue());
        encode4(pulse.getStatusCode());
    }

    public ByteArrayOutputStream getBaos(){
        return baos;
    }

    private void encode4(int value) throws IOException {
        baos.write(ByteBuffer.allocate(4).putInt(value).array());
    }

    private void encode8(long value) throws IOException {
        baos.write(ByteBuffer.allocate(8).putLong(value).array());
    }

    private void byteSerializeHash(String hash) throws IOException {
        int bLenHash =  ByteUtils.fromHexString(hash).length;
        baos.write(ByteBuffer.allocate(4).putInt(bLenHash).array());
        baos.write(ByteUtils.fromHexString(hash));
    }

    private void byteSerializeString(String value) throws IOException {
        int bytLen = value.getBytes(UTF_8).length;
        baos.write(ByteBuffer.allocate(4).putInt(bytLen).array());
        baos.write(value.getBytes(UTF_8));
    }

}
