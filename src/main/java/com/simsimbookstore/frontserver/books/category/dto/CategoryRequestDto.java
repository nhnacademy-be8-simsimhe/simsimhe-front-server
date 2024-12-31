package com.simsimbookstore.frontserver.books.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {

    @NotBlank(message = "등록할 카테고리 이름을 공백없이 적어주세요")
    private String categoryName;

    private Long parentId; // 부모 카테고리 ID


}
