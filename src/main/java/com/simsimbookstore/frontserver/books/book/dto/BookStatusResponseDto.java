package com.simsimbookstore.frontserver.books.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookStatusResponseDto {

    private BookListResponse.BookStatus bookStatus;
}
