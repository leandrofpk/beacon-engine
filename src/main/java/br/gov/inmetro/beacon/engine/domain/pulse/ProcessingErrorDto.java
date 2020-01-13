package br.gov.inmetro.beacon.engine.domain.pulse;

import br.gov.inmetro.beacon.engine.infra.ProcessingErrorTypeEnum;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.ZonedDateTime;

@Getter
public class ProcessingErrorDto {

    private ZonedDateTime timeStamp;

    private int qtdSourcesExpected;

    private String usedOrDiscardedFonts;

    private String chain;

    private ZonedDateTime timeStampError;

    @Enumerated(EnumType.STRING)
    private ProcessingErrorTypeEnum processingErrorTypeEnum;

    public ProcessingErrorDto(ZonedDateTime timeStamp, int qtdSourcesExpected,
                              String usedOrDiscardedFonts, String chain,
                              ZonedDateTime timeStampError, ProcessingErrorTypeEnum processingErrorTypeEnum) {
        this.timeStamp = timeStamp;
        this.qtdSourcesExpected = qtdSourcesExpected;
        this.usedOrDiscardedFonts = usedOrDiscardedFonts;
        this.chain = chain;
        this.timeStampError = timeStampError;
        this.processingErrorTypeEnum = processingErrorTypeEnum;
    }

    @Override
    public String toString() {
        return "ProcessingErrorDto{" +
                "timeStamp=" + timeStamp +
                ", qtdSourcesExpected=" + qtdSourcesExpected +
                ", usedOrDiscardedFonts='" + usedOrDiscardedFonts + '\'' +
                ", chain='" + chain + '\'' +
                ", timeStampError=" + timeStampError +
                ", processingErrorTypeEnum=" + processingErrorTypeEnum +
                '}';
    }
}
