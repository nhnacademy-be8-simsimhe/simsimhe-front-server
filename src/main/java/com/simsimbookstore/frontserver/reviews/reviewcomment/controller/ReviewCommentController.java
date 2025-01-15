package com.simsimbookstore.frontserver.reviews.reviewcomment.controller;


import com.simsimbookstore.frontserver.reviews.reviewcomment.domain.ReviewComment;
import com.simsimbookstore.frontserver.reviews.reviewcomment.domain.ReviewCommentRequestDTO;
import com.simsimbookstore.frontserver.reviews.reviewcomment.feign.ReviewCommentServiceClient;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews/{reviewId}/comments")
public class ReviewCommentController {

    private final ReviewCommentServiceClient reviewServiceClient;
//
//
    @PostMapping
    public String createReviewComment(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long reviewId,
            @ModelAttribute ReviewCommentRequestDTO requestDTO
    ){

        if (customUserDetails == null){
            return "redirect:/login";
        }

        reviewServiceClient.createReviewComment(reviewId, customUserDetails.getUserId(), requestDTO);

        return "redirect:/reviews/"+reviewId+"/comments";
    }


    @PostMapping("/{commentId}")
    public String updateReviewComment(
            @PathVariable Long reviewId,
            @PathVariable Long commentId,
            @RequestBody ReviewCommentRequestDTO requestDTO
    ){
        reviewServiceClient.updateReviewComment(reviewId, commentId, requestDTO);

        return "reviews/reviewList";
    }

    @DeleteMapping("/{commentId}")
    public String deleteReviewComment(
            @PathVariable Long reviewId,
            @PathVariable Long commentId
    ){
        reviewServiceClient.deleteReviewComment(reviewId, commentId);

        return "redirect:/reviews/"+reviewId+"/comments";
    }

    @GetMapping
    public String getReviewAllComments(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long reviewId, Model model){
        Page<ReviewComment> comments = reviewServiceClient.getReviewComments(reviewId, 0 , 10);

        Long loginUserId = ((customUserDetails != null) ? customUserDetails.getUserId() : -1);
        log.info("loginUserId : {}", loginUserId);
        model.addAttribute("size", comments.get().count());
        model.addAttribute("reviewId", reviewId);
        model.addAttribute("comments", comments);
        model.addAttribute("loginUserId", loginUserId);

        return "reviews/comments";
    }

}
