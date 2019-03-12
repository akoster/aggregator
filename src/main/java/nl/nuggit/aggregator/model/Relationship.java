package nl.nuggit.aggregator.model;

import java.util.Arrays;

public enum Relationship {

    BUYER("B"), SUPPLIER("S");

    private final String code;

    Relationship(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Relationship fromCode(String code) {
        return Arrays.stream(Relationship.values()).filter(r -> r.getCode().equals(code)).findAny().orElseThrow(
                () -> new IllegalArgumentException("Relationship not known: " + code));
    }
}
