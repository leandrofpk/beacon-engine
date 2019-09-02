package br.gov.inmetro.beacon.engine.domain.pulse;

import lombok.Getter;

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
