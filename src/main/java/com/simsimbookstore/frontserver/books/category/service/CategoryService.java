package com.simsimbookstore.frontserver.books.category.service;

import com.simsimbookstore.frontserver.books.category.client.CategoryClient;
import com.simsimbookstore.frontserver.books.category.dto.CategoryRequestDto;
import com.simsimbookstore.frontserver.books.category.dto.CategoryResponseDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @CacheEvict(cacheNames = "categories", key = "'allCategories'")
    @Transactional
    public void createCategory(CategoryRequestDto categoryRequestDto) {
        client.createCategory(categoryRequestDto);
    }

    @Cacheable(cacheNames = "categories", key = "'allCategories'")
    public List<CategoryResponseDto> getALlCategorys(){
        return client.getAllCategorys();
    }

    /**
     * 카테고리삭제 -> 자식카테고리가 있으면 삭제안됌
     * @param categoryId
     */

    @CacheEvict(cacheNames = "categories", key = "'allCategories'")
    @Transactional
    public void deleteCartgory(Long categoryId){
        client.deleteCategory(categoryId);
    }


}
