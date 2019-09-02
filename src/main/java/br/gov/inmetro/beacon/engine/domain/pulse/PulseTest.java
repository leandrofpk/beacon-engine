package br.gov.inmetro.beacon.engine.domain.pulse;


import br.gov.inmetro.beacon.engine.domain.chain.ChainValueObject;
import org.apache.commons.lang3.time.DateUtils;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.ByteBuffer;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.charset.StandardCharsets.UTF_8;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class PulseTest {

    @Autowired
    Environment env;

    private Pulse aPulse;

//    @Before
    public void init(){
        ChainValueObject activeChain = new ChainValueObject("2.0", 0, 60000, 1);

        List<ListValue> listValue = new ArrayList<>();
        listValue.add(ListValue.getOneValue("000", "previous", "000"));

        Pulse newRegularPulse = new Pulse.Builder()
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
//                .setSignatureValue("signature")
//                .setOutputValue("outputValue")
                .build();

        aPulse = newRegularPulse;

    }

    /**
     * Should build a pulse for the first chain
     */
    @Test
    public void shouldBuildTheFirstPulseTest(){

//        ChainValueObject


//        System.out.println(newRegularPulse.toString());

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
    }




    @Test
    public void testingDate(){
        ZonedDateTime date1 = ZonedDateTime.parse("2019-07-30T18:22:00.000Z");
        System.out.println(date1);

        ZonedDateTime primeroDaHora = date1.truncatedTo(ChronoUnit.HOURS);
        ZonedDateTime primeroDoDia = date1.truncatedTo(ChronoUnit.DAYS);

        System.out.println(primeroDaHora);
        System.out.println(primeroDoDia);

        ZonedDateTime startofMonth = date1.with(ChronoField.DAY_OF_MONTH, 1).truncatedTo(ChronoUnit.DAYS);
        System.out.println("startofMonth");
        System.out.println(startofMonth);

        ZonedDateTime startofYear = date1.withDayOfYear(1).truncatedTo(ChronoUnit.DAYS);
        System.out.println("startofYear");
        System.out.println(startofYear);


        long between1 = ChronoUnit.MINUTES.between(date1, date1);
        System.out.println(between1);

        long between2 = ChronoUnit.MINUTES.between(date1.minus(1, ChronoUnit.MINUTES), date1.plus(2, ChronoUnit.MINUTES));
        System.out.println(between2);


    }

    @Test
    public void testLenthSerializationHash(){
        String hash =  "5C571D1B7641A359DE56A2498D4B972F4AFD6C85752381790E575E70B3BA7CBD7F5D6C646675F48C696884B0381FDC751C6153102EA14023F2719E23FE0C931C";
        int bLenHash = 64;
        byte[] bytes1 = ByteBuffer.allocate(4).putInt(bLenHash).array();
        byte[] bytes2 = ByteUtils.fromHexString(hash);
        byte[] concatenate = ByteUtils.concatenate(bytes1, bytes2);
        System.out.println(concatenate.length);
    }

    @Test
    public void testSerializeString(){
        //aqui
//        aqui

        String dateStr = "2019-08-21T02:50:00.000Z";

        byte[] bytes1 = dateStr.getBytes(US_ASCII);
        System.out.println(bytes1.length);

        String value = "2.0";
        int bytLen = value.getBytes(UTF_8).length;
        byte[] bytesA1 = value.getBytes(UTF_8);
        byte[] bytesA2 = value.getBytes(UTF_8);



        byte[] concatenate = ByteUtils.concatenate(bytesA1, bytesA2);




        // page 15 Draft
        String uri = "https://beacon.nist.gov/beacon/2.0/chain/1/pulse/1";
        byte[] bytes3 = uri.getBytes(US_ASCII);
        System.out.println(bytes3.length);



    }

}