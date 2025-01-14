package com.simsimbookstore.frontserver.elastic.client;
import com.simsimbookstore.frontserver.elastic.dto.SearchBookDto;

import com.simsimbookstore.frontserver.util.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "elasticsearch-api-server", url = "http://localhost:8000/api/shop/elastic")
public interface ElasticsearchClient {

    @PostMapping("/document")
    public void saveData(@RequestBody SearchBookDto searchBookDto);

    @GetMapping("/document")
    public PageResponse<SearchBookDto> getDatas(@RequestParam String keyword, @RequestParam(required = false) String sort, @RequestParam(defaultValue = "0") int page);

    @DeleteMapping("/document/{id}")
    public void deleteData(@PathVariable String id);



}
