package nl.nuggit.aggregator.model;

import java.util.Arrays;

public enum Ledger {

    RECEIVABLE_INVOICE("R"), PAYABLE_INVOICE("P");

    private final String code;

    Ledger(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Ledger fromCode(String code) {
        return Arrays.stream(Ledger.values()).filter(v -> v.getCode().equals(code)).findAny().orElseThrow(
                () -> new IllegalArgumentException("Relationship not known: " + code));
    }
}
