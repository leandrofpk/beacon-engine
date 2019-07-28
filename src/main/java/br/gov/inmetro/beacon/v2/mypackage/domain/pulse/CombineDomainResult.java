package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import br.gov.inmetro.beacon.v1.application.api.LocalRandomValueDto;
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
