package com.simsimbookstore.frontserver.book.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "bookApi", url = "http://localhost:8000/api/books")
public interface BookServiceClient {

    @GetMapping
    String getBooks();
}
