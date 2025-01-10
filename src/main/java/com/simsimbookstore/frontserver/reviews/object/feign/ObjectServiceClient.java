package com.simsimbookstore.frontserver.reviews.object.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(value = "objectApi", url = "http://localhost:8000/api/shop/objects")
public interface ObjectServiceClient {

    @PostMapping(value = "/upload-file" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<String> uploadObjects(@RequestPart("file") List<MultipartFile> files);
}
