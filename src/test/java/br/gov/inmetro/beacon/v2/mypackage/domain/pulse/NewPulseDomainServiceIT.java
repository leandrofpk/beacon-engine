package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import br.gov.inmetro.beacon.v1.domain.repository.PulsesRepository;
import br.gov.inmetro.beacon.v2.mypackage.application.PulseDto;
import br.gov.inmetro.beacon.v2.mypackage.queue.EntropyDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class NewPulseDomainServiceIT {

    @Autowired
    private NewPulseDomainService newPulseDomainService;

    @Autowired
    private PulsesRepository pulsesRepository;

    @Test
    public void teste(){
        List<EntropyDto> list = new ArrayList<>();
        list.add(new EntropyDto("030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0",
                60000, "1", "2019-07-30T18:22:00.000Z"));
        list.add(new EntropyDto("0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3",
                60000, "1", "2019-07-30T18:23:00.000Z"));
        list.add(new EntropyDto("37ef5148d07725133f32d5c7b13f07fd302a75a107764af727c0494a6e2e220e0a3a8b289eaf8e27964b95441ca642add63a392b1e40186608f4b05574518468",
                60000, "1", "2019-07-30T18:24:00.000Z"));
        newPulseDomainService.begin(list);

//        PulseDto first = pulsesRepository.first(1);
//        assertEquals(1, first.getPulseIndex());
    }

}