package com.simsimbookstore.frontserver.coupon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PageResponseDto<T> {
    @JsonProperty("totalPages")
    private int totalPages;

    @JsonProperty("totalElements")
    private long totalElements;

    @JsonProperty("last")
    private boolean last;

    @JsonProperty("size")
    private int size;

    @JsonProperty("number")
    private int number;

    @JsonProperty("numberOfElements")
    private int numberOfElements;

    @JsonProperty("first")
    private boolean first;

    @JsonProperty("empty")
    private boolean empty;

    @JsonProperty("content")
    private List<T> content;

}
