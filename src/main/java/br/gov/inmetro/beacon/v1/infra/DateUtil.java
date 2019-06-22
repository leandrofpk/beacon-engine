package br.gov.inmetro.beacon.v1.infra;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static LocalDateTime longToLocalDateTime(String data){
        Long millis = new Long(data);
        if (data.length() == 10){
            millis = millis*1000;
        }

        // atual
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis),
                ZoneId.of("America/Sao_Paulo")).truncatedTo(ChronoUnit.MINUTES);

        return localDateTime;
    }

}
