package br.gov.inmetro.beacon.engine.infra.util;

import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ByteSerializationFieldsUtil {

    public static byte[] encode4(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    public static byte[] encode8(long value) {
        return ByteBuffer.allocate(8).putLong(value).array();
    }

    public static byte[] byteSerializeHash(String hash) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int bLenHash =  ByteUtils.fromHexString(hash).length;
        baos.write(ByteBuffer.allocate(4).putInt(bLenHash).array());
        baos.write(ByteUtils.fromHexString(hash));

        return baos.toByteArray();
    }

    public static byte[] byteSerializeString(String value) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int bytLen = value.getBytes(UTF_8).length;
        baos.write(ByteBuffer.allocate(4).putInt(bytLen).array());
        baos.write(value.getBytes(UTF_8));

        return baos.toByteArray();
    }

    public static byte[] byteSerializeSig(String hexString) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bLenHash = ByteUtils.fromHexString(hexString).length;
        baos.write(ByteBuffer.allocate(4).putInt(bLenHash).array());
        baos.write(ByteUtils.fromHexString(hexString));

        return baos.toByteArray();
    }


}
