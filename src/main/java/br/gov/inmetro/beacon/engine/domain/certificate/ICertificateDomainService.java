package br.gov.inmetro.beacon.engine.domain.certificate;

public interface ICertificateDomainService {
    Certificate getActiveCertificate();  // o ativo é o mais recente do banco
    Certificate findByHash(String hash);
    CertificateDto listCertIDsInChain(long chainIndex);
}
