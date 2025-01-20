package com.simsimbookstore.frontserver.wrap.controller;

import com.simsimbookstore.frontserver.wrap.dto.WrapTypeRequestDto;
import com.simsimbookstore.frontserver.wrap.dto.WrapTypeResponseDto;
import com.simsimbookstore.frontserver.wrap.service.WrapService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/wrap-types")
public class WrapTypeAdminController {

    private final WrapService wrapService;

    /**
     * 모든 포장지 조회
     */

    @GetMapping
    public String getAllWrapTypes(Model model) {
        List<WrapTypeResponseDto> wrapTypes = wrapService.getAllWrapTypes();
        model.addAttribute("wrapTypes", wrapTypes);
        return "admin/wrap/wrapTypeList";
    }


    /**
     * 포장지 추가 폼
     */
    @GetMapping("/create")
    public String createWrapTypeForm(Model model) {
        model.addAttribute("wrapType", new WrapTypeRequestDto());
        return "admin/wrap/wrapTypeCreate";
    }

    /**
     * 포장지 추가
     */
    @PostMapping("/create")
    public String createWrapType(@ModelAttribute("wrapType") @Valid WrapTypeRequestDto requestDto
    ) {
        wrapService.createWrapType(requestDto);
        return "redirect:/admin/wrap-types";
    }

    /**
     * 판매 가능 여부 업데이트 폼
     */
    @GetMapping("/update/{id}/availability")
    public String updateAvailabilityForm(@PathVariable("id") Long id, Model model) {
        WrapTypeResponseDto wrapType = wrapService.getWrapType(id);
        model.addAttribute("wrapType", wrapType);
        return "admin/wrap/updateAvailability";
    }

    /**
     * 판매 가능 여부 업데이트
     */
    @PostMapping("/update/{id}/availability")
    public String updateAvailability(@PathVariable("id") Long id,
                                     @RequestParam("isAvailable") Boolean isAvailable) {
        wrapService.updateWrapType(id, isAvailable);
        return "redirect:/admin/wrap-types";
    }
}

