package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import br.gov.inmetro.beacon.v1.domain.repository.PulsesRepository;
import br.gov.inmetro.beacon.v2.mypackage.application.PulseDto;
import br.gov.inmetro.beacon.v2.mypackage.queue.EntropyDto;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static br.gov.inmetro.beacon.v1.domain.service.CriptoUtilService.bytesToHex;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class NewPulseDomainServiceIT {

    @Autowired
    private NewPulseDomainService newPulseDomainService;

    @Autowired
    private PulsesRepository pulsesRepository;

    @Test
    public void teste() throws Exception {
        List<EntropyDto> list = new ArrayList<>();
        list.add(new EntropyDto("030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0",60000, "1", "2019-01-30T18:45:00.000Z"));
        list.add(new EntropyDto("4701694e62abddc3837cd8e309b8722e505f223aac4a3d4fc5b1fc080490e703cd630fe487abc803d4c0f2215c057207f349db70bfacfb75fd02491414d50e4f",60000, "1", "2019-01-30T18:46:00.000Z"));
        list.add(new EntropyDto("0aae39eb9d82d158f3100b0e8ab4fb955c149ea5a14e43c311890c047b590d393fca197f25556c6abec7f1c38fe93fac0616a0ddc846f9a1f86d5ec9492efc46",60000, "1", "2019-01-30T18:47:00.000Z"));
        list.add(new EntropyDto("98a3c8931de2c1957ce26c1a5efdf62774020c71dfb0b297dbd1aaab2cfa9154db335a30d0cbbb0db18eeae0da035ff61ab90664f3a1f652283b205ca1aa0155",60000, "1", "2019-01-30T18:48:00.000Z"));
        list.add(new EntropyDto("0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3",60000, "1", "2019-01-30T18:49:00.000Z"));
        list.add(new EntropyDto("37ef5148d07725133f32d5c7b13f07fd302a75a107764af727c0494a6e2e220e0a3a8b289eaf8e27964b95441ca642add63a392b1e40186608f4b05574518468",60000, "1", "2019-01-30T18:50:00.000Z"));
        list.add(new EntropyDto("accb69840c2426135ff2085ab413d0c7fc3be4b0f9d0d852e0abea8add5c322c00d23dea93bb6c4490445a98e5ecf447a737e1e63235ec0a308afd46ae86eccd",60000, "1", "2019-01-30T18:51:00.000Z"));
        list.add(new EntropyDto("a22c523d3ff106edb8c50e27281a25017d9af44ae7243aa0f529dbe7395f7e0b204178d8a987fe402b153ef3ad388158d2343f7fbbaeff014bb3c1be5b11651b",60000, "1", "2019-01-30T18:52:00.000Z"));
//        list.add(new EntropyDto("182c10c1419ae27012de788663485fdfc58667f6eecd17d186cda1bd9f0e41da0ad38e26d3dd9ea786e6bfae1c9ddbc206d95f65afe439d411ea614f1511a877",60000, "1", "2019-01-30T18:53:00.000Z"));
        list.add(new EntropyDto("182c10c1419ae27012de788663485fdfc58667f6eecd17d186cda1bd9f0e41da0ad38e26d3dd9ea786e6bfae1c9ddbc206d95f65afe439d411ea614f1511a877",60000, "1", "2019-01-30T18:54:00.000Z"));
        list.add(new EntropyDto("0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3",60000, "1", "2019-01-30T18:55:00.000Z"));
        list.add(new EntropyDto("37ef5148d07725133f32d5c7b13f07fd302a75a107764af727c0494a6e2e220e0a3a8b289eaf8e27964b95441ca642add63a392b1e40186608f4b05574518468",60000, "1", "2019-01-30T18:56:00.000Z"));
        list.add(new EntropyDto("accb69840c2426135ff2085ab413d0c7fc3be4b0f9d0d852e0abea8add5c322c00d23dea93bb6c4490445a98e5ecf447a737e1e63235ec0a308afd46ae86eccd",60000, "1", "2019-01-30T18:57:00.000Z"));
        list.add(new EntropyDto("a22c523d3ff106edb8c50e27281a25017d9af44ae7243aa0f529dbe7395f7e0b204178d8a987fe402b153ef3ad388158d2343f7fbbaeff014bb3c1be5b11651b",60000, "1", "2019-01-30T18:58:00.000Z"));
        list.add(new EntropyDto("7a12f3270ab64e2d90b0f32b8cebd2a24867f9e538ed5edbb6d07f570b49feeb7bde36940883ae137bff6737919c384037503352751a9d139282bc5d84b9bda4",60000, "1", "2019-01-30T18:59:00.000Z"));
        list.add(new EntropyDto("8689975ecc4e650ba69a7d1fc0819c0925a0589835ceb153ec94f0913a4ffaa2e54de7dabc49beec33b5dd94ca62e07d20328c8581d903cc486bc6f3b7b57094",60000, "1", "2019-01-30T19:00:00.000Z"));
        list.add(new EntropyDto("881b52d870e832b152ee28260d155372c140d68326294476bb8636a1e632de20b22a0b9ff3eb29e7d830c992ce97033e3f929d17b13d3c441fabc64f710c2d52",60000, "1", "2019-01-30T19:01:00.000Z"));
        list.add(new EntropyDto("905bfa1ab0efcdb07f52f71a4a008460d61792df8a3cfb83b590306e580fde4463111bfe3855b7f62eb3312da620ae2c1c7b42596bb56add87c03f775a4e6448",60000, "1", "2019-01-30T19:02:00.000Z"));

        newPulseDomainService.begin(list);

//        PulseDto first = pulsesRepository.first(1);
//        assertEquals(1, first.getPulseIndex());
    }

    @Test
    public void testarMudancaDoDia() throws Exception {
        List<EntropyDto> list = new ArrayList<>();
        list.add(new EntropyDto("030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0",60000, "1", "2019-08-22T23:54:00.000Z"));
        list.add(new EntropyDto("4701694e62abddc3837cd8e309b8722e505f223aac4a3d4fc5b1fc080490e703cd630fe487abc803d4c0f2215c057207f349db70bfacfb75fd02491414d50e4f",60000, "1", "2019-08-22T23:55:00.000Z"));
        list.add(new EntropyDto("0aae39eb9d82d158f3100b0e8ab4fb955c149ea5a14e43c311890c047b590d393fca197f25556c6abec7f1c38fe93fac0616a0ddc846f9a1f86d5ec9492efc46",60000, "1", "2019-08-22T23:56:00.000Z"));
        list.add(new EntropyDto("98a3c8931de2c1957ce26c1a5efdf62774020c71dfb0b297dbd1aaab2cfa9154db335a30d0cbbb0db18eeae0da035ff61ab90664f3a1f652283b205ca1aa0155",60000, "1", "2019-08-22T23:57:00.000Z"));
        list.add(new EntropyDto("0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3",60000, "1", "2019-08-22T23:58:00.000Z"));
        list.add(new EntropyDto("37ef5148d07725133f32d5c7b13f07fd302a75a107764af727c0494a6e2e220e0a3a8b289eaf8e27964b95441ca642add63a392b1e40186608f4b05574518468",60000, "1", "2019-08-22T23:59:00.000Z"));
        list.add(new EntropyDto("accb69840c2426135ff2085ab413d0c7fc3be4b0f9d0d852e0abea8add5c322c00d23dea93bb6c4490445a98e5ecf447a737e1e63235ec0a308afd46ae86eccd",60000, "1", "2019-08-23T00:00:00.000Z"));
        list.add(new EntropyDto("881b52d870e832b152ee28260d155372c140d68326294476bb8636a1e632de20b22a0b9ff3eb29e7d830c992ce97033e3f929d17b13d3c441fabc64f710c2d52",60000, "1", "2019-08-23T00:01:00.000Z"));
        list.add(new EntropyDto("905bfa1ab0efcdb07f52f71a4a008460d61792df8a3cfb83b590306e580fde4463111bfe3855b7f62eb3312da620ae2c1c7b42596bb56add87c03f775a4e6448",60000, "1", "2019-08-23T00:02:00.000Z"));
        list.add(new EntropyDto("7a12f3270ab64e2d90b0f32b8cebd2a24867f9e538ed5edbb6d07f570b49feeb7bde36940883ae137bff6737919c384037503352751a9d139282bc5d84b9bda4",60000, "1", "2019-08-23T00:03:00.000Z"));

        newPulseDomainService.begin(list);

    }

    @Test
    public void testSerie() throws Exception {
        List<EntropyDto> list = new ArrayList<>();
        ZonedDateTime start = ZonedDateTime.parse("2019-08-23T13:04:00.000Z");

        list.add(new EntropyDto(generateEntropy(),60000, "1", getTimeStampFormated(start)));

        ZonedDateTime actual = start;
        for (int i = 0; i < 1440; i++) {
            actual = actual
                    .truncatedTo(ChronoUnit.MINUTES).plus(1,ChronoUnit.MINUTES)
                    .withZoneSameInstant((ZoneOffset.
                            UTC).normalized());

            list.add(new EntropyDto(generateEntropy(),60000, "1", getTimeStampFormated(actual)));

        }

        newPulseDomainService.begin(list);

    }

    private String generateEntropy() throws NoSuchAlgorithmException {
        byte[] bytes = new byte[64];
        SecureRandom.getInstance("SHA1PRNG").nextBytes(bytes);
        return Hex.toHexString(bytes);
    }

    private String getTimeStampFormated(ZonedDateTime timestamp){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        String format = timestamp.withZoneSameInstant((ZoneOffset.UTC).normalized()).format(dateTimeFormatter);
        return format;
    }

}