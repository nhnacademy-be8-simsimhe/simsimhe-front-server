package com.simsimbookstore.frontserver.reviews.review.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDTO {


    private int score;

    private String title;

    private String content;
}
