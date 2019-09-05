package br.gov.inmetro.beacon.engine.infra.util;

import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.nio.ByteBuffer;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ByteSerializationFieldsUtil {

    public static byte[] encode4(int value){
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    public static byte[] encode8(long value){
        return ByteBuffer.allocate(8).putLong(value).array();
    }

    public static byte[] byteSerializeHash(String hash){
        int bLenHash = 64;
        byte[] bytes1 = ByteBuffer.allocate(4).putInt(bLenHash).array();
        byte[] bytes2 = ByteUtils.fromHexString(hash);
        byte[] concatenate = ByteUtils.concatenate(bytes1, bytes2);

        return concatenate;
    }

    // conferir
    public static byte[] byteSerializeString(String value){
        int bytLen = value.getBytes(UTF_8).length;
        byte[] bytes1 = ByteBuffer.allocate(4).putInt(bytLen).array();
        byte[] bytes2 = value.getBytes(UTF_8);

        byte[] concatenate = ByteUtils.concatenate(bytes1, bytes2);

        return concatenate;
    }

    public static String getTimeStampFormated(ZonedDateTime timeStamp){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        String format = timeStamp.withZoneSameInstant((ZoneOffset.UTC).normalized()).format(dateTimeFormatter);
        return format;
    }

    public static byte[] byteSerializeSig(String hexString){
        int bLenHash = 512;
        byte[] bytes1 = ByteBuffer.allocate(4).putInt(bLenHash).array();
        byte[] bytes2 = ByteUtils.fromHexString(hexString);
        byte[] concatenate = ByteUtils.concatenate(bytes1, bytes2);

        return concatenate;
    }


}
