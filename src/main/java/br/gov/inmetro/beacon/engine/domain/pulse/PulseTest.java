package br.gov.inmetro.beacon.engine.domain.pulse;

import br.gov.inmetro.beacon.engine.domain.chain.ChainValueObject;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

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

    private Pulse aPulse;

//    @Before
    public void init(){
        ChainValueObject activeChain = new ChainValueObject("2.0" ,"Version 2.0", 0, 60000, 1);

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

    @Test
    public void testarData(){
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


        System.out.println("Instant setado pra zero");
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

}