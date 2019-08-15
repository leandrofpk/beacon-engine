package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ListValue {

    private String uri;

    private String type;

    private String value;

    private ListValue(String value, String type, String uri) {
        this.value = value;
        this.type = type;
        this.uri = uri;
    }

    public static ListValue getOneValue(String value, String type, String uri){
        return new ListValue(value, type, uri);
    }

    @Override
    public String toString() {
        return "ListValue{" +
                "value='" + value + '\'' +
                ", type='" + type + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
