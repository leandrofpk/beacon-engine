package br.gov.inmetro.beacon.v2.mypackage.domain.service;

import br.gov.inmetro.beacon.v1.domain.repository.PulsesRepository;
import br.gov.inmetro.beacon.v2.mypackage.application.PulseDto;
import br.gov.inmetro.beacon.v2.mypackage.domain.ActiveChainService;
import br.gov.inmetro.beacon.v2.mypackage.domain.pulse.ListValue;
import br.gov.inmetro.beacon.v2.mypackage.domain.pulse.Pulse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class PastOutputValuesService {

    private final PulsesRepository pulsesRepository;

    private final ActiveChainService activeChainService;

    @Autowired
    public PastOutputValuesService(PulsesRepository pulsesRepository, ActiveChainService activeChainService) {
        this.pulsesRepository = pulsesRepository;
        this.activeChainService = activeChainService;
    }

    @Transactional(readOnly = true)
    public List<ListValue> getOldPulses(ZonedDateTime currentTimestamp){
        List<ListValue> listValues = new ArrayList<>();

        // saber se o current é o mesmo da hora.
        ZonedDateTime primeroDaHora = currentTimestamp.truncatedTo(ChronoUnit.HOURS);
        System.out.println("Current:" + currentTimestamp);
        System.out.println("primeroDaHora:" + primeroDaHora);
        Pulse pulseHour = pulsesRepository.findOldPulses(activeChainService.get().getChainIndex(), primeroDaHora);

        if (pulseHour==null){
            PulseDto first = pulsesRepository.first(activeChainService.get().getChainIndex());
            listValues.add(ListValue.getOneValue(first.getOutputValue(), "hour", first.getUri()));
        } else {
            listValues.add(ListValue.getOneValue(pulseHour.getOutputValue(), "hour", pulseHour.getUri()));
        }

        //day
        ZonedDateTime primeroDoDia = currentTimestamp.truncatedTo(ChronoUnit.DAYS);
        System.out.println(primeroDoDia);
        Pulse pulseDay = pulsesRepository.findOldPulses(activeChainService.get().getChainIndex(), primeroDoDia);
        listValues.add(ListValue.getOneValue(pulseDay.getOutputValue(), "day", pulseDay.getUri()));

        //month
        ZonedDateTime primeroDoMes = currentTimestamp.with(ChronoField.DAY_OF_MONTH, 1).truncatedTo(ChronoUnit.DAYS);
        Pulse pulseMonth = pulsesRepository.findOldPulses(activeChainService.get().getChainIndex(), primeroDoMes);
        listValues.add(ListValue.getOneValue(pulseMonth.getOutputValue(), "month", pulseMonth.getUri()));

        //year
        ZonedDateTime primeroDoAno = currentTimestamp.withDayOfYear(1).truncatedTo(ChronoUnit.DAYS);
        System.out.println("primeroDoAno:" + primeroDoAno);
        Pulse pulseYear = pulsesRepository.findOldPulses(activeChainService.get().getChainIndex(), primeroDoAno);
        listValues.add(ListValue.getOneValue(pulseYear.getOutputValue(), "year", pulseYear.getUri()));

        return listValues;
    }


}
