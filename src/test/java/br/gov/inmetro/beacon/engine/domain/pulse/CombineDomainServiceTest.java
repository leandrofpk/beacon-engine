package br.gov.inmetro.beacon.engine.domain.pulse;

import br.gov.inmetro.beacon.engine.application.PulseDto;
import br.gov.inmetro.beacon.engine.queue.EntropyDto;
import org.junit.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CombineDomainServiceTest {

    @Test
    public void testeCombinacaoXorDoisValores(){
        List<EntropyDto> regularNoises = new ArrayList<>();

        PulseDto recordLastDto = new PulseDto();

        ZonedDateTime nowLast = ZonedDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES).minus(3, ChronoUnit.MINUTES)
                .withZoneSameInstant((ZoneOffset.UTC).normalized());

        recordLastDto.setTimeStamp(nowLast);

        ZonedDateTime now1 = ZonedDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES)
                .withZoneSameInstant((ZoneOffset.UTC).normalized());

        EntropyDto noiseDto1 = new EntropyDto("030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0",
                60000, "1", now1.toString());

        EntropyDto noiseDto2 = new EntropyDto("0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3",
                60000, "2", now1.toString());

        ZonedDateTime now3 = ZonedDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES).minus(2, ChronoUnit.MINUTES)
                .withZoneSameInstant((ZoneOffset.UTC).normalized());

        EntropyDto noiseDto3 = new EntropyDto("37ef5148d07725133f32d5c7b13f07fd302a75a107764af727c0494a6e2e220e0a3a8b289eaf8e27964b95441ca642add63a392b1e40186608f4b05574518468",
                60000, "1", now3.toString());

        ZonedDateTime now4 = ZonedDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES)
                .plus(1, ChronoUnit.MINUTES)
                .withZoneSameInstant((ZoneOffset.UTC).normalized());

        EntropyDto noiseDto4 = new EntropyDto("a22c523d3ff106edb8c50e27281a25017d9af44ae7243aa0f529dbe7395f7e0b204178d8a987fe402b153ef3ad388158d2343f7fbbaeff014bb3c1be5b11651b",
                60000, "1", now4.toString());

        EntropyDto noiseDto5 = new EntropyDto("9fa00ee919ce93c40e6ef83c2fdf499f257037101a36aa682119920130b23f3a45bb7f17890ba8af2c4316a758148ee5c6a5afb2aafd2b625407438bb177e30d",
                60000, "2", now4.toString());

        regularNoises.add(noiseDto1);
        regularNoises.add(noiseDto2);
        regularNoises.add(noiseDto3);
        regularNoises.add(noiseDto4);
        regularNoises.add(noiseDto5);

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, 1, 2, recordLastDto, CombinationEnum.XOR);
        CombineDomainResult combineDomainResult = combineDomainService.processar();

        List<LocalRandomValueDto> recordSimpleDtoList = combineDomainResult.getLocalRandomValueDtos();
        List<ProcessingErrorDto> combineErrorList = combineDomainResult.getCombineErrorList();

        // não precisou combinar
        assertEquals(noiseDto3.getRawData(), recordSimpleDtoList.get(0).getValue());
        assertEquals(noiseDto3.getTimeStamp(), combineErrorList.get(0).getTimeStamp());

        // teste de combinação
        assertEquals("3d8c5cd4263f9529b6abf61b07c56c9e58eac35afd1290c8d43049e609ed413165fa07cf208c56ef07562854f52c0fbd149190cd1153d4631fb48235ea668616",
                recordSimpleDtoList.get(2).getValue());
    }

    @Test
    public void testeCombinacaoXorTresValores(){
        List<EntropyDto> regularNoises = new ArrayList<>();

        PulseDto recordLastDto = new PulseDto();
        ZonedDateTime nowLast = ZonedDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES).minus(1,ChronoUnit.MINUTES).withZoneSameInstant((ZoneOffset.UTC).normalized());
        recordLastDto.setTimeStamp(nowLast);


        ZonedDateTime now1 = ZonedDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES).withZoneSameInstant((ZoneOffset.UTC).normalized());

        EntropyDto noiseDto1 = new EntropyDto("030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0",
                60000, "1", now1.toString());

        EntropyDto noiseDto2 = new EntropyDto("0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3",
                60000, "2", now1.toString());

        EntropyDto noiseDto3 = new EntropyDto("37ef5148d07725133f32d5c7b13f07fd302a75a107764af727c0494a6e2e220e0a3a8b289eaf8e27964b95441ca642add63a392b1e40186608f4b05574518468",
                60000, "3", now1.toString());

        EntropyDto noiseDto4 = new EntropyDto("a22c523d3ff106edb8c50e27281a25017d9af44ae7243aa0f529dbe7395f7e0b204178d8a987fe402b153ef3ad388158d2343f7fbbaeff014bb3c1be5b11651b",
                60000, "1", now1.plus(1,ChronoUnit.MINUTES).toString());

        regularNoises.add(noiseDto1);
        regularNoises.add(noiseDto2);
        regularNoises.add(noiseDto3);
        regularNoises.add(noiseDto4);

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, 1L, 3, recordLastDto, CombinationEnum.XOR);
        CombineDomainResult combineDomainResult = combineDomainService.processar();

        List<LocalRandomValueDto> recordSimpleDtoList = combineDomainResult.getLocalRandomValueDtos();
        List<ProcessingErrorDto> combineErrorList = combineDomainResult.getCombineErrorList();

        // combining test
        assertEquals("39ff40c1857eb9b71828ebbc1b4309382a9d54a4a9e4241c7d3641f801653c3b73a84b0b1f375620fb911d6a73a900ed3c0cb7c413cdb785d96309785265ed1b",
                recordSimpleDtoList.get(0).getValue());
        assertEquals(2, recordSimpleDtoList.size());

        // teste de erro
        assertEquals("1", combineErrorList.get(0).getUsedOrDiscardedFonts());
    }

    @Test
    public void deveRetornarErroDeDuasFontesXor(){
        List<EntropyDto> regularNoises = new ArrayList<>();


        ZonedDateTime nowLast = ZonedDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES).minus(1,ChronoUnit.MINUTES)
                .withZoneSameInstant((ZoneOffset.
                        UTC).normalized());

        PulseDto recordLastDto = new PulseDto();
        recordLastDto.setTimeStamp(nowLast);

        ZonedDateTime now1 = ZonedDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES)
                .withZoneSameInstant((ZoneOffset.UTC).normalized());

        EntropyDto noiseDto1 = new EntropyDto("030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0", 60000, "1", now1.toString());

        EntropyDto noiseDto2 = new EntropyDto("0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3", 60000, "3", now1.toString());

        regularNoises.add(noiseDto1);
        regularNoises.add(noiseDto2);

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, 1, 3, recordLastDto, CombinationEnum.XOR);
        CombineDomainResult combineDomainResult = combineDomainService.processar();

        List<ProcessingErrorDto> combineErrorList = combineDomainResult.getCombineErrorList();

        // teste de erro
        assertEquals("1;3", combineErrorList.get(0).getUsedOrDiscardedFonts());
    }

    @Test
    public void deveDescartarPulsosAntigos(){
        List<EntropyDto> regularNoises = new ArrayList<>();

        PulseDto recordLastDto = new PulseDto();
        ZonedDateTime nowLast = ZonedDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES).plus(1, ChronoUnit.MINUTES).withZoneSameInstant((ZoneOffset.UTC).normalized());

        recordLastDto.setTimeStamp(nowLast);

        // number one
        ZonedDateTime now1 = ZonedDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES).withZoneSameInstant((ZoneOffset.UTC).normalized());
        EntropyDto noiseDto1 = new EntropyDto("030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0", 60000, "1", now1.toString());

        // number one
        EntropyDto noiseDto2 = new EntropyDto("0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3", 60000, "2", now1.toString());

        // number two
        EntropyDto noiseDto3 = new EntropyDto("37ef5148d07725133f32d5c7b13f07fd302a75a107764af727c0494a6e2e220e0a3a8b289eaf8e27964b95441ca642add63a392b1e40186608f4b05574518468", 60000, "1",
                now1.plus(1, ChronoUnit.MINUTES).toString());

        // number three
        EntropyDto noiseDto4 = new EntropyDto("a22c523d3ff106edb8c50e27281a25017d9af44ae7243aa0f529dbe7395f7e0b204178d8a987fe402b153ef3ad388158d2343f7fbbaeff014bb3c1be5b11651b", 60000, "1",
                now1.plus(4, ChronoUnit.MINUTES).toString());

        // number three
        EntropyDto noiseDto5 = new EntropyDto("9fa00ee919ce93c40e6ef83c2fdf499f257037101a36aa682119920130b23f3a45bb7f17890ba8af2c4316a758148ee5c6a5afb2aafd2b625407438bb177e30d", 60000, "2",
                now1.plus(4, ChronoUnit.MINUTES).toString());

        regularNoises.add(noiseDto1);
        regularNoises.add(noiseDto2);
        regularNoises.add(noiseDto3);
        regularNoises.add(noiseDto4);
        regularNoises.add(noiseDto5);

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, 1, 3, recordLastDto, CombinationEnum.XOR);
        CombineDomainResult combineDomainResult = combineDomainService.processar();

        List<LocalRandomValueDto> recordSimpleDtoList = combineDomainResult.getLocalRandomValueDtos();
        List<ProcessingErrorDto> processingErrorList = combineDomainResult.getCombineErrorList();

        assertEquals(1, recordSimpleDtoList.size());
        assertEquals(3, processingErrorList.size());
    }

    @Test
    public void deveConcaternarUmValor(){
        List<EntropyDto> list = new ArrayList<>();
        list.add(new EntropyDto("030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0",
                60000, "1", "2019-07-30T18:22:00.000Z"));

        CombineDomainService combineDomainService = new CombineDomainService(list, 1, 2, null, CombinationEnum.CONCAT);
        CombineDomainResult combineDomainResult = combineDomainService.processar();

        List<LocalRandomValueDto> recordSimpleDtoList = combineDomainResult.getLocalRandomValueDtos();

        String esperado = "320a63cb39a3bfd6e435e5fc8c082fd0000894a6af016c1128c2c9f8929cc427df16a2231161e2e57321dde1b5986ed8b8cb5fe85a285fb1a47312a4317fcefe";

        assertEquals(esperado, recordSimpleDtoList.get(0).getValue());
    }

    @Test
    public void deveConcaternarDoisValores(){
        List<EntropyDto> list = new ArrayList<>();
        list.add(new EntropyDto("030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0",
                60000, "1", "2019-07-30T18:22:00.000Z"));
        list.add(new EntropyDto("0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3",
                60000, "1", "2019-07-30T18:22:00.000Z"));

        CombineDomainService combineDomainService = new CombineDomainService(list, 1, 2, null, CombinationEnum.CONCAT);
        CombineDomainResult combineDomainResult = combineDomainService.processar();

        List<LocalRandomValueDto> recordSimpleDtoList = combineDomainResult.getLocalRandomValueDtos();

        String esperado = "c0499af23e2938cdc6e481d88787941230aeacc8e911861245324bdf86ade84dde20b808c372dd038189f6e5bcf8944c644cf1c7a2aabb978f38f1841753b4fc";

        assertEquals(esperado, recordSimpleDtoList.get(0).getValue());
    }

    @Test
    public void deveConcaternarTresValores(){
        List<EntropyDto> list = new ArrayList<>();
        list.add(new EntropyDto("030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0",
                60000, "1", "2019-07-30T18:22:00.000Z"));
        list.add(new EntropyDto("0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3",
                60000, "1", "2019-07-30T18:22:00.000Z"));
        list.add(new EntropyDto("a22c523d3ff106edb8c50e27281a25017d9af44ae7243aa0f529dbe7395f7e0b204178d8a987fe402b153ef3ad388158d2343f7fbbaeff014bb3c1be5b11651b",
                60000, "1", "2019-07-30T18:22:00.000Z"));

        CombineDomainService combineDomainService = new CombineDomainService(list, 1, 3, null, CombinationEnum.CONCAT);
        CombineDomainResult combineDomainResult = combineDomainService.processar();

        List<LocalRandomValueDto> recordSimpleDtoList = combineDomainResult.getLocalRandomValueDtos();

        String esperado = "9e364ae811b6ab4cd257dbac2c44a823757ff5e44f042b2b1c8b6e878455e01fc7fa150b020dde2c406f3e216607c008c090382a8c25aa7935f920832f0c5b09";

        assertEquals(esperado, recordSimpleDtoList.get(0).getValue());
    }

    @Test
    public void deveOrdenarPorTimestampAndNumeroDaFonte(){
        List<EntropyDto> list = new ArrayList<>();

        list.add(new EntropyDto("030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0",
                60000, "3-Data Center RC", "2019-07-30T18:22:00.000Z"));
        list.add(new EntropyDto("0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3",
                60000, "1-Data Center Xerem", "2019-07-30T18:22:00.000Z"));
        list.add(new EntropyDto("0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3",
                60000, "2-LAINF", "2019-07-30T18:22:00.000Z"));

        CombineDomainService combineDomainService = new CombineDomainService(list, 1, 3, null, CombinationEnum.CONCAT);
        CombineDomainResult combineDomainResult = combineDomainService.processar();

        List<LocalRandomValueDto> recordSimpleDtoList = combineDomainResult.getLocalRandomValueDtos();
        String esperado = "63895db04e007b0ea8076c3b8a2039fcdecc88acde3ac59b270c4c928973aca6f720e28570f9f5ff1a9bdaa2d833825de9e814dc15594507057c73708c9df0ae";

        assertEquals(esperado, recordSimpleDtoList.get(0).getValue());
    }

}