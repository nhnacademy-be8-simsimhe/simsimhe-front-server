package com.simsimbookstore.frontserver.books.book.service;


import com.simsimbookstore.frontserver.books.book.client.BookAdminClient;
import com.simsimbookstore.frontserver.books.book.dto.BookRequestDto;
import com.simsimbookstore.frontserver.books.book.dto.BookResponseDto;
import com.simsimbookstore.frontserver.books.contributor.service.ContributorService;
import com.simsimbookstore.frontserver.books.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
