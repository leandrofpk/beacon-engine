package br.gov.inmetro.beacon.v2.mypackage.domain.certificate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para listar os certificados na API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDto {
    private String hash;
    private long chainIndex;
    private long startPulseIndex;
    private long endPulseIndex;
}
