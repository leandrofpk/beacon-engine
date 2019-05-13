package br.gov.inmetro.beacon.domain;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class RecordGeneratorTest {

    @Test
    public void testeSha512JavaNativo() throws NoSuchAlgorithmException {
        String originalString = "mensagemOriginal";

//        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] encodedhash = digest.digest(
                originalString.getBytes(StandardCharsets.UTF_8));

        String hex = bytesToHex(encodedhash);

        assertEquals("c882b1862fb8d198635609c0b9744098e82fd4ed9942b005e4e1ea12b7ddb6e6", hex);

    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }



}