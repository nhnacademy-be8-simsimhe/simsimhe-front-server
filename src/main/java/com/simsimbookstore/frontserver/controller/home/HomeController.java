package com.simsimbookstore.frontserver.controller.home;

import com.simsimbookstore.frontserver.books.book.dto.BookListResponse;
import com.simsimbookstore.frontserver.books.book.service.BookGetService;
import com.simsimbookstore.frontserver.books.category.dto.CategoryResponseDto;
import com.simsimbookstore.frontserver.books.category.service.CategoryService;
import com.simsimbookstore.frontserver.books.tag.dto.TagResponseDto;
import com.simsimbookstore.frontserver.books.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping()
@Controller
public class HomeController {
    private final BookGetService bookGetService;
    private final TagService tagService;
    private final CategoryService categoryService;


    @GetMapping({"/index","/"})
    public ModelAndView index(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("isAuthenticated", Objects.nonNull(principal));

        List<BookListResponse> newBooks = bookGetService.getNewBooks();
        List<TagResponseDto> tags = tagService.getAllTags();
        List<CategoryResponseDto> categorys = categoryService.getALlCategorys();

        // 카테고리를 6개씩 그룹화
        List<List<CategoryResponseDto>> groupedCategories = new ArrayList<>();
        int groupSize = 6;
        for (int i = 0; i < categorys.size(); i += groupSize) {
            groupedCategories.add(categorys.subList(i, Math.min(i + groupSize, categorys.size())));
        }

        modelAndView.addObject("newBooks", newBooks);
        modelAndView.addObject("tags", tags);
        modelAndView.addObject("groupedCategories", groupedCategories);
        return modelAndView;
    }
}
