package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ListValue {

    private String value;

    private String type;

    private String uri;

    private ListValue(@NonNull String value, @NonNull String type, String uri) {
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
