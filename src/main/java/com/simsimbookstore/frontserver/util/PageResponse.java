package com.simsimbookstore.frontserver.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;


/**
 * 페이지 객체는 여기로 반환되서 넘어옴
 * @param <T>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponse<T> {

    @Setter
    private List<T> data;
    private int currentPage;
    private int startPage;
    private int endPage;
    private int totalPage;
    private Long totalElements;

}
