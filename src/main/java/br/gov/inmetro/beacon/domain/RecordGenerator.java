package br.gov.inmetro.beacon.domain;

import java.nio.charset.StandardCharsets;

public class RecordGenerator {

    private String seed;

    public RecordGenerator(String seed, String previousOutputValue) {
        this.seed = seed;
    }

    public void iniciar(){
//        String HashSeed = gerarHashSha512(this.seed);


    }

//    private String gerarHashSha512(String message){
//        String sha256hex = Hashing.sha256()
//                .hashString(message, StandardCharsets.UTF_8)
//                .toString();
//
//        return sha256hex;
//
//
//    }


}
