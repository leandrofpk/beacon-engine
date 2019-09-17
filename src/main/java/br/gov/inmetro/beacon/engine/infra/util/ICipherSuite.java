package br.gov.inmetro.beacon.engine.infra.util;

import java.security.*;

public interface ICipherSuite {
    String getDigest(String input);
    String getDigest(byte[] input);
    String signPkcs15(PrivateKey privateKey, byte[] message) throws Exception;
    boolean verifyPkcs15(PublicKey publicKey, String sign, byte[] message) throws Exception;
}
