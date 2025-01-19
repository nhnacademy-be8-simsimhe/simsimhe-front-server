package com.simsimbookstore.frontserver.point.service;

import com.simsimbookstore.frontserver.point.client.PointPolicyClient;
import com.simsimbookstore.frontserver.point.dto.PointPolicyRequestDto;
import com.simsimbookstore.frontserver.point.dto.PointPolicyResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointPolicyService {

    private final PointPolicyClient pointPolicyClient;


    public List<PointPolicyResponseDto> getAll() {
        return pointPolicyClient.getAll();
    }

    public PointPolicyResponseDto create(PointPolicyRequestDto dto){
        return pointPolicyClient.create(dto);
    }

    public PointPolicyResponseDto update(Long policyId,PointPolicyRequestDto dto){
        return pointPolicyClient.updatePolicy(policyId, dto);
    }

    public void delete(Long policyId){
        pointPolicyClient.deletePolicy(policyId);
    }

    public PointPolicyResponseDto getById(Long policyId){
        return pointPolicyClient.getById(policyId);
    }

}
