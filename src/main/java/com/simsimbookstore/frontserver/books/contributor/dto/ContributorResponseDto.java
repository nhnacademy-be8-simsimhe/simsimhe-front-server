package com.simsimbookstore.frontserver.books.contributor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContributorResponseDto {

    @JsonProperty("contributorId")
    private Long contributorId;

    @JsonProperty("contributorName")
    private String contributorName;

    @JsonProperty("contributorRole")
    private String contributorRole;
}