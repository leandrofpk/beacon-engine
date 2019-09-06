package br.gov.inmetro.beacon.engine.infra.util;

import java.security.*;

public interface ICipherSuite {
    String getDigest(String input);
    String getDigest(byte[] input);
    String sign(PrivateKey privateKey, byte[] message) throws Exception;
    boolean verify(PublicKey publicKey, String sign, byte[] message) throws Exception;
}
