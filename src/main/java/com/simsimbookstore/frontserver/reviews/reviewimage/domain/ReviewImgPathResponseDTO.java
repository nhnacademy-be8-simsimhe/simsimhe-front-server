package com.simsimbookstore.frontserver.reviews.reviewimage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ReviewImgPathResponseDTO {

    private Long reviewImagePathId;

    private String imageName;

    private Long reviewId;
}
