package com.simsimbookstore.frontserver.reviews.review.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewLikeCountDTO {
    private Long reviewId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String userName;
    private int score;
    private long likeCount;
    private long commentCount;
    private List<String> imagePaths;
    private boolean editable;
    private boolean deletable;
    private boolean userLiked;


}
