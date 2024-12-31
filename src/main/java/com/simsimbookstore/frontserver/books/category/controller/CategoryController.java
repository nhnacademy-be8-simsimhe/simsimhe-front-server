package com.simsimbookstore.frontserver.books.category.controller;


import com.simsimbookstore.frontserver.books.category.dto.CategoryRequestDto;
import com.simsimbookstore.frontserver.books.category.dto.CategoryResponseDto;
import com.simsimbookstore.frontserver.books.category.service.CategoryService;
import com.simsimbookstore.frontserver.books.contributor.dto.ContributorRequestDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String categoryList(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "30") int size,
                               Model model) {
        PageResponse<CategoryResponseDto> categoryPage = categoryService.getAllCategory(page, size);

        model.addAttribute("categories", categoryPage.getData());
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("totalPages", categoryPage.getTotalPage()); // 총 페이지 수
        model.addAttribute("size", size); // 한 페이지에 표시할 항목 수
        return "admin/category/adminCategoryList";
    }

    @GetMapping("/create")
    public String createCategoryForm(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "30") int size,
                                        Model model) {
        PageResponse<CategoryResponseDto> categoryPage = categoryService.getAllCategory(page, size);

        model.addAttribute("category", new CategoryRequestDto());
        model.addAttribute("categories", categoryPage.getData());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryPage.getTotalPage());
        model.addAttribute("size", size); // 한 페이지에 표시할 항목 수

        return "admin/category/addCategoryForm";
    }


    // 카테고리 추가 처리
    @PostMapping("/create")
    public String createCategory(@ModelAttribute("category") @Valid CategoryRequestDto categoryRequestDto,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRequestDto);
            return "admin/category/addCategoryForm";
        }

        categoryService.createCategory(categoryRequestDto);
        return "redirect:/admin/categories";
    }


}
