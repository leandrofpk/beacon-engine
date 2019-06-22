package br.gov.inmetro.beacon.v1.domain;

import org.junit.Test;

import java.security.PublicKey;

import static br.gov.inmetro.beacon.v1.domain.service.CriptoUtilService.loadPublicKey;

public class BeaconNistTest {

    @Test
    public void teste() throws Exception {



        PublicKey publicKey = loadPublicKey("beaconpubkey.pem");

//        boolean isCorrect = CriptoUtilService.verifyBytes(newRecord.getRecordDataBytes(), newRecord.getSignatureValue(), publicKey);




    }

}
