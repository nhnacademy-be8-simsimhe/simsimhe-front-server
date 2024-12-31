package com.simsimbookstore.frontserver.books.contributor.client;

import com.simsimbookstore.frontserver.books.contributor.dto.ContributorRequestDto;
import com.simsimbookstore.frontserver.books.contributor.dto.ContributorResponseDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "contributor-api-server", url = "http://localhost:8000/api/admin/contributors")
public interface ContributorClient {

    /**
     * 페이징 처리해서 모든 기여자 조회
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    PageResponse<ContributorResponseDto> getAllContributorPage(@RequestParam("page") int page, @RequestParam("size") int size);

    @GetMapping("/list")
    List<ContributorResponseDto> getAllContributers();

    /**
     * 기여자 등록
     *
     * @param contributorRequestDto
     * @return
     */
    @PostMapping
    ContributorResponseDto createContributor(@RequestBody ContributorRequestDto contributorRequestDto);


    /**
     * 기여자 삭제 도서에 등록되어있는 기여자는 외래키 제약조건 때문에 삭제안됌
     * @param contributorId
     */
    @DeleteMapping("/{contributorId}")
    void deleteContributor(@PathVariable("contributorId") Long contributorId);


    /**
     * 기여자수정
     * @param contributorId
     * @param contributorRequestDto
     * @return
     */
    @PutMapping("/{contributorId}")
    ContributorResponseDto updateContributor(@PathVariable("contributorId") Long contributorId,@RequestBody ContributorRequestDto contributorRequestDto);


    /**
     * 기여자 단건조회
     * @param contributorId
     * @return
     */
    @GetMapping("/{contributorId}")
    ContributorResponseDto getContributor(@PathVariable("contributorId") Long contributorId);

}
