package com.simsimbookstore.frontserver.booklike.service;


import com.simsimbookstore.frontserver.booklike.client.BookLikeClient;
import com.simsimbookstore.frontserver.booklike.dto.BookLikeRequestDto;
import com.simsimbookstore.frontserver.booklike.dto.BookLikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookLikeService {

    private final BookLikeClient bookLikeClient;

    public BookLikeResponseDto setBookLike(BookLikeRequestDto requestDto){
        return bookLikeClient.setBookLike(requestDto);
    }
}
