package com.simsimbookstore.frontserver.book.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simsimbookstore.frontserver.book.dto.BookPage;
import com.simsimbookstore.frontserver.book.dto.BookListResponse;
import com.simsimbookstore.frontserver.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/books")
@Controller
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ModelAndView getBooks(@RequestParam int page, @RequestParam int size) {
        ModelAndView modelAndView = new ModelAndView("books");
        String jsonString = bookService.getBooks(page, size);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            BookPage bookListMapper = objectMapper.readValue(jsonString, BookPage.class);
            List<BookListResponse> data = bookListMapper.getData();
            modelAndView.addObject("books", data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return modelAndView;
    }
}
