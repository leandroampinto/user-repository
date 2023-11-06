package br.com.mediapro.test.userserver.models;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AgeRange {
    RANGE_21_30(21, 30),
    RANGE_31_40(31, 40),
    RANGE_41_60(41, 60),
    RANGE_61_80(61, 80),
    RANGE_OTHER();

    private Integer start;
    private Integer end;

    private AgeRange() {
        this(null, null);
    }

    private AgeRange(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public boolean isIncluded(int age) {
        if (start == null && end == null) {
            if (age < RANGE_21_30.start) {
                return true;
            }
            if (RANGE_61_80.end < age) {
                return true;
            }
            return false;
        }
        return start <= age && age <= end;
    }

    public static AgeRange get(int age) {
        return Arrays.stream(values()).filter(ageRange -> ageRange.isIncluded(age)).findFirst().get();
    }

    @JsonValue
    public String jsonValue() {
        if (start == null && end == null) {
            return "other";
        }
        return String.format("%s_%s", start, end);
    }
}