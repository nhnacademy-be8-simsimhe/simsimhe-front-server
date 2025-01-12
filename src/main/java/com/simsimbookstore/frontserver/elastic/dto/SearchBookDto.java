package com.simsimbookstore.frontserver.elastic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
@AllArgsConstructor
public class SearchBookDto {
    Long id;
    String title;
    String description;
    String author;
    List<String> tags;
    String publishedAt;
    long salePrice;
    long bookSellCount;
    long reviewCount;
}
