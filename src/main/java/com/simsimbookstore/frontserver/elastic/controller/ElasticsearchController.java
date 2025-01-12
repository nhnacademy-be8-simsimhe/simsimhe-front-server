package com.simsimbookstore.frontserver.elastic.controller;


import com.simsimbookstore.frontserver.elastic.dto.SearchBookDto;
import com.simsimbookstore.frontserver.elastic.service.ElasticsearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ElasticsearchController {


    private final ElasticsearchService elasticsearchService;

    @GetMapping("/search")
    public String searchListShow(@RequestParam(required = false) String word, Model model){
        List<SearchBookDto> searchBooks = elasticsearchService.getSearchData(word);
        model.addAttribute("searchBooks", searchBooks);
        return "/search/searchList";
    }
}
