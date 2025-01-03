package com.simsimbookstore.frontserver.books.book.client;


import com.simsimbookstore.frontserver.books.book.dto.BookListResponse;
import com.simsimbookstore.frontserver.books.book.dto.BookResponseDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "book-api-server", url = "http://localhost:8000/api/shop/books")
public interface BookClient {

    /**
     * 카테고리와 하위 카테고리에 해당하는 책을 조회
     *
     * @param categoryId
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/category/{categoryId}")
    PageResponse<BookListResponse> getBooksByCategory(@PathVariable(name = "categoryId") Long categoryId,
                                                      @RequestParam(required = false) Long userId,
                                                      @RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "10") int size);


    /**
     * 특정 도서를 제외한 동일 카테고리 내 인기 도서 추천 기능
     *
     * @param bookId
     * @param categoryIdList
     * @return
     */
    @GetMapping("/{bookId}/recommend")
    List<BookListResponse> getRecommendBooks(@PathVariable(name = "bookId") Long bookId, @RequestParam(name = "categoryIdList") List<Long> categoryIdList);


    /**
     * 최근에 출판된 6개 도서 조회
     *
     * @return
     */
    @GetMapping("/new")
    List<BookListResponse> getNewBooks();

    /**
     * 도서 상세조회
     *
     * @param bookId
     * @param userId
     * @return
     */
    @GetMapping("/{bookId}")
    BookResponseDto getBook(@PathVariable(name = "bookId") Long bookId, @RequestParam(required = false) Long userId);

    /**
     * 도서 목록조회
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    PageResponse<BookListResponse> getAllBooks(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "30") int size);

    /**
     * 도서 수정을 위해 도서 정보가져오가
     *
     * @param bookId
     * @return
     */
    @GetMapping("/{bookId}/update")
    BookResponseDto getBookByIdForUpdate(@PathVariable(name = "bookId") Long bookId);

    /**
     * 특정 태그와 관련된 도서 조회
     *
     * @param tagId
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/tag/{tagId}")
    PageResponse<BookListResponse> getBooksByTag(@PathVariable(name = "tagId") Long tagId,
                                                 @RequestParam(required = false) Long userId,
                                                 @RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "16") int size);


}
