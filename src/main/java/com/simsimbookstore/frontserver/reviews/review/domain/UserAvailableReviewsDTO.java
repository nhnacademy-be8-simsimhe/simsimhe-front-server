package com.simsimbookstore.frontserver.reviews.review.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserAvailableReviewsDTO {
    private Long bookId;
    private String title;
    private String contributor;
    private String bookImagePath;
    private LocalDateTime orderDate;

}
