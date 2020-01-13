package br.gov.inmetro.beacon.engine.infra.util;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static ZonedDateTime getCurrentTrucatedZonedDateTime(){
        ZonedDateTime now = ZonedDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES)
                .withZoneSameInstant((ZoneOffset.UTC).normalized());
        return now;
    }

    public static String getTimeStampFormated(ZonedDateTime timestamp){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        String format = timestamp.withZoneSameInstant((ZoneOffset.UTC).normalized()).format(dateTimeFormatter);
        return format;
    }

}
