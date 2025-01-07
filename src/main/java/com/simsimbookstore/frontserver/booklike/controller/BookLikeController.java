package com.simsimbookstore.frontserver.booklike.controller;

import com.simsimbookstore.frontserver.booklike.dto.BookLikeRequestDto;
import com.simsimbookstore.frontserver.booklike.dto.BookLikeResponseDto;
import com.simsimbookstore.frontserver.booklike.service.BookLikeService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class BookLikeController {

    private final BookLikeService bookLikeService;

    /**
     * 로그인한 사용자가 도서상세조회 페이지에서 좋아요를 설정
     * @param requestDto
     * @param customUserDetails
     * @return
     */
    @PutMapping
    public ResponseEntity<?> postLike(@RequestBody BookLikeRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (customUserDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        try {
            Long userId = customUserDetails.getUserId();
            requestDto.setUserId(userId);

            // 서비스 호출
            BookLikeResponseDto responseDto = bookLikeService.setBookLike(requestDto);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating like status");
        }
    }

}
