package br.gov.inmetro.beacon.domain;

import br.gov.inmetro.beacon.domain.service.CriptoUtilService;
import org.junit.Test;

import java.security.PublicKey;

import static br.gov.inmetro.beacon.domain.service.CriptoUtilService.loadPublicKey;

public class BeaconNistTest {

    @Test
    public void teste() throws Exception {



        PublicKey publicKey = loadPublicKey("beaconpubkey.pem");

//        boolean isCorrect = CriptoUtilService.verifyBytes(newRecord.getRecordDataBytes(), newRecord.getSignatureValue(), publicKey);




    }

}
