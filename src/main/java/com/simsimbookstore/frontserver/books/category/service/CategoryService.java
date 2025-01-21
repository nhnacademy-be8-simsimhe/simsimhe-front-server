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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<CategoryResponseDto> getALlCategorys() {
        return client.getAllCategorys();
    }

    /**
     * 카테고리삭제 -> 자식카테고리가 있으면 삭제안됌
     *
     * @param categoryId
     */

    @CacheEvict(cacheNames = "categories", key = "'allCategories'")
    @Transactional
    public void deleteCartgory(Long categoryId) {
        client.deleteCategory(categoryId);
    }

    public CategoryResponseDto getCategory(Long categoryId) {
        return client.getCategoryById(categoryId);
    }

    public List<CategoryResponseDto> getCategoryHierarchy(List<CategoryResponseDto> categories) {
        Map<Long, CategoryResponseDto> categoryMap = new HashMap<>();
        List<CategoryResponseDto> topLevelCategories = new ArrayList<>();

        // 카테고리 맵을 만들어서, 부모와 자식 관계를 설정
        for (CategoryResponseDto category : categories) {
            categoryMap.put(category.getCategoryId(), category);
            category.setChildren(new ArrayList<>());
        }

        // 부모 카테고리에 자식 카테고리 추가
        for (CategoryResponseDto category : categories) {
            if (category.getParentId() != null) {
                CategoryResponseDto parent = categoryMap.get(category.getParentId());
                if (parent != null) {
                    parent.getChildren().add(category);
                }
            } else {
                topLevelCategories.add(category);  // 최상위 카테고리
            }
        }

        return topLevelCategories;
    }


}
