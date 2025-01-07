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


    public List<BookListResponse> getPopularityBook(){
        return bookClient.getPopularityBook();
    }

    /**
     * 사용자가 좋아요누른 도서조회
     * @param page
     * @param size
     * @param userId
     * @return
     */
    public PageResponse<BookListResponse> getUserLikeBook(int page, int size, Long userId) {
        return bookClient.getUserLikeBook(page, size, userId);
    }


    //특정 도서를 제외한 동일 카테고리 내 인기 도서 추천 기능
    public List<BookListResponse> getRecommendBooks(Long bookId, List<Long> categoryIdList) {
        return bookClient.getRecommendBooks(bookId, categoryIdList);
    }

    /**
     * 카테고리랑 관련된 도서 조회
     *
     * @param categoryId
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PageResponse<BookListResponse> getBooksByCategory(Long categoryId, Long userId, int page, int size) {
        return bookClient.getBooksByCategory(categoryId, userId, page, size);
    }


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
     *
     * @param page
     * @param size
     * @return
     */
    public PageResponse<BookListResponse> getAllBooks(int page, int size) {
        return bookClient.getAllBooks(page, size);
    }

    /**
     * 도서 수정을 위한 도서 정보 불러오기
     *
     * @param bookId
     * @return
     */
    public BookResponseDto getBookByIdForUpdate(Long bookId) {
        return bookClient.getBookByIdForUpdate(bookId);
    }

    /**
     * 특정 태그와 관련된 도서 조회
     *
     * @param tagId
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PageResponse<BookListResponse> getBooksByTag(Long tagId, Long userId, int page, int size) {
        return bookClient.getBooksByTag(tagId, userId, page, size);
    }
}
