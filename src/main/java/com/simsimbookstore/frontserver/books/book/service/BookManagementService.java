package com.simsimbookstore.frontserver.books.book.service;


import com.simsimbookstore.frontserver.books.book.client.BookAdminClient;
import com.simsimbookstore.frontserver.books.book.dto.BookRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookManagementService {

    private final BookAdminClient bookAdminClient;

    @Transactional
    public void createBook(BookRequestDto bookRequestDto) {
        bookAdminClient.createBook(bookRequestDto);
    }

    /**
     * 도서 상태만 변경
     *
     * @param bookId
     * @param requestDto
     */
    @Transactional
    public void modifyBookStatus(Long bookId, BookRequestDto requestDto) {
        bookAdminClient.modifyBookStatus(bookId, requestDto);
    }

    /**
     * 도서 상태빼고 변경
     *
     * @param bookId
     * @param bookRequestDto
     */
    @Transactional
    public void updateBook(Long bookId, BookRequestDto bookRequestDto) {
        bookAdminClient.updateBook(bookId, bookRequestDto);
    }

    /**
     * 도서 포장수정
     *
     * @param bookId
     * @param bookRequestDto
     */
    @Transactional
    public void modifyGift(Long bookId, BookRequestDto bookRequestDto) {
        bookAdminClient.modifyGift(bookId, bookRequestDto);
    }


}
