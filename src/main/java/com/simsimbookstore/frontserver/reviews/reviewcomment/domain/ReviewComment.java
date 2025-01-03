package com.simsimbookstore.frontserver.reviews.reviewcomment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewComment {
    private Long reviewCommentId;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    private Long reviewId;

    private Long userId;

    private String userName;

}
