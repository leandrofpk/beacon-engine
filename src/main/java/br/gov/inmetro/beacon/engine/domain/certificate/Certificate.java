package br.gov.inmetro.beacon.engine.domain.certificate;

/**
 * Controlar o certificado
 * A última linha do BD é o ativo
 * Implementar uma interface para decidir se o certificado vem do file system ou do HSM
 */
public class Certificate {

    private String hash;

    private long chainIndex;

    private String certificate;

}
