package br.gov.inmetro.beacon.engine.domain.service;

import br.gov.inmetro.beacon.engine.domain.pulse.LocalRandomValueDto;
import br.gov.inmetro.beacon.engine.domain.repository.PulsesRepository;
import br.gov.inmetro.beacon.engine.domain.pulse.ListValue;
import br.gov.inmetro.beacon.engine.domain.pulse.Pulse;
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

    @Transactional
    public List<ListValue> getOldPulses(LocalRandomValueDto currentDto, String uri){
        List<ListValue> listValues = new ArrayList<>();

        ZonedDateTime primeroDaHora = currentDto.getTimeStamp().truncatedTo(ChronoUnit.HOURS);
        Pulse pulseHour = pulsesRepository.findOldPulses(activeChainService.get().getChainIndex(), primeroDaHora);

        // saber se o current é o mesmo da hora.
        if (pulseHour==null){
//            PulseEntity first = pulsesRepository.first(activeChainService.get().getChainIndex());
//            listValues.add(ListValue.getOneValue(first.getOutputValue(), "hour", first.getUri()));
            listValues.add(ListValue.getOneValue(currentDto.getValue(), "hour", uri));  // current is the first of hour
        } else {
            listValues.add(ListValue.getOneValue(pulseHour.getOutputValue(), "hour", pulseHour.getUri()));
        }

        //dayh
        ZonedDateTime primeroDoDia = currentDto.getTimeStamp().truncatedTo(ChronoUnit.DAYS);
        Pulse pulseDay = pulsesRepository.findOldPulses(activeChainService.get().getChainIndex(), primeroDoDia);

        // saber se o current é o primeiro
//        System.out.println(String.format("%s:%s", currentDto.getTimeStamp().get(ChronoField.HOUR_OF_DAY), currentDto.getTimeStamp().get(ChronoField.MINUTE_OF_HOUR)));

        if (pulseDay==null){
            listValues.add(ListValue.getOneValue(currentDto.getValue(), "day", uri));  // current is the first of day
        } else {
            listValues.add(ListValue.getOneValue(pulseDay.getOutputValue(), "day", pulseDay.getUri()));
        }

        //month
        ZonedDateTime primeroDoMes = currentDto.getTimeStamp().with(ChronoField.DAY_OF_MONTH, 1).truncatedTo(ChronoUnit.DAYS);
        Pulse pulseMonth = pulsesRepository.findOldPulses(activeChainService.get().getChainIndex(), primeroDoMes);
        if (pulseMonth==null){
            listValues.add(ListValue.getOneValue(currentDto.getValue(), "month", uri));  // current is the first of month
        } else {
            listValues.add(ListValue.getOneValue(pulseMonth.getOutputValue(), "month", pulseMonth.getUri()));
        }

        //year
        ZonedDateTime primeroDoAno = currentDto.getTimeStamp().withDayOfYear(1).truncatedTo(ChronoUnit.DAYS);
        Pulse pulseYear = pulsesRepository.findOldPulses(activeChainService.get().getChainIndex(), primeroDoAno);
        if (pulseYear==null){
            listValues.add(ListValue.getOneValue(currentDto.getValue(), "year", uri));  // current is the first of year
        } else {
            listValues.add(ListValue.getOneValue(pulseYear.getOutputValue(), "year", pulseYear.getUri()));
        }

        return listValues;
    }

    private boolean isFirstOfDay(ZonedDateTime currentTimestamp){
        if ((currentTimestamp.get(ChronoField.HOUR_OF_DAY) == 0)
                && (currentTimestamp.get(ChronoField.MINUTE_OF_HOUR) == 0)) { // é o primeiro do dia
            return true;
        } else {
            return false;
        }
    }

}
