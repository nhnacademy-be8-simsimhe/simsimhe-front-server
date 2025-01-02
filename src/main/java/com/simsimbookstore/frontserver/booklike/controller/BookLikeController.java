package com.simsimbookstore.frontserver.booklike.controller;

import com.simsimbookstore.frontserver.booklike.dto.BookLikeRequestDto;
import com.simsimbookstore.frontserver.booklike.dto.BookLikeResponseDto;
import com.simsimbookstore.frontserver.booklike.service.BookLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class BookLikeController {

    private final BookLikeService bookLikeService;

    @PutMapping
    public ResponseEntity<BookLikeResponseDto> postLike(@RequestBody BookLikeRequestDto requestDto) {
        // 서비스 호출하여 찜 상태 업데이트
        BookLikeResponseDto responseDto = bookLikeService.setBookLike(requestDto);

        // 성공 응답 반환
        return ResponseEntity.ok(responseDto);

    }
}
