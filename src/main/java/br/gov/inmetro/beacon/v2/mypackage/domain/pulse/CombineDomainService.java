package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import br.gov.inmetro.beacon.v1.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.v1.infra.ProcessingErrorTypeEnum;
import br.gov.inmetro.beacon.v2.mypackage.application.PulseDto;
import br.gov.inmetro.beacon.v2.mypackage.queue.EntropyDto;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CombineDomainService {

    private List<EntropyDto> regularNoisesChainOne;
    private final Long chain;
    private final int numberOfSources;
    private final PulseDto lastPulseDto;
    private List<RecordSimpleDto> recordSimpleDtoList = new ArrayList<>();
    private List<ProcessingErrorDto> combineErrorList = new ArrayList<>();

    public CombineDomainService(List<EntropyDto> regularNoises, long chain, int numberOfSources,  PulseDto lastRecordDto) {
        this.regularNoisesChainOne = regularNoises;
        this.chain = chain;
        this.numberOfSources = numberOfSources;
        this.lastPulseDto = lastRecordDto;
    }

    public void processar(){
        Map<ZonedDateTime, List<EntropyDto>> collect = regularNoisesChainOne
                .stream()
//                .filter(entropyDto -> entropyDto.getChain().equals(chain))
                .collect(Collectors.groupingBy(EntropyDto::getTimeStamp));


//        Map<ZonedDateTime, List<EntropyDto>> collect = null;


        collect.forEach((key, value) -> {

            final String sources = value.stream()
                    .map(EntropyDto::getNoiseSource)
                    .collect(Collectors.joining(";"));

//             discarded number
            if (lastPulseDto != null){
                if (key.isBefore(lastPulseDto.getTimeStamp())){
                    this.combineErrorList.add(new ProcessingErrorDto(key, numberOfSources, sources, chain.toString(),
                            ZonedDateTime.now().withZoneSameInstant((ZoneOffset.UTC).normalized()), ProcessingErrorTypeEnum.DISCARDED_NUMBER));
                    return;
                }
            }

            List<String> listRawData = new ArrayList<>();
            value.forEach(noiseDto -> listRawData.add(noiseDto.getRawData()));

            this.recordSimpleDtoList.add(new RecordSimpleDto(key.toString(), combine(listRawData), chain.toString()));

            // combining errors
            if (value.size() != numberOfSources){
                this.combineErrorList.add(new ProcessingErrorDto(key, numberOfSources, sources, chain.toString(), ZonedDateTime.now().withZoneSameInstant((ZoneOffset.UTC).normalized()), ProcessingErrorTypeEnum.COMBINING));
            }

        });

        this.recordSimpleDtoList.sort(Comparator.comparing(RecordSimpleDto::getTimeStamp));
        this.combineErrorList.sort(Comparator.comparing(ProcessingErrorDto::getTimeStamp));
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
