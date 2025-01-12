package com.simsimbookstore.frontserver.coupon.controller;

import com.simsimbookstore.frontserver.books.book.dto.BookListResponse;
import com.simsimbookstore.frontserver.books.book.service.BookGetService;
import com.simsimbookstore.frontserver.books.category.dto.CategoryResponseDto;
import com.simsimbookstore.frontserver.books.category.service.CategoryService;
import com.simsimbookstore.frontserver.coupon.dto.*;
import com.simsimbookstore.frontserver.coupon.service.CouponAdminService;
import com.simsimbookstore.frontserver.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class CouponAdminController {

    private final CouponAdminService couponAdminService;
    private final BookGetService bookGetService;
    private final CategoryService categoryService;

    @GetMapping("/couponPolicies/create")
    public String createCouponPolicyForm() {
        return "admin/coupon/couponPolicy/addCouponPolicy";
    }

    @PostMapping("/couponPolicies/create")
    public String createCouponPolicy(@ModelAttribute @Valid CouponPolicyRequestDto requestDto,
                                     Model model) {
        CouponPolicyResponseDto couponPolicy = couponAdminService.createCouponPolicy(requestDto);
        model.addAttribute("couponPolicy", couponPolicy);

        return "admin/coupon/couponPolicy/successCreateCouponPolicy";
    }

    @GetMapping("/couponPolicies")
    public String getCouponPolicyList(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      Model model) {
        PageResponseDto<CouponPolicyResponseDto> couponPolicyList = couponAdminService.getCouponPolicyList(page,size);
        model.addAttribute("couponPolicyList", couponPolicyList);
        return "admin/coupon/couponPolicy/couponPolicyList";
    }

    @GetMapping("/couponTypes/selectCouponPolicy")
    public String selectCouponPolicyToCouponType(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       Model model) {

        PageResponseDto<CouponPolicyResponseDto> couponPolicyList = couponAdminService.getCouponPolicyList(page, size);
        model.addAttribute("couponPolicyList", couponPolicyList);
        return "admin/coupon/couponType/selectCouponPolicyToCouponType";
    }

    @GetMapping("/couponTypes/create")
    public String createCouponTypeForm(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam("couponPolicyId") Long couponPolicyId,
                                       @RequestParam(value = "couponTargetType", defaultValue = "ALL") String couponTargetType,
                                       Model model) {

        CouponTypeRequestDto couponTypeRequestDto = CouponTypeRequestDto.builder()
                .couponPolicyId(couponPolicyId)
                .couponTargetType(CouponTargetType.valueOf(couponTargetType))
                .build();

        // html에서 바로 RequestDto에 바인딩 시키기위해 객체를 model에 담는다.
        model.addAttribute("couponTypeRequestDto", couponTypeRequestDto);
        PageResponse<BookListResponse> allBooks = bookGetService.getAllBooks(page, 30);
        model.addAttribute("allBooks", allBooks);
        List<CategoryResponseDto> categories = categoryService.getALlCategorys();
        model.addAttribute("categories", categories);
        model.addAttribute("couponPolicyId",couponPolicyId); // 쿠폰 정책 선택 창에서 받은 Id값
        model.addAttribute("couponTargetTypes", CouponTargetType.values());
        return "admin/coupon/couponType/addCouponType";
    }

    @PostMapping("/couponTypes/create")
    public String createCouponType(@ModelAttribute @Valid CouponTypeRequestDto requestDto,
                                   Model model) {
        CouponTypeResponseDto couponType = couponAdminService.createCouponType(requestDto);
        model.addAttribute("couponType", couponType);
        return "admin/coupon/couponType/successCreateCouponType";
    }

    @GetMapping("/couponTypes")
    public String getCouponTypeList(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    Model model) {
        PageResponseDto<CouponTypeResponseDto> pageResponse = couponAdminService.getAllCouponType(page, size);
        model.addAttribute("pageResponse", pageResponse);
        return "admin/coupon/couponType/couponTypeList";
    }

    @GetMapping("/coupons/selectCouponType")

    private Pageable setPageable(Pageable pageable) {
        return PageRequest.of(
                pageable.getPageNumber(),
                10 // 페이지 크기를 10으로 고정
        );
    }
}
