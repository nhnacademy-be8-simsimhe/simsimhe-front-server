package com.simsimbookstore.frontserver.books.book.controller;

import com.simsimbookstore.frontserver.books.book.dto.BookListResponse;
import com.simsimbookstore.frontserver.books.book.dto.BookResponseDto;
import com.simsimbookstore.frontserver.books.book.service.BookGetService;
import com.simsimbookstore.frontserver.books.category.dto.CategoryResponseDto;
import com.simsimbookstore.frontserver.books.category.service.CategoryService;
import com.simsimbookstore.frontserver.books.tag.dto.TagResponseDto;
import com.simsimbookstore.frontserver.books.tag.service.TagService;
import com.simsimbookstore.frontserver.reviews.review.domain.ReviewLikeCountDTO;
import com.simsimbookstore.frontserver.reviews.review.service.ReviewService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.util.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookGetController {

    private final BookGetService bookGetService;
    private final TagService tagService;
    private final CategoryService categoryService;
    private final ReviewService reviewService;

    /**
     * 사용자가 좋아요를누른 도서 조회
     *
     * @param page
     * @param size
     * @param model
     * @param customUserDetails
     * @return
     */
    @GetMapping("/user/like")
    public String getUserLikeBook(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model,
                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        // 로그인 여부 확인
        if (customUserDetails == null || customUserDetails.getUserId() == null) {
            model.addAttribute("message", "로그인 부탁드립니다.");
            return "book/wishlist"; // 동일한 화면으로 이동
        }
        Long userId = customUserDetails.getUserId();
        PageResponse<BookListResponse> userLikeBook = bookGetService.getUserLikeBook(page, size, userId);
        model.addAttribute("books", userLikeBook.getData()); // 도서 리스트
        model.addAttribute("userId", userId);
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("totalPages", userLikeBook.getTotalPage()); // 총 페이지 수
        model.addAttribute("size", size); // 페이지 크기

        return "book/wishlist";
    }


    @GetMapping("/category/{categoryId}")
    public String getBooksByCategory(@PathVariable(name = "categoryId") Long categoryId,
                                     @RequestParam(required = false) Long userId,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "latest") String sort,
                                     Model model) {
        // 카테고리와 관련된 도서 조회
        PageResponse<BookListResponse> booksPage = bookGetService.getBooksByCategory(categoryId, userId, page, size, sort);
        model.addAttribute("sort", sort);                 // 현재 정렬 기준


        model.addAttribute("books", booksPage.getData()); // 도서 리스트
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("totalPages", booksPage.getTotalPage()); // 총 페이지 수
        model.addAttribute("size", size); // 페이지 크기

        return "book/booksByCategory"; // 카테고리별 도서 목록을 보여줄 뷰 이름
    }

//
//    @GetMapping
//    public String getNewBooks(Model model) {
//        List<BookListResponse> newBooks = bookGetService.getNewBooks();
//        List<TagResponseDto> tags = tagService.getAllTags();
//        List<CategoryResponseDto> categorys = categoryService.getALlCategorys();
//
//        // 카테고리를 6개씩 그룹화
//        List<List<CategoryResponseDto>> groupedCategories = new ArrayList<>();
//        int groupSize = 6;
//        for (int i = 0; i < categorys.size(); i += groupSize) {
//            groupedCategories.add(categorys.subList(i, Math.min(i + groupSize, categorys.size())));
//        }
//
//        model.addAttribute("newBooks", newBooks);
//        model.addAttribute("tags", tags);
//        model.addAttribute("groupedCategories", groupedCategories);
//        return "main/index";
//    }

    @GetMapping("/{bookId}")
    public String getBookDetails(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                 @PathVariable Long bookId,
                                 @RequestParam(required = false) Long userId,
                                 Model model) {
        BookResponseDto book = bookGetService.getBook(bookId, userId);
        // 도서의 카테고리 ID 리스트 변환
        List<Long> categoryIdList = book.getCategoryList().stream()
                .flatMap(List::stream)
                .map(CategoryResponseDto::getCategoryId) // 각 CategoryResponseDto에서 ID 추출
                .collect(Collectors.toList());

        // 추천 도서 조회
        List<BookListResponse> recommendBooks = bookGetService.getRecommendBooks(bookId, categoryIdList);


        Long loginUserId = -1L;
        if (customUserDetails != null){
            loginUserId = customUserDetails.getUserId();
        }

        // 해당 도서 리뷰 조회
        Page<ReviewLikeCountDTO> reviews = reviewService.getAllReviewsOrderByRecent(bookId, loginUserId,0, 10);

        model.addAttribute("book", book);
        model.addAttribute("recommendBooks", recommendBooks);
        model.addAttribute("reviews", reviews);
        model.addAttribute("loginUserId",loginUserId);


        return "book/bookDetail";
    }

    //    @GetMapping("/tag/{tagId}")
//    public String getBooksByTag(@PathVariable Long tagId,
//                                @RequestParam(required = false) Long userId,
//                                @RequestParam(defaultValue = "1") int page,
//                                @RequestParam(defaultValue = "16") int size,
//                                Model model) {
//        // 태그에 맞는 도서 목록 조회
//        PageResponse<BookListResponse> booksByTag = bookGetService.getBooksByTag(tagId, userId, page, size);
//        List<TagResponseDto> tags = tagService.getAllTags();
//
//        model.addAttribute("books", booksByTag.getData()); // 도서 목록
//        //model.addAttribute("tags", tags);                 // 모든 태그
//        model.addAttribute("currentTagId", tagId);        // 현재 선택된 태그 ID
//        model.addAttribute("currentPage", page); // 현재 페이지 번호
//        model.addAttribute("totalPages", booksByTag.getTotalPage()); // 총 페이지 수
//        model.addAttribute("size", size); // 한 페이지에 표시할 항목 수
//
//        return "book/bookListByTag"; // 태그별 도서 목록 뷰
//    }
    @GetMapping("/tag/{tagId}")
    public String getBooksByTag(@PathVariable Long tagId,
                                @RequestParam(required = false) Long userId,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "16") int size,
                                @RequestParam(defaultValue = "latest") String sort,
                                Model model) {
        // 태그에 맞는 도서 목록 조회
        PageResponse<BookListResponse> booksByTag = bookGetService.getBooksByTag(tagId, userId, page, size, sort);
        List<TagResponseDto> tags = tagService.getAllTags();

        model.addAttribute("books", booksByTag.getData()); // 도서 목록
        model.addAttribute("currentTagId", tagId);        // 현재 선택된 태그 ID
        model.addAttribute("currentPage", page);          // 현재 페이지 번호
        model.addAttribute("totalPages", booksByTag.getTotalPage()); // 총 페이지 수
        model.addAttribute("size", size);                 // 한 페이지에 표시할 항목 수
        model.addAttribute("sort", sort);                 // 현재 정렬 기준

        return "book/bookListByTag"; // 태그별 도서 목록 뷰
    }


}
