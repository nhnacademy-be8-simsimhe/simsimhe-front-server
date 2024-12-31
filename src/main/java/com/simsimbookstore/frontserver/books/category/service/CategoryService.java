package com.simsimbookstore.frontserver.books.category.service;

import com.simsimbookstore.frontserver.books.category.client.CategoryClient;
import com.simsimbookstore.frontserver.books.category.dto.CategoryRequestDto;
import com.simsimbookstore.frontserver.books.category.dto.CategoryResponseDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryClient client;

    public PageResponse<CategoryResponseDto> getAllCategory(int page, int size) {
        return client.getAllCategory(page, size);
    }

    @Transactional
    public void createCategory(CategoryRequestDto categoryRequestDto) {
        client.createCategory(categoryRequestDto);
    }

    public List<CategoryResponseDto> getALlCategorys(){
        return client.getAllCategorys();
    }
}
