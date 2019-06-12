package br.gov.inmetro.beacon.core.dominio.schedule;

import br.gov.inmetro.beacon.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.queue.NoiseDto;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CombineDomainServiceTest {

    @Test
    public void testeCombinacaoDoisValores(){
        List<NoiseDto> regularNoises = new ArrayList<>();

        NoiseDto noiseDto1 = new NoiseDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                "030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0", "1", "60", "1");

        NoiseDto noiseDto2 = new NoiseDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                "0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3", "1", "60", "2");

        NoiseDto noiseDto3 = new NoiseDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minus(2, ChronoUnit.MINUTES),
                "37ef5148d07725133f32d5c7b13f07fd302a75a107764af727c0494a6e2e220e0a3a8b289eaf8e27964b95441ca642add63a392b1e40186608f4b05574518468", "1", "60", "1");


        NoiseDto noiseDto4 = new NoiseDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plus(1, ChronoUnit.MINUTES),
                "a22c523d3ff106edb8c50e27281a25017d9af44ae7243aa0f529dbe7395f7e0b204178d8a987fe402b153ef3ad388158d2343f7fbbaeff014bb3c1be5b11651b", "1", "60", "1");

        NoiseDto noiseDto5 = new NoiseDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plus(1, ChronoUnit.MINUTES),
                "9fa00ee919ce93c40e6ef83c2fdf499f257037101a36aa682119920130b23f3a45bb7f17890ba8af2c4316a758148ee5c6a5afb2aafd2b625407438bb177e30d", "1", "60", "2");

        regularNoises.add(noiseDto1);
        regularNoises.add(noiseDto2);
        regularNoises.add(noiseDto3);
        regularNoises.add(noiseDto4);
        regularNoises.add(noiseDto5);

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, 2);
        combineDomainService.processar();

        List<RecordSimpleDto> recordSimpleDtoList = combineDomainService.getRecordSimpleDtoList();
        List<CombineErroDto> combineErrorList = combineDomainService.getCombineErrorList();

        // não precisou combinar
        Assert.assertEquals(noiseDto3.getRawData(), recordSimpleDtoList.get(0).getRawData());
        Assert.assertEquals(noiseDto3.getTimeStamp(), combineErrorList.get(0).getTimestamp());

        // teste de combinação
        assertEquals("3d8c5cd4263f9529b6abf61b07c56c9e58eac35afd1290c8d43049e609ed413165fa07cf208c56ef07562854f52c0fbd149190cd1153d4631fb48235ea668616",
                recordSimpleDtoList.get(2).getRawData());

    }

    @Test
    public void testeCombinacaoTresValores(){
        List<NoiseDto> regularNoises = new ArrayList<>();

        NoiseDto noiseDto1 = new NoiseDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                "030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0", "1", "60", "1");

        NoiseDto noiseDto2 = new NoiseDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                "0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3", "1", "60", "2");

        NoiseDto noiseDto3 = new NoiseDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                "37ef5148d07725133f32d5c7b13f07fd302a75a107764af727c0494a6e2e220e0a3a8b289eaf8e27964b95441ca642add63a392b1e40186608f4b05574518468", "1", "60", "3");

        NoiseDto noiseDto4 = new NoiseDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plus(1, ChronoUnit.MINUTES),
                "a22c523d3ff106edb8c50e27281a25017d9af44ae7243aa0f529dbe7395f7e0b204178d8a987fe402b153ef3ad388158d2343f7fbbaeff014bb3c1be5b11651b", "1", "60", "1");

        regularNoises.add(noiseDto1);
        regularNoises.add(noiseDto2);
        regularNoises.add(noiseDto3);
        regularNoises.add(noiseDto4);

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, 3);
        combineDomainService.processar();

        List<RecordSimpleDto> recordSimpleDtoList = combineDomainService.getRecordSimpleDtoList();

        // teste de combinação
        assertEquals("39ff40c1857eb9b71828ebbc1b4309382a9d54a4a9e4241c7d3641f801653c3b73a84b0b1f375620fb911d6a73a900ed3c0cb7c413cdb785d96309785265ed1b",
                recordSimpleDtoList.get(0).getRawData());
    }

    @Test
    public void deveRetornarErroDeDuasFontes(){
        List<NoiseDto> regularNoises = new ArrayList<>();

        NoiseDto noiseDto1 = new NoiseDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                "030b5d4e297262022390977e0d771762ceffd4ef9a79f7f7a0cb0439a347a46a5558969e8ded74de678f1a4d50e33bf68e5b317cbc523893fc987fca13ea84c0", "1", "60", "1");

        NoiseDto noiseDto2 = new NoiseDto(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                "0d1b4cc77c7bfea6048aa905a70b19a7d448f5ea34eb991cfa3d0c8bcc0cba5f2cca56bd0c75acd90a5592633fec79b6646dbf93b1df97702d0fc6e735deedb3", "1", "60", "3");

        regularNoises.add(noiseDto1);
        regularNoises.add(noiseDto2);

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, 3);
        combineDomainService.processar();

        List<CombineErroDto> combineErrorList = combineDomainService.getCombineErrorList();

        // teste de erro
        assertEquals("1;3", combineErrorList.get(0).getFontesUtilizadas());
    }

    @Test
    public void deveDescartarPulsosAntigos(){
        Assert.fail("Não implementado");
    }


}