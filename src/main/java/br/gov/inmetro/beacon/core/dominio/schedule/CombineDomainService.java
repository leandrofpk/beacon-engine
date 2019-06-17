package br.gov.inmetro.beacon.core.dominio.schedule;

import br.gov.inmetro.beacon.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.infra.ProcessingErrorTypeEnum;
import br.gov.inmetro.beacon.queue.EntropyDto;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CombineDomainService {

    private List<EntropyDto> regularNoisesChainOne;

    private final int qtdFontes;

    private final EntropyDto lastPersistentNumber;

    private List<RecordSimpleDto> recordSimpleDtoList = new ArrayList<>();

    private List<ProcessingErrorDto> combineErrorList = new ArrayList<>();

    public CombineDomainService(List<EntropyDto> regularNoises, int qtdFontes, EntropyDto lastPersistentNumber) {
        this.regularNoisesChainOne = regularNoises;
        this.qtdFontes = qtdFontes;
        this.lastPersistentNumber = lastPersistentNumber;
    }

    public void processar(){
        Map<Long, List<EntropyDto>> collect = regularNoisesChainOne.stream().collect(Collectors.groupingBy(EntropyDto::getTimeStamp));

        collect.forEach((key, value) -> {

            final String sources = value.stream()
                    .map(EntropyDto::getNoiseSource)
                    .collect(Collectors.joining(";"));

//             discarded number
            if (lastPersistentNumber != null){
                if (key.longValue() <= lastPersistentNumber.getTimeStamp()){
                    this.combineErrorList.add(new ProcessingErrorDto(key.longValue(), qtdFontes, sources, "1", LocalDateTime.now(), ProcessingErrorTypeEnum.DISCARDED_NUMBER));
                    return;
                }
            }

            List<String> listRawData = new ArrayList<>();
            value.forEach(noiseDto -> listRawData.add(noiseDto.getRawData()));

            this.recordSimpleDtoList.add(new RecordSimpleDto(key.toString(), combine(listRawData), "1"));

            // combining errors
            if (value.size() != qtdFontes){
//                final String sources = value.stream()
//                        .map(EntropyDto::getNoiseSource)
//                        .collect(Collectors.joining(";"));
                this.combineErrorList.add(new ProcessingErrorDto(key.longValue(), qtdFontes, sources, "1", LocalDateTime.now(), ProcessingErrorTypeEnum.COMBINING));
            }

        });

//        this.combineErrorList.forEach(processingErrorDto -> recordSimpleDtoList.remove(processingErrorDto));
        this.recordSimpleDtoList.sort(Comparator.comparing(RecordSimpleDto::getTimeStamp));
        this.combineErrorList.sort(Comparator.comparing(ProcessingErrorDto::getTimestamp));
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

    public List<ProcessingErrorDto> getCombineErrorList() {
        return this.combineErrorList;
    }
}
