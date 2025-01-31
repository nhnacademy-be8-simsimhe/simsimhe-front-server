package com.simsimbookstore.frontserver.booklike.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookLikeRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long bookId;


}
