package com.simsimbookstore.frontserver.reviews.reviewlike.controller;


import com.simsimbookstore.frontserver.reviews.reviewlike.feign.ReviewLikeServiceClient;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewLikeController {

    private final ReviewLikeServiceClient reviewServiceClient;

    @PostMapping("/reviews/{reviewId}/likes")
    public ResponseEntity<Long> createReviewLikes(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long reviewId, @RequestParam(required = false) Long userId){
        if (customUserDetails == null){
            return ResponseEntity.status(HttpStatus.FOUND) // 302 리디렉션 상태 코드
                    .location(URI.create("/login")) // 리디렉션 URL
                    .build();
        }
        reviewServiceClient.createReviewLike(reviewId, customUserDetails.getUserId());
        return ResponseEntity.ok(reviewServiceClient.getReviewLikesCount(reviewId));
    }



    @DeleteMapping("/reviews/{reviewId}/likes")
    public ResponseEntity<Long> deleteReviewLikes(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable Long reviewId, @RequestParam(required = false) Long userId){
        reviewServiceClient.deleteReviewLike(reviewId, customUserDetails.getUserId());
        long count = reviewServiceClient.getReviewLikesCount(reviewId);

        return ResponseEntity.ok(count);
    }
}
