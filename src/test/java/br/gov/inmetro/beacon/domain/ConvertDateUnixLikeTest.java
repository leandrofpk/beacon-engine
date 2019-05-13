package br.gov.inmetro.beacon.domain;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ConvertDateUnixLikeTest {

    @Test
    public void converterDataParaLong() {
// how to transform unix time (number of milliseconds since
        // 1.1.1970) to date / date and time
        {
            long millis = 1450539174378L;

            // unix time to java.util.Date (pre Java 8)
            System.out.println(new Date(millis));

            // unix time to java.time.LocalDate (since Java 8)
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
            System.out.println(localDateTime);

            // unix time to java.time.LocalDateTime (since Java 8)
            LocalDate localDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault()).toLocalDate();
            System.out.println(localDate);

        }

        // how to transform date to unix time
        {
            // get unix time from java.util.Date (pre Java 8)
            System.out.println(new Date().getTime());

            // get unix time from java.time.LocalDate (since Java 8)
            System.out.println(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());

            // get unix time from java.time.LocalDateTime (since Java 8)
            System.out.println(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }

//        public void teste3(){
//
//        }

    }

}
