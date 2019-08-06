package br.gov.inmetro.beacon.v2.mypackage.domain.service;

import br.gov.inmetro.beacon.v1.domain.repository.PulsesRepository;
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
        System.out.println("primeroDaHora:" + primeroDaHora);
        Pulse pulseHour = pulsesRepository.findOldPulses(activeChainService.get().getChainIndex(), primeroDaHora);
        listValues.add(ListValue.getOneValue(pulseHour.getLocalRandomValue(), "hour", pulseHour.getUri()));

        ZonedDateTime primeroDoDia = currentTimestamp.truncatedTo(ChronoUnit.DAYS);
        System.out.println(primeroDoDia);
        Pulse pulseDay = pulsesRepository.findOldPulses(activeChainService.get().getChainIndex(), primeroDoDia);
        listValues.add(ListValue.getOneValue(pulseDay.getLocalRandomValue(), "day", pulseDay.getUri()));

        ZonedDateTime primeroDoMes = currentTimestamp.with(ChronoField.DAY_OF_MONTH, 1).truncatedTo(ChronoUnit.DAYS);
        Pulse pulseMonth = pulsesRepository.findOldPulses(activeChainService.get().getChainIndex(), primeroDoMes);
        listValues.add(ListValue.getOneValue(pulseMonth.getLocalRandomValue(), "month", pulseMonth.getUri()));

        ZonedDateTime primeroDoAno = currentTimestamp.withDayOfYear(1).truncatedTo(ChronoUnit.DAYS);
        Pulse pulseYear = pulsesRepository.findOldPulses(activeChainService.get().getChainIndex(), primeroDoAno);
        listValues.add(ListValue.getOneValue(pulseYear.getLocalRandomValue(), "year", pulseYear.getUri()));

        return listValues;
    }


}
