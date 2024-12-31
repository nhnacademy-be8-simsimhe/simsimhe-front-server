package com.simsimbookstore.frontserver.books.book.service;


import com.simsimbookstore.frontserver.books.book.client.BookClient;
import com.simsimbookstore.frontserver.books.book.dto.BookListResponse;
import com.simsimbookstore.frontserver.books.book.dto.BookResponseDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookGetService {

    private final BookClient bookClient;

    /**
     * 최근 출판된 6개 상품 조회
     *
     * @return
     */
    public List<BookListResponse> getNewBooks() {
        return bookClient.getNewBooks();
    }

    /**
     * 도서상세조회
     *
     * @param bookId
     * @param userId
     * @return
     */
    public BookResponseDto getBook(Long bookId, Long userId) {
        return bookClient.getBook(bookId, userId);
    }

    /**
     * 관리자가 모든 도서조회 페이징처리해서
     * @param page
     * @param size
     * @return
     */
    public PageResponse<BookListResponse> getAllBooks(int page, int size) {
        return bookClient.getAllBooks(page,size);
    }

    public BookResponseDto getBookByIdForUpdate(Long bookId){
        return bookClient.getBookByIdForUpdate(bookId);
    }
}
