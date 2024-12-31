package com.simsimbookstore.frontserver.books.book.client;


import com.simsimbookstore.frontserver.books.book.dto.BookListResponse;
import com.simsimbookstore.frontserver.books.book.dto.BookResponseDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "book-api-server",url = "http://localhost:8000/api/shop/books")
public interface BookClient {

    /**
     * 최근에 출판된 6개 도서 조회
     * @return
     */
    @GetMapping("/new")
    List<BookListResponse> getNewBooks();

    /**
     * 도서 상세조회
     * @param bookId
     * @param userId
     * @return
     */
    @GetMapping("/{bookId}")
    BookResponseDto getBook(@PathVariable(name = "bookId") Long bookId, @RequestParam(required = false) Long userId);

    /**
     * 도서 목록조회
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    PageResponse<BookListResponse> getAllBooks(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "30") int size);

    @GetMapping("/{bookId}/update")
    BookResponseDto getBookByIdForUpdate(@PathVariable(name = "bookId") Long bookId);


}
