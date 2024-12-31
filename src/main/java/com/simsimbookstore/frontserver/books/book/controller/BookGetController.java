package com.simsimbookstore.frontserver.books.book.controller;

import com.simsimbookstore.frontserver.books.book.dto.BookListResponse;
import com.simsimbookstore.frontserver.books.book.dto.BookResponseDto;
import com.simsimbookstore.frontserver.books.book.service.BookGetService;
import com.simsimbookstore.frontserver.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookGetController {

    private final BookGetService bookGetService;

    @GetMapping
    public String getNewBooks(Model model) {
        List<BookListResponse> newBooks = bookGetService.getNewBooks();
        model.addAttribute("newBooks", newBooks);
        return "main/index";
    }

    @GetMapping("/{bookId}")
    public String getBookDetails(@PathVariable Long bookId,
                                 @RequestParam(required = false) Long userId,
                                 Model model) {
        BookResponseDto book = bookGetService.getBook(bookId, userId);
        model.addAttribute("book", book);
        return "book/bookDetail";
    }


}
