package com.simsimbookstore.frontserver.book.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookListResponse {
    private int bookId;
    private String title;
    private String publicationDate;
    private double price;
    private double saleprice;
    private String publisher;
    private String bookStatus;
    private int quantity;
    private Object bookLikeId; // null일 수 있으므로 Object로 설정
    private List<Contributor> contributorList;
    private boolean liked;

    // Getters and Setters
}
