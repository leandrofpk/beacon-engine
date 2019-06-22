package br.gov.inmetro.beacon.v1.domain.service;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import static br.gov.inmetro.beacon.v1.domain.service.CriptoUtilService.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CriptoUtilServiceTest {

    @Test
    public void testeHashSha512JavaNativo() throws NoSuchAlgorithmException {
        String originalString = "2BF94CA75B69061C4440C606270AF37F2993782ED52FF80485BE221318465577A10D27AB93BEA19188BEFF14703BD723E356D4D5E9EEC420F1A64412A7A7FE6D";
        String expected = "5C571D1B7641A359DE56A2498D4B972F4AFD6C85752381790E575E70B3BA7CBD7F5D6C646675F48C696884B0381FDC751C6153102EA14023F2719E23FE0C931C";

        CriptoUtilService cripto = new CriptoUtilService();
        final String actual = cripto.hashSha512Hexa(originalString);

        assertEquals(expected, actual);
    }

    @Test
    public void testSignAndVerifyFromPemFiles() throws Exception {
        String hashSha512Hexa = "5C571D1B7641A359DE56A2498D4B972F4AFD6C85752381790E575E70B3BA7CBD7F5D6C646675F48C696884B0381FDC751C6153102EA14023F2719E23FE0C931C";

        //sign
        PrivateKey privateKey = loadPrivateKey("privatekey-pkcs8.pem");
        String signature = sign(hashSha512Hexa, privateKey);
//        System.out.println(signature);

        PublicKey publicKey = loadPublicKey("publickey.pem");
        //Let's check the signature
        boolean isCorrect = CriptoUtilService.verify(hashSha512Hexa, signature, publicKey);

        assertTrue(isCorrect);
    }

    @Test
    public void cripto() throws Exception {
        String clearText = "Sample plain text";

        PublicKey publicKey = loadPublicKey("publickey.pem");

        String encrypted = encrypt(clearText, publicKey);

        PrivateKey privateKey = loadPrivateKey("privatekey-pkcs8.pem");

        String decrypted = decrypt(encrypted, privateKey);

        System.out.println("ClearText: " + clearText);
        System.out.println("Decrypted: " + decrypted);
        System.out.println("ClearText length: " + clearText.getBytes(StandardCharsets.UTF_8).length);
        System.out.println("Encrypted length: " + encrypted.length());
        System.out.println("Encrypted: " + encrypted);
    }

}