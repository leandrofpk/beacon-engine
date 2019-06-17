package br.gov.inmetro.beacon.infra;

import br.gov.inmetro.beacon.core.dominio.schedule.ProcessingErrorDto;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
public class ProcessingErrorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long timestamp;

    @NotNull
    private LocalDateTime timestampDate;

    @NotNull
    private int qtdSourcesExpected;

    @NotNull
    @Column(length = 20)
    private String usedOrDiscardedFonts;

    @NotNull
    @Column(length = 20)
    private String chain;

    @NotNull
    private LocalDateTime timestampError;

    @NotNull
    @Column(length = 20, name = "processing_error_type")
    @Enumerated(EnumType.STRING)
    private ProcessingErrorTypeEnum processingErrorTypeEnum;

    public ProcessingErrorEntity() {

    }

    public ProcessingErrorEntity(ProcessingErrorDto dto) {
        this.timestamp = dto.getTimestamp();
        this.qtdSourcesExpected = dto.getQtdSourcesExpected();
        this.usedOrDiscardedFonts = dto.getUsedOrDiscardedFonts();
        this.timestampDate = DateUtil.longToLocalDateTime(String.valueOf(this.timestamp));
        this.qtdSourcesExpected = dto.getQtdSourcesExpected();
        this.chain = dto.getChain();
        this.timestampError = dto.getTimestampError();
        this.processingErrorTypeEnum = dto.getProcessingErrorTypeEnum();
    }
}
