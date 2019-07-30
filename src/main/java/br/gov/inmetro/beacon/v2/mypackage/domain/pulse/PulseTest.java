package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;


import br.gov.inmetro.beacon.v2.mypackage.domain.chain.ChainValueObject;
import org.apache.commons.lang3.time.DateUtils;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.Cipher;
import java.security.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class PulseTest {

    @Autowired
    Environment env;

    /**
     * Should build a pulse for the first chain
     */
    @Test
    public void shouldBuildTheFirstPulseTest(){

//        ChainValueObject

        ChainValueObject activeChain = new ChainValueObject("2.0", 0, 60000, 1);

        List<ListValue> listValue = new ArrayList<>();
        listValue.add(ListValue.getOneValue("000", "previous", "000"));

        Pulse newRegularPulse = new Pulse.BuilderRegular()
                .setUri(env.getProperty("beacon.url"))
                .setChainValueObject(activeChain)
                .setCertificateId("")
                .setPulseIndex(1)
                .setTimeStamp(ZonedDateTime.now().truncatedTo(ChronoUnit.MINUTES).withZoneSameInstant((ZoneOffset.UTC).normalized()))
                .setLocalRandomValue("111111111")
                .setExternal(External.newExternal())
                .setListValue(listValue)
                .setPrecommitmentValue("precommitment")
                .setStatusCode(0)
                .setSignatureValue("signature")
                .setOutputValue("outputValue")
                .build();

        System.out.println(newRegularPulse.toString());

//        Assert.assertEquals();

    }

    /**
     *
     * Should build the last pulse of the chain
     */
    @Test
    public void shouldBuildTheLastPulseOfTheChain(){

    }

    /**
     * Must Should a pulse for the start of a new chain
     */
    @Test
    public void shouldBuildTheFirstPuseNewChain(){

    }





//    private String uri;
//    private String version;
//    private int cipherSuite;
//    private int period;
//    private String certificateId;
//    private long chainIndex;
//    private long pulseIndex;
//    private LocalDateTime timeStamp;

//    public void tetarPKCS15(){
//        Security.addProvider(BouncyCastleProvider());
//        byte[] input = "Abc123".getBytes();
//        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
//        FileInputStream fin = new FileInputStream(new File("/test.cer"));
//        CertificateFactory f = CertificateFactory.getInstance("X.509");
//        X509Certificate certificate = (X509Certificate)f.generateCertificate(fin);
//        PublicKey pk = certificate.getPublicKey();
//        cipher.init(Cipher.ENCRYPT_MODE, pk, new SecureRandom());
//        byte[] cipherText = cipher.doFinal(input);
//    }

    @Test
    public void outroTesteDeData(){
//        ZonedDateTime now = ZonedDateTime.now().truncatedTo(ChronoUnit.MINUTES).withZoneSameInstant((ZoneOffset.UTC));
//        System.out.println(now);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");

        ZonedDateTime now = ZonedDateTime.now().truncatedTo(ChronoUnit.MINUTES).withZoneSameInstant((ZoneOffset.UTC).normalized());
        System.out.println(now);

        ZonedDateTime now2 = ZonedDateTime.now().truncatedTo(ChronoUnit.MINUTES).withZoneSameInstant((ZoneOffset.UTC).normalized());
        System.out.println(now2);

        String format2 = now2.format(dateTimeFormatter);
        System.out.println(format2);

//        2019-07-21T12:12:00.000Z
//        2019-07-20T14:15:00.000Z

//        System.out.println(now.withZoneSameInstant(ZoneId.of("UTC")));
    }

    @Test
    public void testarData(){
//        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'h:m:ss.SZ");
//        final String format = simpleDateFormat.format(LocalDateTime.now());
//        System.out.println(format);

        Instant instant = Instant.parse( "2011-05-03T11:58:01Z" );
        System.out.println(instant);

        // do nist
        Instant instant2 = Instant.parse( "2019-07-19T18:34:00.000Z" );
        System.out.println("milisegundos");
        System.out.println(instant2);

        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String text = date.format(formatter);
        System.out.println("Formatando sem o Z");
        System.out.println(text);

//        ---------------------------------------------------

        System.out.println("Instant");
        Instant now = Instant.now();
        System.out.println(now);


        System.out.println("Instant sertado pra zero");
        Instant instant1 = now.truncatedTo(ChronoUnit.MINUTES);

        System.out.println(instant1);


        Date nearestMinute = DateUtils.round(new Date(), Calendar.SECOND);
        System.out.println(nearestMinute);

        Instant from = Instant.from(nearestMinute.toInstant());

        System.out.println("from");
        System.out.println(from);

        LocalDateTime nowT =  LocalDateTime.now(Clock.systemUTC());

        LocalDateTime newTime = nowT.plusMinutes(1).minusNanos(1).withSecond(0).withNano(0000);

        System.out.println(newTime);





//        ------------------------------------------------------------

//        final Instant now = Instant.now().truncatedTo(ChronoUnit.MINUTES);
//        System.out.println("teste");
//        System.out.println(now.toString());
//        System.out.println(now.atZone(ZoneId.of("America/Sao_Paulo")).toString());
    }

    @Test
    public void testarData2(){
        LocalDateTime plus = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plus(1, ChronoUnit.MINUTES);
        Instant instant = plus.toInstant(ZoneOffset.UTC);
        System.out.println(instant);

        Date date = new Date();
        Instant instant1 = date.toInstant();
        System.out.println(instant1);


        Instant agora = Instant.now();
        System.out.println(agora);

        Instant with = agora.with(ChronoField.MILLI_OF_SECOND, 0);
        System.out.println(with.get(ChronoField.MILLI_OF_SECOND));

//        System.out.println(with);

        // 2018-07-23T19:26:00.000Z

//        Instant instantTruncated = Instant.now().truncatedTo( ChronoUnit.SECONDS );
//        System.out.println(instantTruncated);
//
//        LocalDateTime now =  LocalDateTime.now(Clock.systemUTC());
//        LocalDateTime newTime =  now.plusMinutes(1).truncatedTo(ChronoUnit.MILLIS);
//        System.out.println(newTime);


//
//        LocalDateTime plus = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plus(1, ChronoUnit.MINUTES);
//
//        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss.SSSSSS Z");
//        String format = plus.format(FORMATTER);
//        System.out.println(format);
//

//        2018-07-23T19:26:00.000Z


    }


//    http://www.java2s.com/Tutorial/Java/0490__Security/BasicclassforexploringPKCS1V15Signatures.htm
//    @Test
    public void validarAssinaturaNist() throws Exception{
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
        keyGen.initialize(512, new SecureRandom());
        KeyPair keyPair = keyGen.generateKeyPair();

        Signature signature = Signature.getInstance("SHA256withRSA", "BC");
        signature.initSign(keyPair.getPrivate());

        byte[] message = "abc".getBytes();
        signature.update(message);

        byte[] sigBytes = signature.sign();
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPublic());

        byte[] decSig = cipher.doFinal(sigBytes);
        ASN1InputStream aIn = new ASN1InputStream(decSig);
        ASN1Sequence seq = (ASN1Sequence) aIn.readObject();

        System.out.println(ASN1Dump.dumpAsString(seq));

        MessageDigest hash = MessageDigest.getInstance("SHA-256", "BC");
        hash.update(message);

        ASN1OctetString sigHash = (ASN1OctetString) seq.getObjectAt(1);
        System.out.println(MessageDigest.isEqual(hash.digest(), sigHash.getOctets()));

    }

//    @Test
    public void testeCampoStatusCode(){
        int teste = 0;
        System.out.println(teste);

        teste =  0x1;
        System.out.println(teste);

        teste = 0x10;
        System.out.println(teste);

        int by1 = -32;
        int by2 = 0xBB;
        System.out.println(by1);
        System.out.println(by2);


    }


}