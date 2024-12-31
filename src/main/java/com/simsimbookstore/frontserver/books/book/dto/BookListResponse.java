package com.simsimbookstore.frontserver.books.book.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookListResponse {

    private Long bookId;

    private String imagePath;

    private String title;

    private LocalDate publicationDate;

    private BigDecimal price;

    private BigDecimal saleprice;

    private String publisher;

    private BookStatus bookStatus;

    private int quantity;

    private Long bookLikeId;

    private boolean isLiked;

    private boolean giftPackaging;

    private List<BookContributorResponsDto> contributorList;


    @Getter
    public enum BookStatus {
        ONSALE("정상판매"),
        SOLDOUT("매진"),
        OUTOFPRINT("절판"),
        DELETED("삭제");

        private final String status;


        BookStatus(String status) {
            this.status = status;
        }


    }


}


