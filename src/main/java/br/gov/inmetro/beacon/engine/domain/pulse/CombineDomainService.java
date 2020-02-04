package br.gov.inmetro.beacon.engine.domain.pulse;

import br.gov.inmetro.beacon.engine.application.PulseDto;
import br.gov.inmetro.beacon.engine.infra.ProcessingErrorTypeEnum;
import br.gov.inmetro.beacon.engine.queue.EntropyDto;
import br.gov.inmetro.beacon.library.ciphersuite.suite0.CipherSuiteBuilder;
import br.gov.inmetro.beacon.library.ciphersuite.suite0.ICipherSuite;
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

    private CombinationEnum combinationEnum;

    private final ICipherSuite cipherSuite = CipherSuiteBuilder.build(0);

    public CombineDomainService(List<EntropyDto> regularNoises, long chain, int numberOfSources,
                                PulseDto lastRecordDto, CombinationEnum combinationEnum) {
        this.regularNoisesChainOne = regularNoises;
        this.chain = chain;
        this.numberOfSources = numberOfSources;
        this.lastPulseDto = lastRecordDto;
        this.combinationEnum = combinationEnum;
    }

    public CombineDomainResult processar(){
        List<LocalRandomValueDto> localRandomValueDtos = new ArrayList<>();
        List<ProcessingErrorDto> combineErrorList = new ArrayList<>();

        Map<ZonedDateTime, List<EntropyDto>> collect = regularNoisesChainOne
                .stream()
                .sorted((Comparator.comparing(EntropyDto::getNoiseSource)).thenComparing(EntropyDto::getNoiseSource))
                .collect(Collectors.groupingBy(EntropyDto::getTimeStamp));

        collect.forEach((key, value) -> {

            final String sources = value.stream()
                    .map(EntropyDto::getNoiseSource)
                    .collect(Collectors.joining(";"));

//             discarded number
            if (lastPulseDto != null){
                if (key.isBefore(lastPulseDto.getTimeStamp()) || (key.equals(lastPulseDto.getTimeStamp()))){
                    combineErrorList.add(new ProcessingErrorDto(key, numberOfSources, sources, chain.toString(),
                            ZonedDateTime.now().withZoneSameInstant((ZoneOffset.UTC).normalized()), ProcessingErrorTypeEnum.DISCARDED_NUMBER));
                    return;
                }
            }

            List<String> listRawData = new ArrayList<>();
            value.forEach(noiseDto -> listRawData.add(noiseDto.getRawData()));

            if (this.combinationEnum.equals(CombinationEnum.XOR)){
                localRandomValueDtos.add(new LocalRandomValueDto(key, combineXor(listRawData)));
            } else {
                localRandomValueDtos.add(new LocalRandomValueDto(key, combineConcat(listRawData)));
            }

            // combining errors
            if (value.size() != numberOfSources){
                combineErrorList.add(new ProcessingErrorDto(key, numberOfSources, sources, chain.toString(), ZonedDateTime.now().withZoneSameInstant((ZoneOffset.UTC).normalized()), ProcessingErrorTypeEnum.COMBINING));
            }

        });

        localRandomValueDtos.sort(Comparator.comparing(LocalRandomValueDto::getTimeStamp));
        combineErrorList.sort(Comparator.comparing(ProcessingErrorDto::getTimeStamp));

        return new CombineDomainResult(localRandomValueDtos, combineErrorList, numberOfSources);
    }

    private String combineXor(List<String> rawDataList) {
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

    private String combineConcat(List<String> rawDataList) {
        String value = "";
        for (int i = 0; i < rawDataList.size(); i++) {
            value = value + rawDataList.get(i);
        }

        return cipherSuite.getDigest(value);
    }

}
