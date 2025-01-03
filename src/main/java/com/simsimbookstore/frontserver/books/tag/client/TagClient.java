package com.simsimbookstore.frontserver.books.tag.client;

import com.simsimbookstore.frontserver.books.tag.dto.TagRequestDto;
import com.simsimbookstore.frontserver.books.tag.dto.TagResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "tag-api-server", url = "http://localhost:8000/api/admin/tags")
public interface TagClient {

    @GetMapping("/list")
    List<TagResponseDto> getAllTags();

    @PostMapping
    TagResponseDto createTag(@RequestBody TagRequestDto requestDto);

    @PutMapping("/{tagId}")
    TagResponseDto updateTag(@PathVariable(name = "tagId") Long tagId, @RequestBody TagRequestDto requestDto);

    @GetMapping("/{tagId}")
    TagResponseDto getTag(@PathVariable(name = "tagId") Long tagId);
}
