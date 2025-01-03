package com.simsimbookstore.frontserver.reviews.review.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Review {

    private Long reviewId;
    private int score;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private Long bookId;
    private Long userId;
}
