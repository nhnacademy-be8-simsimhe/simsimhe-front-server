package com.simsimbookstore.frontserver.books.category.client;

import com.simsimbookstore.frontserver.books.category.dto.CategoryRequestDto;
import com.simsimbookstore.frontserver.books.category.dto.CategoryResponseDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "category-api-server", url = "http://localhost:8000/api/admin/categories")
public interface CategoryClient {

    @DeleteMapping("/{categoryId}")
    void deleteCategory(@PathVariable(name = "categoryId") Long categoryId);

    @GetMapping
    PageResponse<CategoryResponseDto> getAllCategory(@RequestParam(defaultValue = "1") int page,
                                                     @RequestParam(defaultValue = "30") int size);

    @GetMapping("/list")
    List<CategoryResponseDto> getAllCategorys();

    @PostMapping
    CategoryResponseDto createCategory(@RequestBody CategoryRequestDto categoryRequestDto);

    @GetMapping("/{categoryId}")
    CategoryResponseDto getCategoryById(@PathVariable Long categoryId);
}
