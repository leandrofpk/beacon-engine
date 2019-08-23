package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import br.gov.inmetro.beacon.v1.application.api.LocalRandomValueDto;
import br.gov.inmetro.beacon.v2.mypackage.application.PulseDto;
import br.gov.inmetro.beacon.v2.mypackage.queue.EntropyDto;
import org.junit.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CombineDomainServiceTest {

    @Test
    public void testeCombinacaoDoisValores(){
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

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, 1, 2, recordLastDto);
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
    public void testeCombinacaoTresValores(){
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

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, 1L, 3, recordLastDto);
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
    public void deveRetornarErroDeDuasFontes(){
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

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, 1, 3, recordLastDto);
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

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, 1, 3, recordLastDto);
        CombineDomainResult combineDomainResult = combineDomainService.processar();

        List<LocalRandomValueDto> recordSimpleDtoList = combineDomainResult.getLocalRandomValueDtos();
        List<ProcessingErrorDto> processingErrorList = combineDomainResult.getCombineErrorList();

        assertEquals(1, recordSimpleDtoList.size());
        assertEquals(3, processingErrorList.size());
    }

    @Test
    public void shouldSetPrecommitmentValue(){
        List<EntropyDto> list = new ArrayList<>();
        list.add(new EntropyDto("030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0",
                60000, "1", "2019-07-30T18:22:00.000Z"));
        list.add(new EntropyDto("0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3",
                60000, "1", "2019-07-30T18:23:00.000Z"));
        list.add(new EntropyDto("37ef5148d07725133f32d5c7b13f07fd302a75a107764af727c0494a6e2e220e0a3a8b289eaf8e27964b95441ca642add63a392b1e40186608f4b05574518468",
                60000, "1", "2019-07-30T18:24:00.000Z"));


        CombineDomainService combineDomainService = new CombineDomainService(list, 1, 3, null);
        CombineDomainResult combineDomainResult = combineDomainService.processar();

        List<LocalRandomValueDto> recordSimpleDtoList = combineDomainResult.getLocalRandomValueDtos();
    }

//
//    @Test
//    public void testarDuasFontes(){
//        // number one
//        EntropyDto noiseDto1 = new EntropyDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
//                "030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0", "1", "60", "1");
//
//        // number one
//        EntropyDto noiseDto2 = new EntropyDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
//                "0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3", "1", "60", "2");
//
//        // number one
//        EntropyDto noiseDto3 = new EntropyDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
//                "37ef5148d07725133f32d5c7b13f07fd302a75a107764af727c0494a6e2e220e0a3a8b289eaf8e27964b95441ca642add63a392b1e40186608f4b05574518468", "2", "60", "1");
//
//        // number one
//        EntropyDto noiseDto4 = new EntropyDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
//                "a22c523d3ff106edb8c50e27281a25017d9af44ae7243aa0f529dbe7395f7e0b204178d8a987fe402b153ef3ad388158d2343f7fbbaeff014bb3c1be5b11651b", "2", "60", "2");
//
//        // number two
//        EntropyDto noiseDto5 = new EntropyDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plus(1, ChronoUnit.MINUTES),
//                "37ef5148d07725133f32d5c7b13f07fd302a75a107764af727c0494a6e2e220e0a3a8b289eaf8e27964b95441ca642add63a392b1e40186608f4b05574518468", "1", "60", "1");
//
//        // number two
//        EntropyDto noiseDto6 = new EntropyDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plus(1, ChronoUnit.MINUTES),
//                "a22c523d3ff106edb8c50e27281a25017d9af44ae7243aa0f529dbe7395f7e0b204178d8a987fe402b153ef3ad388158d2343f7fbbaeff014bb3c1be5b11651b", "1", "60", "2");
//
//        // number two
//        EntropyDto noiseDto7 = new EntropyDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plus(1, ChronoUnit.MINUTES),
//                "accb69840c2426135ff2085ab413d0c7fc3be4b0f9d0d852e0abea8add5c322c00d23dea93bb6c4490445a98e5ecf447a737e1e63235ec0a308afd46ae86eccd", "2", "60", "2");
//
//        // should be discarded in font one
//        EntropyDto noiseDto8 = new EntropyDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minus(1, ChronoUnit.MINUTES),
//                "accb69840c2426135ff2085ab413d0c7fc3be4b0f9d0d852e0abea8add5c322c00d23dea93bb6c4490445a98e5ecf447a737e1e63235ec0a308afd46ae86eccd", "2", "60", "1");
//
//        // should be discarded in font one
//        EntropyDto noiseDto9 = new EntropyDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minus(1, ChronoUnit.MINUTES),
//                "accb69840c2426135ff2085ab413d0c7fc3be4b0f9d0d852e0abea8add5c322c00d23dea93bb6c4490445a98e5ecf447a737e1e63235ec0a308afd46ae86eccd", "2", "60", "2");
//
//        PulseDto recordLastDto = new PulseDto();
//        recordLastDto.setTimeStamp(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minus(1, ChronoUnit.MINUTES).atZone(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli());
//
//        List<EntropyDto> regularNoises = new ArrayList<>();
//        regularNoises.add(noiseDto1);
//        regularNoises.add(noiseDto2);
//        regularNoises.add(noiseDto3);
//        regularNoises.add(noiseDto4);
//        regularNoises.add(noiseDto5);
//        regularNoises.add(noiseDto6);
//        regularNoises.add(noiseDto7);
//        regularNoises.add(noiseDto8);
//        regularNoises.add(noiseDto9);
//
//        CombineDomainService combineDomainService1 = new CombineDomainService(regularNoises, "1", 2, recordLastDto);
//        combineDomainService1.processar();
//
//        CombineDomainService combineDomainService2 = new CombineDomainService(regularNoises, "2", 2, recordLastDto);
//        combineDomainService2.processar();
//
//        List<LocalRandomValueDto> recordSimpleDtoList1 = combineDomainService1.getLocalRandomValuesDto();
//        List<ProcessingErrorDto> combineErrorList1 = combineDomainService1.getCombineErrorList();
//
//        List<LocalRandomValueDto> recordSimpleDtoList2 = combineDomainService2.getLocalRandomValuesDto();
//        List<ProcessingErrorDto> combineErrorList2 = combineDomainService2.getCombineErrorList();
//
//        assertEquals(2, recordSimpleDtoList1.size());
//        assertEquals(0, combineErrorList1.size());
//
//        assertEquals(2, recordSimpleDtoList2.size());
//        assertEquals(2, combineErrorList2.size());
//
//        assertEquals("1;2", combineErrorList2.get(0).getUsedOrDiscardedFonts());
//        assertEquals(ProcessingErrorTypeEnum.DISCARDED_NUMBER, combineErrorList2.get(0).getProcessingErrorTypeEnum());
//
//        assertEquals("2", combineErrorList2.get(1).getUsedOrDiscardedFonts());
//        assertEquals(ProcessingErrorTypeEnum.COMBINING, combineErrorList2.get(1).getProcessingErrorTypeEnum());
//    }
//

}