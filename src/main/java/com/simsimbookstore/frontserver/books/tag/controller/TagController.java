package com.simsimbookstore.frontserver.books.tag.controller;


import com.simsimbookstore.frontserver.books.tag.dto.TagRequestDto;
import com.simsimbookstore.frontserver.books.tag.dto.TagResponseDto;
import com.simsimbookstore.frontserver.books.tag.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/tags")
public class TagController {

    private final TagService tagService;

    /**
     * 모든 태그 조회
     *
     * @param model
     * @return
     */
    @GetMapping
    public String getAllTags(Model model) {
        List<TagResponseDto> tags = tagService.getAllTags();
        model.addAttribute("tags", tags);

        return "admin/book/tagList";
    }

    /**
     * 태그 추가 폼
     * @param model
     * @return
     */
    @GetMapping("/create")
    public String createTagForm(Model model) {
        model.addAttribute("tag", new TagRequestDto());
        return "admin/book/addTagForm";
    }

    /**
     * 태그 추가
     * @param requestDto
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/create")
    public String createTag(@ModelAttribute("tag") @Valid TagRequestDto requestDto,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tag", requestDto);
            return "admin/book/addTagForm";
        }

        tagService.createTag(requestDto);

        return "redirect:/admin/tags";
    }

    /**
     * 태그 수정 폼
     * @param tagId
     * @param model
     * @return
     */
    @GetMapping("/update/{tagId}")
    public String updateTagForm(@PathVariable(name = "tagId") Long tagId,
                                Model model) {
        TagResponseDto tag = tagService.getTag(tagId);
        model.addAttribute("tag", tag);
        return "admin/book/updateTagForm";
    }

    /**
     * 태그 수정
     * @param tagId
     * @param tagRequestDto
     * @param bindingResult
     * @param model
     * @return
     */
    @PutMapping("/update/{tagId}")
    public String updateTag(@PathVariable(name = "tagId") Long tagId,
                            @ModelAttribute @Valid TagRequestDto tagRequestDto,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tag", tagRequestDto);
            return "admin/book/updateTagForm";
        }

        tagService.updateTag(tagId,tagRequestDto);

       return "redirect:/admin/tags";
    }

}
