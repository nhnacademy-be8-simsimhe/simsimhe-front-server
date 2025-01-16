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
    public String searchListShow(@RequestParam(required = false) String keyword, @RequestParam(required = false) String sort, @RequestParam(defaultValue = "1") int page, Model model){

        if (Objects.isNull(sort)){
            sort = "popular";
        }

        PageResponse<SearchBookDto> searchBooks = elasticsearchService.getSearchData(keyword, sort, page);

        int pageGroupSize = 10;

        int currentPage = searchBooks.getCurrentPage() - 1; // 1부터 시작한 값을 0부터 시작하도록 보정
        int currentPageGroup = currentPage / pageGroupSize;

        int startPage = currentPageGroup * pageGroupSize + 1; // 1부터 시작
        int endPage = Math.min(startPage + pageGroupSize - 1, searchBooks.getTotalPage());


        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);
        model.addAttribute("searchBooks", searchBooks.getData());
        model.addAttribute("currentPage", currentPage + 1); // 다시 1부터 시작하도록 보정
        model.addAttribute("totalPages", searchBooks.getTotalPage());
        model.addAttribute("total", searchBooks.getTotalElements());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        log.info("totalElements : {}", searchBooks.getTotalElements());
        log.info("currentPage : {}", searchBooks.getCurrentPage());
        log.info("startPage : {}", startPage);
        log.info("endPage : {}", endPage);

        return "search/searchList";
    }
}
