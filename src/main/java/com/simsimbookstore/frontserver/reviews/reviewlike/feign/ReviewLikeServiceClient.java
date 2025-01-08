package com.simsimbookstore.frontserver.reviews.reviewlike.feign;


import com.simsimbookstore.frontserver.reviews.review.domain.Review;
import com.simsimbookstore.frontserver.reviews.review.domain.ReviewResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "reviewLikeApi", url = "http://localhost:8000/api/shop")
public interface ReviewLikeServiceClient {

    // 리뷰 좋아요
    @PostMapping("/reviews/{reviewId}/likes")
    public ReviewResponseDTO createReviewLike(@PathVariable Long reviewId, @RequestParam Long userId);

    @DeleteMapping("/reviews/{reviewId}/likes")
    public Review deleteReviewLike(@PathVariable Long reviewId,@RequestParam Long userId);

    @GetMapping("/reviews/{reviewId}/likes")
    public Page<Review> getAllReviewLikes(@PathVariable Long reviewId, @RequestParam int page, @RequestParam int size);

    @GetMapping("/reviews/{reviewId}/likes/count")
    public long getReviewLikesCount(@PathVariable Long reviewId);

}
