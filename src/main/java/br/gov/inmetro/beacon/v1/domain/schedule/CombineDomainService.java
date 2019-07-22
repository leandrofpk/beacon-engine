package br.gov.inmetro.beacon.v1.domain.schedule;

import br.gov.inmetro.beacon.queue.EntropyDto;
import br.gov.inmetro.beacon.v1.application.api.PulseDto;
import br.gov.inmetro.beacon.v1.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.v1.infra.ProcessingErrorTypeEnum;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CombineDomainService {

    private List<EntropyDto> regularNoisesChainOne;
    private final String chain;
    private final int numberOfSources;
    private final PulseDto lastRecordDto;
    private List<RecordSimpleDto> recordSimpleDtoList = new ArrayList<>();
    private List<ProcessingErrorDto> combineErrorList = new ArrayList<>();

    public CombineDomainService(List<EntropyDto> regularNoises, String chain, int numberOfSources,  PulseDto lastRecordDto) {
        this.regularNoisesChainOne = regularNoises;
        this.chain = chain;
        this.numberOfSources = numberOfSources;

        this.lastRecordDto = lastRecordDto;
    }

    public void processar(){
        Map<Long, List<EntropyDto>> collect = regularNoisesChainOne
                .stream()
                .filter(entropyDto -> entropyDto.getChain().equals(chain))
                .collect(Collectors.groupingBy(EntropyDto::getTimeStamp));

        collect.forEach((key, value) -> {

            final String sources = value.stream()
                    .map(EntropyDto::getNoiseSource)
                    .collect(Collectors.joining(";"));

//             discarded number
            if (lastRecordDto != null){
                if (key <= lastRecordDto.getTimeStamp()){
                    this.combineErrorList.add(new ProcessingErrorDto(key, numberOfSources, sources, chain,
                            LocalDateTime.now(), ProcessingErrorTypeEnum.DISCARDED_NUMBER));
                    return;
                }
            }

            List<String> listRawData = new ArrayList<>();
            value.forEach(noiseDto -> listRawData.add(noiseDto.getRawData()));

            this.recordSimpleDtoList.add(new RecordSimpleDto(key.toString(), combine(listRawData), chain));

            // combining errors
            if (value.size() != numberOfSources){
                this.combineErrorList.add(new ProcessingErrorDto(key, numberOfSources, sources, chain, LocalDateTime.now(), ProcessingErrorTypeEnum.COMBINING));
            }

        });

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
