package com.simsimbookstore.frontserver.books.tag.service;

import com.simsimbookstore.frontserver.books.tag.client.TagClient;
import com.simsimbookstore.frontserver.books.tag.dto.TagRequestDto;
import com.simsimbookstore.frontserver.books.tag.dto.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagClient tagClient;

    public List<TagResponseDto> getAllTags() {
        return tagClient.getAllTags();
    }

    /**
     * 태그 등록
     *
     * @param requestDto
     */
    @Transactional
    public void createTag(TagRequestDto requestDto) {
        tagClient.createTag(requestDto);
    }

    /**
     * 태그 수정
     *
     * @param tagId
     * @param requestDto
     */
    @Transactional
    public void updateTag(Long tagId, TagRequestDto requestDto) {
        tagClient.updateTag(tagId, requestDto);
    }

    /**
     * 태그 단건조회
     *
     * @param tagId
     * @return
     */
    public TagResponseDto getTag(Long tagId) {
        return tagClient.getTag(tagId);
    }


    /**
     * 태그 삭제(비활성화)
     * @param tagId
     */
    @Transactional
    public void deleteTag(Long tagId) {
        tagClient.deleteTag(tagId);
    }
}
