package br.gov.inmetro.beacon.engine.infra;

import br.gov.inmetro.beacon.engine.domain.pulse.ProcessingErrorDto;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "processing_error")
public class ProcessingErrorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private ZonedDateTime timeStamp;

    @NotNull
    private int qtdSourcesExpected;

    @NotNull
    @Column(length = 200)
    private String usedOrDiscardedFonts;

    @NotNull
    @Column(length = 20)
    private String chain;

    @NotNull
    private ZonedDateTime timeStampError;

    @NotNull
    @Column(length = 20, name = "processingErrorType")
    @Enumerated(EnumType.STRING)
    private ProcessingErrorTypeEnum processingErrorTypeEnum;

    public ProcessingErrorEntity() {

    }

    public ProcessingErrorEntity(ProcessingErrorDto dto) {
        this.timeStamp = dto.getTimeStamp();
        this.qtdSourcesExpected = dto.getQtdSourcesExpected();
        this.usedOrDiscardedFonts = dto.getUsedOrDiscardedFonts();
        this.qtdSourcesExpected = dto.getQtdSourcesExpected();
        this.chain = dto.getChain();
        this.timeStampError = dto.getTimeStampError();
        this.processingErrorTypeEnum = dto.getProcessingErrorTypeEnum();
    }
}
