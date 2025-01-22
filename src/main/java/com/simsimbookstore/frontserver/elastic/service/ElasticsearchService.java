package com.simsimbookstore.frontserver.elastic.service;

import com.simsimbookstore.frontserver.elastic.client.ElasticsearchClient;
import com.simsimbookstore.frontserver.elastic.dto.SearchBookDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@RequiredArgsConstructor
@Service
public class ElasticsearchService {

    private final ElasticsearchClient elasticsearchClient;


    // 생성한 날짜대로 리뷰 조회
    public PageResponse<SearchBookDto> getSearchData(String keyword, String sort, int page){
        return elasticsearchClient.getDatas(keyword, sort, page);
    };

}
