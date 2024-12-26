package com.simsimbookstore.frontserver.book.feign;

import com.simsimbookstore.frontserver.book.dto.BookListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "bookApi", url = "http://localhost:8000/api/shop/books")
public interface BookServiceClient {
    @GetMapping
    String getBooks(@RequestParam int page, @RequestParam int size);
}
