package com.simsimbookstore.frontserver.reviews.review.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {


    private Long reviewId;
    private String title;
    private String content;
    private int score;
    private Long userId;
    private Long bookId;


}
