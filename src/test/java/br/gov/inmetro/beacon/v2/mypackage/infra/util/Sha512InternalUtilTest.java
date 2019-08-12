package br.gov.inmetro.beacon.v2.mypackage.infra.util;

import org.junit.Test;

public class Sha512InternalUtilTest {

    @Test
    public void testing(){

        Sha512InternalUtil mdg = new Sha512InternalUtil();

        String sha512 = mdg.getDigest("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");

        System.out.println(sha512);

    }

}