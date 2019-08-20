package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import br.gov.inmetro.beacon.v2.mypackage.infra.ExternalEntity;
import lombok.Getter;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.nio.ByteBuffer;

@Getter
public class External {

    private String sourceId;

    private short statusCode;

    private String value;

    private External(String sourceId, short statusCode, String value) {
        this.sourceId = sourceId;
        this.statusCode = statusCode;
        this.value = value;
    }

    public static External newExternal(){
        return new External("00000000000000000000000000000000000000000000000000000000000000000000000000000000" +
                                     "000000000000000000000000000000000000000000000000",
                                     new Short("0"),
                                "000000000000000000000000000000000000000000000000000000000000000000000000000000" +
                                        "00000000000000000000000000000000000000000000000000");
    }

    public static External newExternalFromEntity(ExternalEntity entity){
        return new External(entity.getSourceId(), entity.getStatusCode(), entity.getValue());
    }

    public byte[] getSourceIdAsByte(){
        return ByteUtils.fromHexString(sourceId);
    }

    public byte[] getValueAsByte(){
        return ByteUtils.fromHexString(value);
    }

    @Override
    public String toString() {
        return "External{" +
                "sourceId='" + sourceId + '\'' +
                ", statusCode=" + statusCode +
                ", value='" + value + '\'' +
                '}';
    }
}
