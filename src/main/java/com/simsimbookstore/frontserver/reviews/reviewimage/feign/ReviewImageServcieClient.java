package com.simsimbookstore.frontserver.reviews.reviewimage.feign;


import com.simsimbookstore.frontserver.reviews.reviewimage.domain.ReviewImagePath;
import com.simsimbookstore.frontserver.reviews.reviewimage.domain.ReviewImgPathResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "reviewImageApi", url = "http://localhost:8000/api/shop/reviews")
public interface ReviewImageServcieClient {

    @PostMapping("/{reviewId}/images")
    public List<ReviewImgPathResponseDTO> addReviewImages(@PathVariable Long reviewId, @RequestParam List<String> imageName);

    @GetMapping("/{reviewId}/images")
    public List<ReviewImagePath> getReviewImages(@PathVariable Long reviewId);


    @DeleteMapping("/{reviewId}/images/{imageId}")
    public ReviewImagePath deleteReviewImages(@PathVariable Long imageId);


}
