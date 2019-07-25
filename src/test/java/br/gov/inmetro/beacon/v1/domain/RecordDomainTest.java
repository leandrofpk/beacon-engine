package br.gov.inmetro.beacon.v1.domain;

import br.gov.inmetro.beacon.v1.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.v1.domain.service.CriptoUtilService;
import br.gov.inmetro.beacon.v1.domain.service.RecordNew;
import br.gov.inmetro.beacon.v1.infra.DateUtil;
import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.PublicKey;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class RecordDomainTest {

    @Autowired
    private Environment environment;

    private PulseEntity lastPulseEntity;

//    @Before
//    public void initClass(){
//        lastPulseEntity = new PulseEntity();
//        lastPulseEntity.setVersionBeacon("1.0.0");
//        lastPulseEntity.setFrequency("60");
//        lastPulseEntity.setTimeStamp(1558201740000L); //2019-05-18 14:49:00
//        lastPulseEntity.setTimeStampWork(DateUtil.longToLocalDateTime(lastPulseEntity.getTimeStamp().toString()));
//
//        lastPulseEntity.setSeedValue("seed");
//        lastPulseEntity.setPreviousOutputValue("output");
//        lastPulseEntity.setSignatureValue("signature");
//        lastPulseEntity.setOutputValue("output");
//        lastPulseEntity.setStatusCode("0");
//    }
//
//    @Test
//    public void deveRetornarStatuscode0() throws Exception {
//        RecordSimpleDto recordSimpleDto = new RecordSimpleDto("1558201800000", "31d66e769bcf356ad3cc80bdd6ea592dc7bc5e983ea02f9c1ca3ee165a17f030f658414c4b20ca5c367431824c11e2227c15629fade280020148ce8a5ab9bd32", "1");
//
//        String propertyPrivateKey = environment.getProperty("beacon.x509.privatekey");
//
//        NewPulseDomainService record = new NewPulseDomainService(recordSimpleDto, lastPulseEntity, "1.0.0",
//                CriptoUtilService.loadPrivateKey(propertyPrivateKey), false);
//
//        RecordNew newRecord = record.iniciar();
//        Assert.assertEquals("0", newRecord.getStatusCode());
//    }
//
//    @Test
//    public void deveRetornarStatusCode2() throws Exception {
//
//        // Um minuto de gap - deve mudar o status code para 2
//        RecordSimpleDto recordSimpleDto = new RecordSimpleDto("1558201860000", "5f19be7d2cb0de7a94c8829c8a4d20f79ccca5a319475ff684e6c8683c1ce93d94d0231cecc3a7e127faf99df4b920eb133f58c28d2375481f3b9929af992006", "1");
//
//        String propertyPrivateKey = environment.getProperty("beacon.x509.privatekey");
//
//        NewPulseDomainService record = new NewPulseDomainService(recordSimpleDto, lastPulseEntity, "1.0.0",
//                CriptoUtilService.loadPrivateKey(propertyPrivateKey), false);
//
//        RecordNew newRecord = record.iniciar();
//        Assert.assertEquals("2", newRecord.getStatusCode());
//    }
//
//    @Test
//    public void shouldRetursNewChainWithStatusCode1() throws Exception {
//        // Start a new chain of values
//
//        RecordSimpleDto recordSimpleDto = new RecordSimpleDto("1558201860000", "5f19be7d2cb0de7a94c8829c8a4d20f79ccca5a319475ff684e6c8683c1ce93d94d0231cecc3a7e127faf99df4b920eb133f58c28d2375481f3b9929af992006","1");
//
//        String propertyPrivateKey = environment.getProperty("beacon.x509.privatekey");
//
//        NewPulseDomainService record = new NewPulseDomainService(recordSimpleDto, lastPulseEntity, "1.0.0",
//                CriptoUtilService.loadPrivateKey(propertyPrivateKey), true);
//
//        RecordNew newRecord = record.iniciar();
//        Assert.assertEquals("1", newRecord.getStatusCode());
//
//        Assert.assertEquals("00000000000000000000000000000000000000000000000000000" +
//                "000000000000000000000000000000000000000000000000000000000000000000000000000", newRecord.getPreviousOutput());
//
//    }
//
//    @Test
//    public void deveValidarAssinatura() throws Exception {
//        RecordSimpleDto recordSimpleDto = new RecordSimpleDto("1558201860000", "5f19be7d2cb0de7a94c8829c8a4d20f79ccca5a319475ff684e6c8683c1ce93d94d0231cecc3a7e127faf99df4b920eb133f58c28d2375481f3b9929af992006", "1");
//
//        String propertyPrivateKey = environment.getProperty("beacon.x509.privatekey");
//
//        NewPulseDomainService record = new NewPulseDomainService(recordSimpleDto, lastPulseEntity, "1.0.0",
//                CriptoUtilService.loadPrivateKey(propertyPrivateKey), false);
//
//        RecordNew newRecord = record.iniciar();
//
//
//        String propertyCertificate = environment.getProperty("beacon.x509.certificate");
//        PublicKey publicKey = CriptoUtilService.loadPublicKeyFromCertificate(propertyCertificate);
//
//        boolean isCorrect = CriptoUtilService.verifyBytes(newRecord.getRecordInBytes(), newRecord.getSignature(), publicKey);
//
//        assertEquals(true, isCorrect);
//
//    }
//
//    @Test
//    public void testar2(){
//        UUID uuid = UUID.randomUUID();
//        System.out.println(uuid.toString());
//    }

}
