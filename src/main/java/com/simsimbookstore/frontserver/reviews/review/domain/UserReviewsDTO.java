package com.simsimbookstore.frontserver.reviews.review.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserReviewsDTO {
    private Long reviewId;
    private Long bookId;
    private String bookTitle;
    private String contributor;
    private String bookImagePath;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String userName;
    private Long userId;
    private int score;
    private long likeCount;
    private long commentCount;
    private List<String> imagePaths;


}
