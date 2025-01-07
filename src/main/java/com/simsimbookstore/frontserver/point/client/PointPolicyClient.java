package com.simsimbookstore.frontserver.point.client;

import com.simsimbookstore.frontserver.point.dto.PointPolicyRequestDto;
import com.simsimbookstore.frontserver.point.dto.PointPolicyResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PointPolicy-api-server", url = "http://localhost:8000/api/admin/pointPolicies")
public interface PointPolicyClient {
    @GetMapping
    List<PointPolicyResponseDto> getAll();

    @GetMapping("/{policyId}")
    PointPolicyResponseDto getById(@PathVariable("policyId") Long policyId);

    @PostMapping
    PointPolicyResponseDto create(@RequestBody PointPolicyRequestDto requestDto);

    @PostMapping("/{policyId}")
    PointPolicyResponseDto updatePolicy(
            @PathVariable Long policyId,
            @RequestBody PointPolicyRequestDto requestDto);

    @DeleteMapping("/{policyId}")
    Void deletePolicy(@PathVariable Long policyId);

    @GetMapping("/{policyId}")
    PointPolicyResponseDto getPolicyById(@PathVariable Long policyId);
}
