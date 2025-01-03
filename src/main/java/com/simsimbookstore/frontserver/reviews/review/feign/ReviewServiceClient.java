package com.simsimbookstore.frontserver.reviews.review.feign;


import com.simsimbookstore.frontserver.reviews.review.domain.Review;
import com.simsimbookstore.frontserver.reviews.review.domain.ReviewLikeCountDTO;
import com.simsimbookstore.frontserver.reviews.review.domain.ReviewRequestDTO;
import com.simsimbookstore.frontserver.reviews.review.domain.ReviewResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "reviewApi", url = "http://localhost:8020/api/books")
public interface ReviewServiceClient {

    // 리뷰 등록 요청
    @PostMapping("/{bookId}/reviews")
    public ReviewResponseDTO createReview(@PathVariable Long bookId, @RequestParam Long userId, @RequestBody ReviewRequestDTO reviewRequestDTO);

    @GetMapping("/{bookId}/reviews/{reviewId}")
    public Review getReviewById(@PathVariable Long bookId,  @PathVariable Long reviewId);

    @GetMapping("/{bookId}/reviews")
    public Page<Review> getAllReviews(@PathVariable Long bookId, @RequestParam int page, @RequestParam int size);

    @GetMapping("/{bookId}/reviews/score")
    public Page<Review> getAllReviewsOrderByScore(@PathVariable Long bookId, @RequestParam int page, @RequestParam int size);

    @GetMapping("/{bookId}/reviews/like")
    public Page<Review> getAllReviewsOrderByLike(@PathVariable Long bookId, @RequestParam int page, @RequestParam int size);

    @GetMapping("/{bookId}/reviews/recent")
    public Page<ReviewLikeCountDTO> getAllReviewsOrderByRecent(@PathVariable Long bookId, @RequestParam int page, @RequestParam int size);

    @PostMapping("/{bookId}/reviews/{reviewId}")
    public Review updateReview(@PathVariable Long bookId, @PathVariable Long reviewId, @RequestBody ReviewRequestDTO reviewRequestDTO);
//
    @DeleteMapping("/{bookId}/reviews/{reviewId}")
    public void deleteReview(@PathVariable Long bookId, @PathVariable Long reviewId);
}
