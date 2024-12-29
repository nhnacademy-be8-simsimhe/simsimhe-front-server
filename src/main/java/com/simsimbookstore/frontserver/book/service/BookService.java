package com.simsimbookstore.frontserver.book.service;

import com.simsimbookstore.frontserver.book.dto.BookListResponse;
import com.simsimbookstore.frontserver.book.feign.BookServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookServiceClient bookServiceClient;


    public String getBooks(int page, int size){
//        List<BookListResponse> books = bookServiceClient.getBooks(0, 10);
        String books = bookServiceClient.getBooks(0, 10);
        return books;
    }
}
