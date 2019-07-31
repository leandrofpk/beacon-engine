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
        list.add(new EntropyDto("1111", 60000, "1", "2019-07-30T18:22:00.000Z"));
        newPulseDomainService.begin(list);

//        PulseDto first = pulsesRepository.first(1);

//        assertEquals(1, first.getPulseIndex());
    }

}