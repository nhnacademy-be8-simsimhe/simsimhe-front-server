package com.simsimbookstore.frontserver.booklike.client;


import com.simsimbookstore.frontserver.booklike.dto.BookLikeRequestDto;
import com.simsimbookstore.frontserver.booklike.dto.BookLikeResponseDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "booklike-api-server",url = "http://localhost:8000/api/shop/likes")
public interface BookLikeClient {

    @PutMapping
    BookLikeResponseDto setBookLike(@RequestBody  BookLikeRequestDto requestDto);
}
