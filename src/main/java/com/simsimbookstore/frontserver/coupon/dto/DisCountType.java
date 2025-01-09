package com.simsimbookstore.frontserver.coupon.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DisCountType {
    RATE("정률"),
    FIX("정액");

    private final String name;

    DisCountType(String name) {this.name = name;}

    // 역직렬화 : JSON -> Enum
    @JsonCreator
    public static DisCountType from(String value) {
        for (DisCountType disCountType : values()) {
            if (disCountType.name.equals(value)) {
                return disCountType;
            }
        }
        return FIX;
    }
    // 직렬화 : Enum -> JSON
    @JsonValue
    public String getName() {
        return name;
    }
}
