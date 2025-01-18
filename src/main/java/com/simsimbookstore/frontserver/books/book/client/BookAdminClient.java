package com.simsimbookstore.frontserver.books.book.client;

import com.simsimbookstore.frontserver.books.book.dto.BookGiftResponse;
import com.simsimbookstore.frontserver.books.book.dto.BookRequestDto;
import com.simsimbookstore.frontserver.books.book.dto.BookResponseDto;
import com.simsimbookstore.frontserver.books.book.dto.BookStatusResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "book-admin-api-server", url = "http://localhost:8000/api/admin/shop/books")
public interface BookAdminClient {

    /**
     * 도서 등록
     *
     * @param bookRequestDto
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    BookResponseDto createBook(@RequestBody BookRequestDto bookRequestDto);

    /**
     * 도서 상태만 수정
     *
     * @param bookId
     * @param requestDto
     * @return
     */
    @PutMapping("/status/{bookId}")
    BookStatusResponseDto modifyBookStatus(@PathVariable(name = "bookId") Long bookId, @RequestBody BookRequestDto requestDto);

    /**
     * 도서 전체수정
     *
     * @param bookId
     * @param requestDto
     * @return
     */
    @PutMapping("/{bookId}")
    BookResponseDto updateBook(@PathVariable(name = "bookId") Long bookId, @RequestBody BookRequestDto requestDto);

    /**
     * 도서 포장 여부만 수정
     *
     * @param bookId
     * @param requestDto
     * @return
     */
    @PutMapping("/gift/{bookId}")
    BookGiftResponse modifyGift(@PathVariable(name = "bookId") Long bookId, @RequestBody BookRequestDto requestDto);


}
