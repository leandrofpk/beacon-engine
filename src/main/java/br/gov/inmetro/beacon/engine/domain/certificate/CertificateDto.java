package br.gov.inmetro.beacon.engine.domain.certificate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * DTO para listar os certificados na API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDto {
    private String hash;
    private long chainIndex;
    private ZonedDateTime expiration;
    private long startPulseIndex;
    private long endPulseIndex;
}
