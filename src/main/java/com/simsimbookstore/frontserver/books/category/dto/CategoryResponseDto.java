package com.simsimbookstore.frontserver.books.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {

    private Long categoryId;
    private String categoryName;
    private Long parentId; // 부모 카테고리 ID
    private String parentName; // 부모 카테고리 이름
    private List<CategoryResponseDto> children; // 자식 카테고리 목록
}