package br.gov.inmetro.beacon.core.dominio.schedule;

import br.gov.inmetro.beacon.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.queue.NoiseDto;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CombineDomainService {

    private List<NoiseDto> regularNoises;

    private final int qtdFontes;

    private List<RecordSimpleDto> recordSimpleDtoList = new ArrayList<>();

    private List<CombineErroDto> combineErrorList = new ArrayList<>();

    public CombineDomainService(List<NoiseDto> regularNoises, int qtdFontes) {
        this.regularNoises = regularNoises;
        this.qtdFontes = qtdFontes;
    }

    public void processar(){
        Map<Long, List<NoiseDto>> collect = regularNoises.stream().collect(Collectors.groupingBy(NoiseDto::getTimeStamp));

        collect.forEach((key, value) -> {
            List<String> listRawData = new ArrayList<>();
            value.forEach(noiseDto -> listRawData.add(noiseDto.getRawData()));

            this.recordSimpleDtoList.add(new RecordSimpleDto(key.toString(), combine(listRawData)));

            //tratar erros
            if (value.size() != qtdFontes){
                final String sources = value.stream()
                        .map(NoiseDto::getNoiseSource)
                        .collect(Collectors.joining(";"));
                this.combineErrorList.add(new CombineErroDto(key, qtdFontes, sources));
            }

        });

        this.recordSimpleDtoList.sort(Comparator.comparing(RecordSimpleDto::getTimeStamp));
        this.combineErrorList.sort(Comparator.comparing(CombineErroDto::getTimestamp));
    }

    private String combine(List<String> rawDataList) {
        byte[] xor = null;

        if (rawDataList.size() == 1){
            return rawDataList.get(0);
        }

        if (rawDataList.size() > 1){
            xor = ByteUtils.xor(ByteUtils.fromHexString(rawDataList.get(0)), ByteUtils.fromHexString(rawDataList.get(1)));

            for (int i = 2; i < rawDataList.size(); i++) {
                xor = ByteUtils.xor(xor, ByteUtils.fromHexString(rawDataList.get(i)));
            }
        }

        return ByteUtils.toHexString(xor);
    }

    public List<RecordSimpleDto> getRecordSimpleDtoList() {
        return this.recordSimpleDtoList;
    }

    public List<CombineErroDto> getCombineErrorList() {
        return this.combineErrorList;
    }
}
