package br.gov.inmetro.beacon.v2.mypackage.domain;


import org.junit.Test;

import java.time.LocalDateTime;

class PulseTest {


//    private String uri;
//    private String version;
//    private int cipherSuite;
//    private int period;
//    private String certificateId;
//    private long chainIndex;
//    private long pulseIndex;
//    private LocalDateTime timeStamp;


    @Test
    public void teste(){
        Pulse builder = new Pulse.Builder()
                .setUri("")
                .setVersion("")
                .setCipherSuite(0)
                .setPeriod(0)
                .setCertificateId("")
                .setChainIndex(0)
                .setPulseIndex(0)
                .setTimeStamp(LocalDateTime.now())
                .build();
    }

}