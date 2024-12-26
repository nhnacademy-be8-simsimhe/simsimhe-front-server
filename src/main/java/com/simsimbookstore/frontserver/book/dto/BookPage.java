package com.simsimbookstore.frontserver.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookPage {
    List<BookListResponse> data;

    private int currentPage;
    private int startPage;
    private int endPage;
    private int totalPage;
    private Long totalElements;
}
