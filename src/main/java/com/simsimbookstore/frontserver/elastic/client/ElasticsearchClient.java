package com.simsimbookstore.frontserver.elastic.client;


import com.simsimbookstore.frontserver.elastic.dto.SearchBookDto;
import com.simsimbookstore.frontserver.reviews.review.domain.Review;
import com.simsimbookstore.frontserver.reviews.review.domain.ReviewLikeCountDTO;
import com.simsimbookstore.frontserver.reviews.review.domain.ReviewRequestDTO;
import com.simsimbookstore.frontserver.reviews.review.domain.ReviewResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "elasticsearch-api-server", url = "http://localhost:8000/api/shop/elastic")
public interface ElasticsearchClient {

    @PostMapping("/document")
    public void saveData(@RequestBody SearchBookDto searchBookDto);

    @GetMapping("/document")
    public List<SearchBookDto> getDatas(@RequestParam String word);

    @DeleteMapping("/document/{id}")
    public void deleteData(@PathVariable String id);



}
