package com.simsimbookstore.frontserver.reviews.reviewcomment.controller;


import com.simsimbookstore.frontserver.reviews.reviewcomment.domain.ReviewComment;
import com.simsimbookstore.frontserver.reviews.reviewcomment.domain.ReviewCommentRequestDTO;
import com.simsimbookstore.frontserver.reviews.reviewcomment.feign.ReviewCommentServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
            @PathVariable Long reviewId,
            @RequestParam(required = false) Long userId,
            @ModelAttribute ReviewCommentRequestDTO requestDTO
    ){

        reviewServiceClient.createReviewComment(reviewId, 1L, requestDTO);

        return "redirect:/reviews/"+reviewId+"/comments";
    }


//    @PostMapping
//    public String updateReviewComment(
//            @PathVariable Long reviewId,
//            @PathVariable Long commentId,
//            @RequestBody ReviewCommentRequestDTO requestDTO
//    ){
//        reviewServiceClient.updateReviewComment(commentId, requestDTO);
//
//        return "/review/reviewList";
//    }
//
//
//
//
    @DeleteMapping("/{commentId}")
    public String deleteReviewComment(
            @PathVariable Long reviewId,
            @PathVariable Long commentId
    ){
        reviewServiceClient.deleteReviewComment(reviewId, commentId);

        return "redirect:/reviews/"+reviewId+"/comments";
    }

    @GetMapping
    public String getReviewAllComments(@PathVariable Long reviewId, Model model){
        Page<ReviewComment> comments = reviewServiceClient.getReviewComments(reviewId, 0 , 10);
        model.addAttribute("size", comments.get().count());
        model.addAttribute("reviewId", reviewId);
        model.addAttribute("comments", comments);

        return "/reviews/comments";
    }

}
