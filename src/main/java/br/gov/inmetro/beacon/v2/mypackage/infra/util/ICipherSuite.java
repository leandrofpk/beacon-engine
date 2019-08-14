package br.gov.inmetro.beacon.v2.mypackage.infra.util;

import java.security.Key;
import java.security.PublicKey;

public interface ICipherSuite {
    String getDigest(String input);
    String getDigest(byte[] input);
    String signBytes15(String plainTextBytes, Key privateKey);
    String verifySignBytes15(String cipherText, PublicKey pubKey);
}
