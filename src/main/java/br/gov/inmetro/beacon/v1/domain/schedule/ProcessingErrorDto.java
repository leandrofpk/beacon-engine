package br.gov.inmetro.beacon.v1.domain.schedule;

import br.gov.inmetro.beacon.v1.infra.ProcessingErrorTypeEnum;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
public class ProcessingErrorDto {

    private Long timestamp;

    private LocalDateTime timestampDate;

    private int qtdSourcesExpected;

    private String usedOrDiscardedFonts;

    private String chain;

    private LocalDateTime timestampError;

    @Enumerated(EnumType.STRING)
    private ProcessingErrorTypeEnum processingErrorTypeEnum;

    public ProcessingErrorDto(Long timestamp, int qtdSourcesExpected,
                              String usedOrDiscardedFonts, String chain,
                              LocalDateTime timestampError, ProcessingErrorTypeEnum processingErrorTypeEnum) {
        this.timestamp = timestamp;
//        this.timestampDate = timestampDate;
        this.qtdSourcesExpected = qtdSourcesExpected;
        this.usedOrDiscardedFonts = usedOrDiscardedFonts;
        this.chain = chain;
        this.timestampError = timestampError;
        this.processingErrorTypeEnum = processingErrorTypeEnum;
    }
}
