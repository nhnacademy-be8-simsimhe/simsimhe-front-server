package com.simsimbookstore.frontserver.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contributor {

    private Long contributorId;

    private String contributorName;

    private String contributorRole;
}
