package com.simsimbookstore.frontserver.elastic.client;


import com.simsimbookstore.frontserver.reviews.review.domain.Review;
import com.simsimbookstore.frontserver.reviews.review.domain.ReviewLikeCountDTO;
import com.simsimbookstore.frontserver.reviews.review.domain.ReviewRequestDTO;
import com.simsimbookstore.frontserver.reviews.review.domain.ReviewResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "elasticsearch-api-server", url = "http://localhost:8000/api/shop/elastic")
public interface ElasticsearchClient {

//    @PostMapping("/document")
//    public void saveData();
//


}
