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

        // 한 번에 표시할 페이지 버튼 개수
        // 한 페이지 그룹에 표시할 페이지 수
        int pageGroupSize = 10;

// 현재 페이지 그룹 계산
        int currentPage = searchBooks.getCurrentPage() - 1; // 1부터 시작한 값을 0부터 시작하도록 보정
        int currentPageGroup = currentPage / pageGroupSize;

// 시작 페이지와 끝 페이지 계산
        int startPage = currentPageGroup * pageGroupSize + 1; // 1부터 시작
        int endPage = Math.min(startPage + pageGroupSize - 1, searchBooks.getTotalPage());

// 모델에 값 전달
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);
        model.addAttribute("searchBooks", searchBooks.getData());
        model.addAttribute("currentPage", currentPage + 1); // 다시 1부터 시작하도록 보정
        model.addAttribute("totalPages", searchBooks.getTotalPage());
        model.addAttribute("total", searchBooks.getTotalElements());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "search/searchList";
    }
}
