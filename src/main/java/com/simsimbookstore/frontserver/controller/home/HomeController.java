package com.simsimbookstore.frontserver.controller.home;

import com.simsimbookstore.frontserver.books.book.dto.BookListResponse;
import com.simsimbookstore.frontserver.books.book.service.BookGetService;
import com.simsimbookstore.frontserver.books.category.dto.CategoryResponseDto;
import com.simsimbookstore.frontserver.books.category.service.CategoryService;
import com.simsimbookstore.frontserver.books.tag.dto.TagResponseDto;
import com.simsimbookstore.frontserver.books.tag.service.TagService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping()
@Controller
public class HomeController {
    private final BookGetService bookGetService;
    private final TagService tagService;
    private final CategoryService categoryService;

    @GetMapping({"/index", "/"})
    public ModelAndView index(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("isAuthenticated", Objects.nonNull(customUserDetails));

        if (Objects.nonNull(customUserDetails)) {
            modelAndView.addObject("isAdmin", customUserDetails.isAdmin());
        }

        List<BookListResponse> newBooks = bookGetService.getNewBooks();
        List<TagResponseDto> tags = tagService.getAllTags();
        List<CategoryResponseDto> categories = categoryService.getALlCategorys();

        // 카테고리 계층 구조 가져오기
        List<CategoryResponseDto> categoryHierarchy = categoryService.getCategoryHierarchy(categories);

        List<BookListResponse> popularityBooks = bookGetService.getPopularityBook();

        modelAndView.addObject("newBooks", newBooks);
        modelAndView.addObject("popularityBooks", popularityBooks);
        modelAndView.addObject("tags", tags);
        modelAndView.addObject("categoryHierarchy", categoryHierarchy);

        return modelAndView;
    }


}
