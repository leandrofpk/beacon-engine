package br.gov.inmetro.beacon.v2.mypackage.infra.util;

import br.gov.inmetro.beacon.v2.mypackage.infra.util.suite0.CipherSuiteZero;

public class CipherSuiteBuilder {

    public static final ICipherSuite build(int suite){
        if (suite==0){
            return new CipherSuiteZero();
        } else {
            throw new CipherSuiteAlgoritmException("Invalid suite");
        }
    }

}
