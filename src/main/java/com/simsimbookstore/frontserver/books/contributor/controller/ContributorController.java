package com.simsimbookstore.frontserver.books.contributor.controller;

import com.simsimbookstore.frontserver.books.contributor.dto.ContributorRequestDto;
import com.simsimbookstore.frontserver.books.contributor.dto.ContributorResponseDto;
import com.simsimbookstore.frontserver.books.contributor.service.ContributorService;
import com.simsimbookstore.frontserver.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("/admin/contributors")
@RequiredArgsConstructor
public class ContributorController {

    private final ContributorService contributorService;


    /**
     * 기여자 페이징처리해서 조회
     * @param page
     * @param size
     * @param model
     * @return
     */
    @GetMapping
    public String getContributorPage(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     Model model) {
        PageResponse<ContributorResponseDto> allContributorPage = contributorService.getAllContributorPage(page, size);

        model.addAttribute("allContributorPage", allContributorPage);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "admin/book/contributorList";
    }

    // 기여자 추가 폼 반환
    @GetMapping("/create")
    public String createContributorForm(Model model) {
        model.addAttribute("contributor", new ContributorRequestDto());
        return "admin/book/addContributorForm";
    }

    // 기여자 생성 처리
    @PostMapping("/create")
    public String createContributor(@ModelAttribute("contributor") @Valid ContributorRequestDto requestDto,
                                    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 폼에 에러를 담아 다시 반환
            model.addAttribute("contributor", requestDto);
            return "admin/book/addContributorForm";
        }

        contributorService.createContributor(requestDto);

        return "redirect:/admin/contributors"; // 기여자 목록 페이지로 리다이렉트
    }


    /**
     * 기여자 삭제
     *
     * @param contributorId
     * @param page
     * @return
     */
    @DeleteMapping("/{contributorId}")
    public String deleteContributor(@PathVariable(name = "contributorId") Long contributorId, @RequestParam int page) {
        contributorService.deleteContributor(contributorId);
        return "redirect:/admin/contributors?page=" + page;
    }


    // 수정 폼 반환
    @GetMapping("/update/{contributorId}")
    public String getUpdateContributorForm(@PathVariable(name = "contributorId") Long contributorId,
                                           @RequestParam(name = "page", defaultValue = "1") int page,
                                           Model model) {
        ContributorResponseDto contributor = contributorService.getContributor(contributorId);
        model.addAttribute("contributor", contributor);
        model.addAttribute("page", page);
        return "admin/book/updateContributorForm"; // 수정 폼으로 이동
    }

    // 수정 처리
    @PutMapping("/update/{contributorId}")
    public String updateContributor(@PathVariable(name = "contributorId") Long contributorId,
                                    @ModelAttribute @Valid ContributorRequestDto contributorRequestDto,
                                    BindingResult bindingResult,
                                    @RequestParam(name = "page") int page,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            // 에러가 있을 경우 수정 폼으로 다시 반환
            model.addAttribute("contributor", contributorRequestDto);
            model.addAttribute("page", page);
            return "admin/book/updateContributorForm";
        }

        contributorService.updateContributor(contributorId, contributorRequestDto);
        return "redirect:/admin/contributors?page=" + page; // 수정 완료 후 목록 페이지로 리다이렉트
    }

}
