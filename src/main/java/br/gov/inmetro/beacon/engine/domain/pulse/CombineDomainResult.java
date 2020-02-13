package br.gov.inmetro.beacon.engine.domain.pulse;

import br.gov.inmetro.beacon.engine.infra.ProcessingErrorTypeEnum;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.gov.inmetro.beacon.engine.infra.util.DateUtil.getTimeStampFormated;

@Getter
public final class CombineDomainResult {

    private final List<LocalRandomValueDto> localRandomValueDtos;

    private final List<ProcessingErrorDto> combineErrorList;

    private final int numberOfSources;

    public CombineDomainResult(List<LocalRandomValueDto> localRandomValueDtos, List<ProcessingErrorDto> combineErrorList,
                               int numberOfSources) {
        this.localRandomValueDtos = localRandomValueDtos;
        this.combineErrorList = combineErrorList;
        this.numberOfSources = numberOfSources;
    }

    public List<String> getDomainResultInText(){
        List<String> text = new ArrayList();

        text.add(String.format("Number of expected sources: %s", numberOfSources));
        text.add(String.format("Timestamp: %s", LocalDateTime.now()));

        List<ProcessingErrorDto> errors1 = combineErrorList
                .stream()
                .filter(c -> c.getProcessingErrorTypeEnum().equals(ProcessingErrorTypeEnum.COMBINING))
                .collect(Collectors.toList());

        if (errors1.size() > 0){
            text.add("");
            text.add(String.format("List of numbers received(%s):", errors1.size()));

            for (ProcessingErrorDto dto: errors1) {
               text.add(String.format("|Pulse timestamp: %s | Source received: %s|\n", getTimeStampFormated(dto.getTimeStamp()), dto.getUsedOrDiscardedFonts()));
            }
        }

        List<ProcessingErrorDto> errors2 = combineErrorList
                .stream()
                .filter(c -> c.getProcessingErrorTypeEnum().equals(ProcessingErrorTypeEnum.DISCARDED_NUMBER))
                .collect(Collectors.toList());

        if (errors2.size() > 0){
            text.add("");
            text.add(String.format("List of discarded numbers(%s):", errors2.size()));

            for (ProcessingErrorDto dto: errors2) {
                text.add(String.format("|Pulse timestamp: %s | Source(s) discarded: %s|\n", getTimeStampFormated(dto.getTimeStamp()), dto.getUsedOrDiscardedFonts()));
            }
        }
//
//        return sb.toString();

        return text;
    }

}
