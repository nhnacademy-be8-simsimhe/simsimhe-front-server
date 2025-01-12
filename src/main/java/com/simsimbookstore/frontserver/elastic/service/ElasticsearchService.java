package com.simsimbookstore.frontserver.elastic.service;

import com.simsimbookstore.frontserver.elastic.client.ElasticsearchClient;
import com.simsimbookstore.frontserver.elastic.dto.SearchBookDto;
import com.simsimbookstore.frontserver.reviews.review.domain.ReviewLikeCountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ElasticsearchService {

    private final ElasticsearchClient elasticsearchClient;


    // 생성한 날짜대로 리뷰 조회
    public List<SearchBookDto> getSearchData(String word){
        return elasticsearchClient.getDatas(word);
    };

}
