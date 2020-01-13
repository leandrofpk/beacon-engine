package br.gov.inmetro.beacon.engine.domain.pulse;

import lombok.Getter;

import java.util.List;

@Getter
public final class CombineDomainResult {

    private final List<LocalRandomValueDto> localRandomValueDtos;

    private final List<ProcessingErrorDto> combineErrorList;

    public CombineDomainResult(List<LocalRandomValueDto> localRandomValueDtos, List<ProcessingErrorDto> combineErrorList) {
        this.localRandomValueDtos = localRandomValueDtos;
        this.combineErrorList = combineErrorList;
    }


}
