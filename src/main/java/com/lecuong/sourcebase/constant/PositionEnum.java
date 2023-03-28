package com.lecuong.sourcebase.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

/**
 * @author CuongLM18
 * @created 28/03/2023 - 11:18 AM
 * @project source-base
 */
public enum PositionEnum {

    DEVELOPER(0),
    TESTER(1),
    QA(2),
    MANAGER(3),
    BA(4);

    private int value;

    private PositionEnum(int value) {
        this.value = value;
    }

    @JsonCreator
    public static PositionEnum of(int value) {
        return Stream.of(PositionEnum.values())
                .filter(p -> p.value == value)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @JsonValue
    public String getName() {
        return this.toString();
    }

//    @JsonValue
//    public Integer getValue() {
//        return this.value;
//    }
}
