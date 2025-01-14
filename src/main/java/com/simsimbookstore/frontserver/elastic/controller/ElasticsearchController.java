package com.simsimbookstore.frontserver.elastic.controller;

import com.simsimbookstore.frontserver.elastic.dto.SearchBookDto;
import com.simsimbookstore.frontserver.elastic.service.ElasticsearchService;
import com.simsimbookstore.frontserver.util.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ElasticsearchController {


    private final ElasticsearchService elasticsearchService;

    @GetMapping("/search")
    public String searchListShow(@RequestParam(required = false) String keyword, @RequestParam(required = false) String sort, @RequestParam(defaultValue = "0") int page, Model model){

        if (Objects.isNull(sort)){
            sort = "popular";
        }

        PageResponse<SearchBookDto> searchBooks = elasticsearchService.getSearchData(keyword, sort, page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);
        model.addAttribute("searchBooks", searchBooks.getData());
        model.addAttribute("currentPage", searchBooks.getCurrentPage()); // 현재 페이지 번호
        model.addAttribute("totalPages", searchBooks.getTotalPage()); // 총 페이지 수
        model.addAttribute("total", searchBooks.getTotalElements());

        log.info("totalElements : {}", searchBooks.getTotalElements());
        log.info("currentPage : {}", searchBooks.getCurrentPage());
        log.info("totalPages : {}", searchBooks.getTotalPage());

        return "search/searchList";
    }
}
