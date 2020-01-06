package br.gov.inmetro.beacon.engine.domain.service;

import br.gov.inmetro.beacon.engine.domain.repository.PulsesRepository;
import br.gov.inmetro.beacon.engine.application.PulseDto;
import br.gov.inmetro.beacon.engine.infra.PulseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuerySinglePulsesService {

    private final PulsesRepository pulsesRepository;

    private final ActiveChainService activeChainService;

    @Autowired
    public QuerySinglePulsesService(PulsesRepository records, ActiveChainService activeChainService) {
        this.pulsesRepository = records;
        this.activeChainService = activeChainService;
    }

    public List<PulseDto> findLast20(Integer chain) {
        List<PulseDto> dtos = new ArrayList<>();
        pulsesRepository.obterTodos(chain).forEach(pulseEntity -> dtos.add(new PulseDto(pulseEntity)));
        return Collections.unmodifiableList(dtos);
    }

    public Optional<PulseEntity> last(Long chain) {
        return Optional.ofNullable(pulsesRepository.last(chain));
    }

    public PulseDto lastDto(Long chain) {
        PulseEntity last = pulsesRepository.last(chain);
        if (last==null){
            return null;
        } else {
            return new PulseDto(last);
        }
    }

    public PulseDto firstDto(Long chain) {
        PulseEntity last = pulsesRepository.first(chain);
        if (last==null){
            return null;
        } else {
            return new PulseDto(last);
        }
    }

//    public PulseDto findByTimestamp(ZonedDateTime timeStamp){
//        PulseEntity byTimestamp = pulsesRepository.findByTimestamp(timeStamp);
//        if (byTimestamp==null){
//            return null;
//        } else {
//            return new PulseDto(byTimestamp);
//        }
//    }

    public PulseDto findSpecificTime(ZonedDateTime specificTimeStamp){
        PulseEntity byTimestamp = pulsesRepository.findByTimestamp(specificTimeStamp);
        if (byTimestamp==null){

            PulseEntity next = pulsesRepository.findNext(specificTimeStamp);
            PulseEntity previous = pulsesRepository.findPrevious(specificTimeStamp);

            long between1 = ChronoUnit.MINUTES.between(specificTimeStamp, next.getTimeStamp());
            long between2 = ChronoUnit.MINUTES.between(previous.getTimeStamp(), specificTimeStamp);

            if (between1==0 && between2==0){
                return null;
            }

            if (between1 <= between2){
                return new PulseDto(pulsesRepository.findByTimestamp(next.getTimeStamp()));
            } else {
                return new PulseDto(pulsesRepository.findByTimestamp(previous.getTimeStamp()));
            }

        } else {
            return new PulseDto(pulsesRepository.findByTimestamp(byTimestamp.getTimeStamp()));
        }
    }

    public PulseDto findNext(ZonedDateTime timeStamp){
        PulseEntity next = pulsesRepository.findNext(timeStamp);
        if (next==null){
            return null;
        } else {
            return new PulseDto(pulsesRepository.findByTimestamp(next.getTimeStamp()));
        }
    }

    public PulseDto findPrevious(ZonedDateTime timeStamp){
        PulseEntity next = pulsesRepository.findPrevious(timeStamp);
        if (next==null){
            return null;
        } else {
            return new PulseDto(pulsesRepository.findByTimestamp(next.getTimeStamp()));
        }
    }


    public PulseDto findLast() {
        PulseEntity last = pulsesRepository.last(activeChainService.get().getChainIndex());

        if (last==null){
            return null;
        } else {
            return new PulseDto(last);
        }

    }

    public PulseDto findByChainAndPulseIndex(Long chainIndex, Long pulseIndex){
        PulseEntity last = pulsesRepository.findByChainAndPulseIndex(chainIndex, pulseIndex);

        if (last == null){
            return null;
        } else {
            return new PulseDto(last);
        }
    }

}