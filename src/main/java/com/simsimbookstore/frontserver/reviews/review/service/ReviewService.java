package com.simsimbookstore.frontserver.reviews.review.service;

import com.simsimbookstore.frontserver.reviews.review.domain.ReviewLikeCountDTO;
import com.simsimbookstore.frontserver.reviews.review.feign.ReviewServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewServiceClient reviewClient;


    // 생성한 날짜대로 리뷰 조회
    public Page<ReviewLikeCountDTO> getAllReviewsOrderByRecent(Long bookId, Long userId, int page, int size, String sort){
        return reviewClient.getAllReviewsOrderByRecent(bookId,userId, page, size, sort);
    };



}
