package br.gov.inmetro.beacon.engine.domain.pulse;

import br.gov.inmetro.beacon.engine.application.PulseDto;
import br.gov.inmetro.beacon.engine.queue.EntropyDto;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CombineDomainResultTest {

    @Test
    public void combiningOneError(){
        List<EntropyDto> list = new ArrayList<>();

        list.add(new EntropyDto("030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0",60000, "1-Data Center Xerem", "2019-08-30T18:45:00.000Z"));
        list.add(new EntropyDto("4701694e62abddc3837cd8e309b8722e505f223aac4a3d4fc5b1fc080490e703cd630fe487abc803d4c0f2215c057207f349db70bfacfb75fd02491414d50e4f",60000, "2-LAINF", "2019-08-30T18:45:00.000Z"));

        list.add(new EntropyDto("98a3c8931de2c1957ce26c1a5efdf62774020c71dfb0b297dbd1aaab2cfa9154db335a30d0cbbb0db18eeae0da035ff61ab90664f3a1f652283b205ca1aa0155",60000, "2-LAINF", "2019-08-30T18:46:00.000Z"));

        CombineDomainService combineDomainService = new CombineDomainService(list, 1, 2, null, CombinationEnum.CONCAT);
        CombineDomainResult combineDomainResult = combineDomainService.processar();

        List<String> domainResultInText = combineDomainResult.getDomainResultInText();

        assertEquals("Number of expected sources: 2", domainResultInText.get(0));
        assertEquals("Timestamp:", domainResultInText.get(1).substring(0,10));
        assertEquals("List of numbers received(1):", domainResultInText.get(3));
        assertEquals("|Pulse timestamp: 2019-08-30T18:46:00.000Z | Source received: 2-LAINF|\n", domainResultInText.get(4));

    }

    @Test
    public void combiningTwoErrors() {
        List<EntropyDto> list = new ArrayList<>();
        list.add(new EntropyDto("030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0",60000, "1-Data Center Xerem", "2019-08-30T18:45:00.000Z"));
        list.add(new EntropyDto("4701694e62abddc3837cd8e309b8722e505f223aac4a3d4fc5b1fc080490e703cd630fe487abc803d4c0f2215c057207f349db70bfacfb75fd02491414d50e4f",60000, "2-LAINF", "2019-08-30T18:45:00.000Z"));

        list.add(new EntropyDto("0aae39eb9d82d158f3100b0e8ab4fb955c149ea5a14e43c311890c047b590d393fca197f25556c6abec7f1c38fe93fac0616a0ddc846f9a1f86d5ec9492efc46",60000, "1-Data Center Xerem", "2019-08-30T18:46:00.000Z"));

        list.add(new EntropyDto("37ef5148d07725133f32d5c7b13f07fd302a75a107764af727c0494a6e2e220e0a3a8b289eaf8e27964b95441ca642add63a392b1e40186608f4b05574518468",60000, "1-Data Center Xerem", "2019-08-30T18:47:00.000Z"));

        list.add(new EntropyDto("905bfa1ab0efcdb07f52f71a4a008460d61792df8a3cfb83b590306e580fde4463111bfe3855b7f62eb3312da620ae2c1c7b42596bb56add87c03f775a4e6448",60000, "2-LAINF", "2019-08-30T18:48:00.000Z"));

        list.add(new EntropyDto("7a12f3270ab64e2d90b0f32b8cebd2a24867f9e538ed5edbb6d07f570b49feeb7bde36940883ae137bff6737919c384037503352751a9d139282bc5d84b9bda4",60000, "2-LAINF", "2019-08-30T18:49:00.000Z"));

        PulseDto pulseDto = new PulseDto();
        pulseDto.setTimeStamp(ZonedDateTime.parse("2019-08-30T18:45:00.000Z"));

        CombineDomainService combineDomainService = new CombineDomainService(list, 1, 2, pulseDto, CombinationEnum.CONCAT);
        CombineDomainResult combineDomainResult = combineDomainService.processar();

        List<String> domainResultInText = combineDomainResult.getDomainResultInText();

        assertEquals("Number of expected sources: 2", domainResultInText.get(0));
        assertEquals("Timestamp:", domainResultInText.get(1).substring(0,10));
        assertEquals("List of numbers received(4):", domainResultInText.get(3));
        assertEquals("|Pulse timestamp: 2019-08-30T18:46:00.000Z | Source received: 1-Data Center Xerem|\n", domainResultInText.get(4));
        assertEquals("|Pulse timestamp: 2019-08-30T18:47:00.000Z | Source received: 1-Data Center Xerem|\n", domainResultInText.get(5));
        assertEquals("|Pulse timestamp: 2019-08-30T18:48:00.000Z | Source received: 2-LAINF|\n", domainResultInText.get(6));
        assertEquals("|Pulse timestamp: 2019-08-30T18:49:00.000Z | Source received: 2-LAINF|\n", domainResultInText.get(7));

        assertEquals("List of discarded numbers(1):", domainResultInText.get(9));
        assertEquals("|Pulse timestamp: 2019-08-30T18:45:00.000Z | Source(s) discarded: 1-Data Center Xerem;2-LAINF|\n", domainResultInText.get(10));
    }

}