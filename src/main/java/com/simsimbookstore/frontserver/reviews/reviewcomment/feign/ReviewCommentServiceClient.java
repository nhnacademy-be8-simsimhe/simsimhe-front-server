package com.simsimbookstore.frontserver.reviews.reviewcomment.feign;


import com.simsimbookstore.frontserver.reviews.reviewcomment.domain.ReviewComment;
import com.simsimbookstore.frontserver.reviews.reviewcomment.domain.ReviewCommentRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "reviewCommentApi", url = "http://localhost:8020/api/reviews")
public interface ReviewCommentServiceClient {


    @PostMapping("/{reviewId}/comments")
    public ReviewComment createReviewComment(@PathVariable Long reviewId,
                                             @RequestParam Long userId,
                                             @RequestBody ReviewCommentRequestDTO requestDTO);

    @GetMapping("/{reviewId}/comments")
    public Page<ReviewComment> getReviewComments(@PathVariable Long reviewId, @RequestParam int page, @RequestParam int size);

    @GetMapping("/{reviewId}/comments/{commentId}")
    public Page<ReviewComment> getReviewComment(@PathVariable Long reviewId, @PathVariable Long commentId);

    @PutMapping("/{reviewId}/comments/{commentId}")
    public ReviewComment updateReviewComment(@PathVariable Long reviewId, @PathVariable Long commentId);

    @DeleteMapping("/{reviewId}/comments/{commentId}")
    public void deleteReviewComment(@PathVariable Long reviewId, @PathVariable Long commentId);


//    @PostMapping
//    ReviewComment createReviewComment(@PathVariable Long reviewId, @RequestParam Long userId, @RequestBody ReviewCommentRequestDTO request);
//

//    @PostMapping
//    public ReviewComment updateReviewComment(@PathVariable Long commentId,
//                                             @RequestBody ReviewCommentRequestDTO reviewComment);
//
//
//    @GetMapping
//    public ReviewComment getReviewCommentById(@PathVariable Long commentId);
//
//
//    @GetMapping
//    public ReviewComment getReviewComemntsById(@PathVariable Long bookId, @PathVariable Long reviewId);
//
//    @DeleteMapping
//    public ReviewComment deleteReviewComment(@PathVariable Long commentId);

}
