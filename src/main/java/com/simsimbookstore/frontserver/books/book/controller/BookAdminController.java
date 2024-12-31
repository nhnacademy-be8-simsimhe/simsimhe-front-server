package com.simsimbookstore.frontserver.books.book.controller;

import com.simsimbookstore.frontserver.books.book.client.BookAdminClient;
import com.simsimbookstore.frontserver.books.book.dto.BookListResponse;
import com.simsimbookstore.frontserver.books.book.dto.BookRequestDto;
import com.simsimbookstore.frontserver.books.book.dto.BookResponseDto;
import com.simsimbookstore.frontserver.books.book.service.BookGetService;
import com.simsimbookstore.frontserver.books.book.service.BookManagementService;
import com.simsimbookstore.frontserver.books.category.dto.CategoryResponseDto;
import com.simsimbookstore.frontserver.books.category.service.CategoryService;
import com.simsimbookstore.frontserver.books.contributor.dto.ContributorResponseDto;
import com.simsimbookstore.frontserver.books.contributor.service.ContributorService;
import com.simsimbookstore.frontserver.books.tag.dto.TagResponseDto;
import com.simsimbookstore.frontserver.books.tag.service.TagService;
import com.simsimbookstore.frontserver.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/books")
@RequiredArgsConstructor
public class BookAdminController {

    private final BookGetService bookGetService;
    private final BookManagementService bookManagementService;
    private final ContributorService contributorService;
    private final TagService tagService;
    private final CategoryService categoryService;


    /**
     * 도서 등록 폼
     *
     * @param model
     * @return
     */
    @GetMapping("/create")
    public String getBookForm(Model model) {

        List<ContributorResponseDto> allContributors = contributorService.getAllContributors();
        List<TagResponseDto> allTags = tagService.getAllTags();
        List<CategoryResponseDto> aLlCategorys = categoryService.getALlCategorys();
        model.addAttribute("contributorList", allContributors);
        model.addAttribute("tagList", allTags);
        model.addAttribute("categoryList", aLlCategorys);
        model.addAttribute("book", new BookRequestDto());

        return "admin/book/registerBookForm";
    }

    /**
     * 도서 등록
     *
     * @param requestDto
     * @param categoryIdList
     * @param contributorIdList
     * @param tagIdList
     * @return
     */
    @PostMapping("/create")
    public String createBook(@ModelAttribute @Valid BookRequestDto requestDto,
                             @RequestParam(value = "categoryIdList") List<Long> categoryIdList,
                             @RequestParam(value = "contributorIdList") List<Long> contributorIdList,
                             @RequestParam(value = "tagIdList") List<Long> tagIdList) {
        requestDto.setCategoryIdList(categoryIdList != null ? categoryIdList : List.of());
        requestDto.setContributoridList(contributorIdList != null ? contributorIdList : List.of());
        requestDto.setTagIdList(tagIdList != null ? tagIdList : List.of());

        bookManagementService.createBook(requestDto);
        return "redirect:/books";
    }

    /**
     * 관리자가 도서 목록보기
     *
     * @param model
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public String getAllBook(Model model, @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "30") int size) {
        PageResponse<BookListResponse> allBooks = bookGetService.getAllBooks(page, size);

        model.addAttribute("books", allBooks.getData());
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("totalPages", allBooks.getTotalPage()); // 총 페이지 수
        model.addAttribute("size", size); // 한 페이지에 표시할 항목 수
        return "admin/book/bookList";
    }


    /**
     * 도서 상태만 변경하는 폼
     *
     * @param bookId
     * @param page
     * @param model
     * @return
     */
    @GetMapping("/status/{bookId}")
    public String updateBookStatusForm(@PathVariable Long bookId,
                                       @RequestParam(defaultValue = "1") int page,
                                       Model model) {
        BookResponseDto book = bookGetService.getBook(bookId, null);
        model.addAttribute("book", book);
        model.addAttribute("page", page);

        return "admin/book/updateBookStatusForm";
    }

    /**
     * 책을 상태를 변경하는 컨트롤러 입니다.
     */
    @PutMapping("/status/{bookId}")
    public String updateStatus(@PathVariable("bookId") Long bookId,
                               @ModelAttribute BookRequestDto request,
                               @RequestParam int page) {

        bookManagementService.modifyBookStatus(bookId, request);

        return "redirect:/admin/books/list?page=" + page;
    }

    /**
     * 도서 변경 폼
     *
     * @param bookId
     * @param page
     * @param model
     * @return
     */
    @GetMapping("/update/{bookId}")
    public String getUpdateBookForm(@PathVariable("bookId") Long bookId,
                                    @RequestParam("page") int page,
                                    Model model) {


        List<ContributorResponseDto> contributorList = contributorService.getAllContributors();
        List<TagResponseDto> tagList = tagService.getAllTags();
        model.addAttribute("contributorList", contributorList);
        model.addAttribute("tagList", tagList);
        BookResponseDto bookByIdForUpdate = bookGetService.getBookByIdForUpdate(bookId);
        model.addAttribute("book", bookByIdForUpdate);
        model.addAttribute("page", page);

        return "admin/book/updateBookForm";
    }

    /**
     * 도서 을 수정하는 컨트롤러 입니다.
     */
    @PutMapping("/{bookId}")
    public String updateBook(@ModelAttribute BookRequestDto request,
                             @PathVariable("bookId") Long bookId,
                             @RequestParam(value = "page", defaultValue = "1") int page) {

        bookManagementService.updateBook(bookId, request);
        return "redirect:/admin/books/list?page=" + page;
    }

    /**
     * 도서 포장 여부 수정 폼으로 이동
     */
    @GetMapping("/gift/{bookId}")
    public String getGiftUpdateForm(@PathVariable("bookId") Long bookId,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    Model model) {
        // 도서 정보 조회
        BookResponseDto book = bookGetService.getBook(bookId, null);

        // 모델에 데이터 추가
        model.addAttribute("bookId", bookId);
        model.addAttribute("book", book);
        model.addAttribute("page", page);

        return "admin/book/updateBookGiftForm"; // 포장 여부 수정 폼 뷰로 이동
    }

    /**
     * 도서 포장 여부 수정
     */
    @PutMapping("/gift/{bookId}")
    public String modifyGift(@PathVariable("bookId") Long bookId,
                             @ModelAttribute BookRequestDto bookRequestDto,
                             @RequestParam(value = "page", defaultValue = "1") int page) {
        // 포장 여부 수정 서비스 호출
        bookManagementService.modifyGift(bookId, bookRequestDto);

        return "redirect:/admin/books/list?page=" + page; // 목록 페이지로 리다이렉트
    }


}
