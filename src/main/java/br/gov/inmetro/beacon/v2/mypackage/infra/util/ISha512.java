package br.gov.inmetro.beacon.v2.mypackage.infra.util;

import java.security.PrivateKey;

public interface ISha512 {
    String getDigest(String input);
    String getDigest(byte[] input);
    String signBytes15(String plainTextBytes, PrivateKey privateKey) throws Exception;
}
