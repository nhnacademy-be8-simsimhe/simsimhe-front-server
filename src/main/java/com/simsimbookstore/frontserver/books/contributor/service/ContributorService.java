package com.simsimbookstore.frontserver.books.contributor.service;


import com.simsimbookstore.frontserver.books.contributor.client.ContributorClient;
import com.simsimbookstore.frontserver.books.contributor.dto.ContributorRequestDto;
import com.simsimbookstore.frontserver.books.contributor.dto.ContributorResponseDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContributorService {

    private final ContributorClient client;

    /**
     * 페이징 처리해서 모든 기여자 조회
     *
     * @param page
     * @param size
     * @return
     */
    public PageResponse<ContributorResponseDto> getAllContributorPage(int page, int size) {
        return client.getAllContributorPage(page, size);
    }

    public void createContributor(ContributorRequestDto contributorRequestDto) {
        client.createContributor(contributorRequestDto);
    }

    public void deleteContributor(Long contributorId) {
        client.deleteContributor(contributorId);
    }

    public void updateContributor(Long contributorId,ContributorRequestDto contributorRequestDto){
        client.updateContributor(contributorId,contributorRequestDto);
    }

    public ContributorResponseDto getContributor(Long contributorId){
        return client.getContributor(contributorId);
    }

}
