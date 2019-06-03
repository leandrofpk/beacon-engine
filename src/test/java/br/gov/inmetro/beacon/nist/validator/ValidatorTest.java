package br.gov.inmetro.beacon.nist.validator;

import br.gov.inmetro.beacon.nist.validator.marshalling.RecordType;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.security.Security;

import static org.junit.Assert.assertTrue;

public class ValidatorTest {

    private static URL RECORD_URL = ValidatorTest.class.getResource("/1420021860.xml");
    private final static URL RECORD_NEXT_URL = ValidatorTest.class.getResource("/1420021920.xml");
    private final static URL BAD_SIG_RECORD_URL = ValidatorTest.class.getResource("/1420021860-bad-sig.xml");
    private final static URL CERT_URL = ValidatorTest.class.getResource("/beacon.cer");

    private static URL NIST_CERT_URL;
    private static URL NIST_RECORD_URL;

    private static UnpackedRecord RECORD;
    private static UnpackedRecord RECORD_NEXT;

    private Validator vlad;

    @BeforeClass
    public static void beforeClass() throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        NIST_CERT_URL = new URL("https://beacon.nist.gov/certificate/beacon.cer");
        NIST_RECORD_URL = new URL("https://beacon.nist.gov/rest/record/last");

        RECORD_URL = new URL("https://beacon.nist.gov/rest/record/1420021860");


        RecordType recordType = new RecordType();
        recordType.setVersion("Version 1.0");
        recordType.setFrequency(60);
        recordType.setTimeStamp(1420021860);
        recordType.setSeedValue("A006A414A48343D52532016F5B7A563F68EA72828F229C6634BD628364D4E2D4F73638A054CD9BC453146EB40FB1162BD0B4F525C808B0320DFA03D218C80FC0");
        recordType.setPreviousOutputValue("88B8EBD8998BE820E473D31ABCBACE80123EB83A5D371951AE8EF973E2DA925E0179D73E8A0DDF6C03789354C4BF8114F768EF4DE2BDEDFCAD0CB3F7C48E60B9");
        recordType.setSignatureValue("831FB3A546DF8741CCE8BC797646B92FD67586BB13795180300FCA2A6FD292906CFACF6A805E176269F5C978B089F16AEEDF02693CD1E758EDF81C2BC4726674875B98E07089EFD00ACFAD2D8E022B1BA4766BFD2D0E7E0AD5D535558A475306459FCB82E771F85BD478D76FBF05E387AED55E86CDA42E13149024C3BDC60775D6414B4DCC5BEE41AE52546522D20681DD66083F41541747A0A757F645FCB84B19C0D27E3C13AB9196D44FD8A54FA97F4098B9BA57B7FC76DAF4A2E6DF9B3EA3E6096BAFE0A58C009BE9B74E42EB917BBA90A9CDA11B9F5FE5D1949DDAA5DF71A84EA51BACD6026604A1E592CA70BFB125A33E8D9CE65FFAB346BC9050F5CF9D");
        recordType.setOutputValue("5AB754952CDFC7534492DABBC5C03BF78D65F5347149FC4CB47C42A6C8F3C568CA388335F3ADB9EF80730129D97D42CF9392F46FEE7D53B8CA639204C5AFCBDD");
        recordType.setStatusCode("0");

        RecordType recordTypeNext = new RecordType();
        recordTypeNext.setVersion("Version 1.0");
        recordTypeNext.setFrequency(60);
        recordTypeNext.setTimeStamp(1420021920);
        recordTypeNext.setSeedValue("3AD5FFB8A6149C3826F9B3C02D74BB815A74AF217651402B50935EA5AC833FA0F6686D8641E86FE202C14A8D8E52FF7CD897D44EFD8B3045AD3C5DE8EC021C82");
        recordTypeNext.setPreviousOutputValue("5AB754952CDFC7534492DABBC5C03BF78D65F5347149FC4CB47C42A6C8F3C568CA388335F3ADB9EF80730129D97D42CF9392F46FEE7D53B8CA639204C5AFCBDD");
        recordTypeNext.setSignatureValue("283DFB93D554ADB75FCDCAAAA226E669A0C9B37B0D985ADFE2DC5E3D8D099F6DC3611B4DF6311D1CAB0A4C09DC48724D202E88D46225B562771636D96442EF2BDCF700812E6E8345F802314C592F209B63C6CBF89E84EA1DC63D2BB576C06C076707C3D3F44A08E301E94146D53DE3233CD0AA843CB930E8D7C576D720902AF2F4478B6DFCEABBE566918C80B043505A41A247A73832141707EAE45887BE886503126D24A709852EAA96A10B0DAE7177EC1E57FA6CF8309E5501828CF7B02C1E6ED04448A3081DA2F7B64331C77B0C29B8348F44A401A14E47C3E610D1A105C7F22EA4551390485AADBB3F552C9A5E15BBB9C5362227797A455880BE09EBF74B");
        recordTypeNext.setOutputValue("AB1A6F75A80B5BA66033E6F8C7CB25473450CF0FEA5B5417C52BF29B9A31F50778451685A086C0AE4EF616E6140B0C73F385F628371DD90AC49159A4D31A1297");
        recordTypeNext.setStatusCode("0");

        RECORD = new UnpackedRecord(recordType);
        RECORD_NEXT = new UnpackedRecord(recordTypeNext);

    }

    @Before
    public void setUpValidator() throws Exception {
        vlad = new Validator();

        URL url = new URL("https://beacon.nist.gov/certificate/beacon.cer");
        vlad.setCertificate(Loader.loadCert(url));
    }

    @Test
    public void validSignature() throws Exception {
        assertTrue(vlad.isSignatureValid(RECORD));
    }

    @Test
    public void validOutputValue() throws Exception {
        assertTrue(vlad.isOutputValueValid(RECORD));
    }

//    @Test
//    public void validSignatureWithHTTPCalls() throws Exception {
//        vlad.setCertificate(Loader.loadCert(NIST_CERT_URL));
//
//        assertTrue(vlad.isSignatureValid(new UnpackedRecord(Loader.loadRecord(NIST_RECORD_URL))));
//    }

    @Test
    public void validChaining() throws Exception {
        assertTrue(vlad.isChainingValid(RECORD, RECORD_NEXT));
    }

//    @Test
//    public void invalidChaining() throws Exception {
//        assertFalse(vlad.isChainingValid(RECORD_NEXT, RECORD));
//    }

//    @Test
//    public void invalidSignature() throws Exception {
//        assertFalse(vlad.isSignatureValid(new UnpackedRecord(Loader.loadRecord(BAD_SIG_RECORD_URL))));
//    }

//    @Test
//    public void invalidOutputValue() throws Exception {
//        assertFalse(vlad.isOutputValueValid(new UnpackedRecord(Loader.loadRecord(BAD_SIG_RECORD_URL))));
//    }

}