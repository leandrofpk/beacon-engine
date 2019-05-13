package br.gov.inmetro.beacon;

import br.gov.inmetro.beacon.domain.RegistroDto;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class RegistroTest {

    @Test
    public void validarFormatoDaData(){
        RegistroDto registro = new RegistroDto("1", "19/05/2018 23:27","uma assinatura");
        registro.toString();
    }

    @Test
    public void validarFormatoDaData2(){
        RegistroDto registro = new RegistroDto("1", LocalDateTime.now(),"uma assinatura");
        registro.toString();
    }

    @Test
    public void testarDadosDoNist(){
        String timestamp = "";
        timestamp = "1528932240";
        System.out.println(longToLocalDateTime(timestamp));

        timestamp = "1528932254497";
        System.out.println(longToLocalDateTime(timestamp));

        timestamp = "1528932255100";
        System.out.println(longToLocalDateTime(timestamp));

        timestamp = "1528932255488";
        System.out.println(longToLocalDateTime(timestamp));

        timestamp = "1537902726";
        System.out.println(longToLocalDateTime(timestamp));

        timestamp = "1537902726";
        System.out.println(longToLocalDateTimeTwo(timestamp));

    }

    private LocalDateTime longToLocalDateTime(String data){
//        long millis = new Long(data);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(new Long(data)), ZoneId.of("America/Sao_Paulo"));

        return localDateTime;
    }


    private LocalDateTime longToLocalDateTimeTwo(String data){
        Long millis = new Long(data);
        if (data.length() == 10){
            millis = millis*1000;
        }

        // atual
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("America/Sao_Paulo"));

        //teste
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(new Long(data)), ZoneId.of("America/Sao_Paulo"));


        return localDateTime;
    }


}
