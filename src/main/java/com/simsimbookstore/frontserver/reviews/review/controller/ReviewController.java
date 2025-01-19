package com.simsimbookstore.frontserver.reviews.review.controller;


import com.simsimbookstore.frontserver.point.client.PointHistoryClient;
import com.simsimbookstore.frontserver.reviews.object.feign.ObjectServiceClient;
import com.simsimbookstore.frontserver.reviews.review.domain.*;
import com.simsimbookstore.frontserver.reviews.review.feign.ReviewServiceClient;
import com.simsimbookstore.frontserver.reviews.review.feign.UserReviewServiceClient;
import com.simsimbookstore.frontserver.reviews.reviewimage.feign.ReviewImageServcieClient;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewServiceClient reviewServiceClient;
    private final ObjectServiceClient objectServiceClient;
    private final ReviewImageServcieClient reviewImageServcieClient;
    private final UserReviewServiceClient userReviewServiceClient;
    private final PointHistoryClient pointHistoryClient;

    @PostMapping("/reviews")
    public String createReview(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam Long bookId, @RequestPart(value = "file", required = false) List<MultipartFile> files, @ModelAttribute ReviewRequestDTO dto){
        log.info("userId = {}", customUserDetails.getUserId() );
        ReviewResponseDTO review = reviewServiceClient.createReview(bookId, customUserDetails.getUserId(), dto);
        log.info("Review created: {}", review);
        if (!files.getFirst().getOriginalFilename().isEmpty()){
            List<String> images = objectServiceClient.uploadObjects(files);
            reviewImageServcieClient.addReviewImages(review.getReviewId(), images);
        }
        Long l = pointHistoryClient.earnReviewPoint(customUserDetails.getUserId(), review.getReviewId());
        log.info("earnpoint: {}", l);
        return "redirect:/";
    }

    @GetMapping("/reviews/create")
    public String createReviewView(@RequestParam Long bookId, Model model){

        model.addAttribute("bookId", bookId);
        return "reviews/review";
    }

    @GetMapping("/users/myPage/reviews")
    public String createReviewListView(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam(required = false) Long bookId, Model model){
        Page<UserAvailableReviewsDTO> availableReviews = userReviewServiceClient.getEligibleBooksForReview(customUserDetails.getUserId(), 0, 10);
        Page<UserReviewsDTO> submittedReviews = userReviewServiceClient.getUserReviews(customUserDetails.getUserId(), 0,10);

        model.addAttribute("availableReviews",availableReviews);
        model.addAttribute("submittedReviews",submittedReviews);
        return "reviews/reviewList";
    }


    @GetMapping("/myPage/reviews")
    public String getMyReviewList(@RequestParam Long bookId, Model model){

        model.addAttribute("bookId", bookId);
        return "reviews/review";
    }


    @GetMapping("/reviews")
    public ResponseEntity<?> getReviewsView(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam Long bookId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "latest") String sort){
        Long loginUserId = customUserDetails != null ? customUserDetails.getUserId() : -1;
        Page<ReviewLikeCountDTO> reviews = reviewServiceClient.getAllReviewsOrderByRecent(bookId, loginUserId, page, size, sort);
        for (ReviewLikeCountDTO review : reviews){
            log.info("review : {}", review);
        }


        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<?> getReviewById(@PathVariable Long reviewId){
        Review review = reviewServiceClient.getReviewById(18L,reviewId);

        return ResponseEntity.ok(review);
    }


    @GetMapping("/reviews/edit/{reviewId}")
    public String updateReviewView(@PathVariable Long reviewId, Model model){

        Review existingReview = reviewServiceClient.getReviewById(18L, reviewId);
        model.addAttribute("review", existingReview);
        return "reviews/reviewUpdate";
    }


    @PostMapping("/reviews/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reviewId, @ModelAttribute ReviewRequestDTO dto){

        reviewServiceClient.updateReview(18L, reviewId,dto);

        return ResponseEntity.ok("updated");
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId){
        log.info("delete review");
        reviewServiceClient.deleteReview(18L, reviewId);

        return ResponseEntity.ok("deleted!");
    }


//    @PostMapping("/reviews/{reviewId}")
//    public String updateReview(@PathVariable Long reviewId,
//                               @ModelAttribute ReviewRequestDTO dto){
//
////        String userId = userDetails.getUsername();
//
//        log.info("title : {}", dto.getTitle());
//        log.info("content : {}", dto.getContent());
//        log.info("score : {}", dto.getScore());
//        reviewServiceClient.createReview(1L, 1L, dto);
//
//        return "redirect:/";
//    }
//
//    @GetMapping("/reviews")
//    public String updateReviewView(){
//
//        return "/reviews/review";
//    }



}
