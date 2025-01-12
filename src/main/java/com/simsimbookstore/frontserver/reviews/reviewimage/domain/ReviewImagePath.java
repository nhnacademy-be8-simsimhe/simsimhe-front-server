package com.simsimbookstore.frontserver.reviews.reviewimage.domain;



import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ReviewImagePath {

    private Long reviewImagePathId;

    private String imageName;

    private Long reviewId;

}
