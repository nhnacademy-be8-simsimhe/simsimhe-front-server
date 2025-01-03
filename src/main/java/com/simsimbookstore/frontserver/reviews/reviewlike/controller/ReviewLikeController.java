package com.simsimbookstore.frontserver.reviews.reviewlike.controller;


import com.simsimbookstore.frontserver.reviews.reviewlike.feign.ReviewLikeServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewLikeController {

    private final ReviewLikeServiceClient reviewServiceClient;

    @PostMapping("/reviews/{reviewId}/likes")
    public ResponseEntity<?> createReviewLikes(@PathVariable Long reviewId, @RequestParam(required = false) Long userId){
        reviewServiceClient.createReviewLike(reviewId, 1L);
        return ResponseEntity.ok("Like added successfully");
    }



    @DeleteMapping("/reviews/{reviewId}/likes")
    public ResponseEntity<?> deleteReviewLikes(@PathVariable Long reviewId, @RequestParam(required = false) Long userId){
        reviewServiceClient.deleteReviewLike(reviewId, 1L);
        return ResponseEntity.ok("Like removed successfully");
    }
}
