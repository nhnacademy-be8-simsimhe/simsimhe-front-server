package com.simsimbookstore.frontserver.reviews.review.feign;


import com.simsimbookstore.frontserver.reviews.review.domain.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-review-api-server", url = "http://localhost:8000/api/shop/users")
public interface UserReviewServiceClient {

    @GetMapping("/{userId}/reviews")
    public Page<UserReviewsDTO> getUserReviews(@PathVariable Long userId, @RequestParam int page, @RequestParam int size);

    @GetMapping("/{userId}/reviews/available")
    public Page<UserAvailableReviewsDTO> getEligibleBooksForReview(@PathVariable Long userId, @RequestParam int page, @RequestParam int size);


}
