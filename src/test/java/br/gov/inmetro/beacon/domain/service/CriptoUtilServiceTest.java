package br.gov.inmetro.beacon.domain.service;

import org.junit.Test;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import static br.gov.inmetro.beacon.domain.service.CriptoUtilService.sign;
import static org.junit.Assert.assertEquals;

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
    public void teste() throws Exception {
        String originalString = "2BF94CA75B69061C4440C606270AF37F2993782ED52FF80485BE221318465577A10D27AB93BEA19188BEFF14703BD723E356D4D5E9EEC420F1A64412A7A7FE6D";
//        final String hashSha512Hexa = CriptoUtilService.hashSha512Hexa(originalString);

        System.out.println(originalString);

        KeyPair pair = CriptoUtilService.generateKeyPair();

        String signature = sign(originalString, pair.getPrivate());

        //Let's check the signature
        boolean isCorrect = CriptoUtilService.verify(originalString, signature, pair.getPublic());
        System.out.println("Signature correct: " + isCorrect);
    }

}