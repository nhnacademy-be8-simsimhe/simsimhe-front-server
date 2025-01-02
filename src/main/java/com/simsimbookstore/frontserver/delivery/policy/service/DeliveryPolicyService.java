package com.simsimbookstore.frontserver.delivery.policy.service;

import com.simsimbookstore.frontserver.delivery.policy.client.DeliveryPolicyClient;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryPolicy;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryPolicyRequestDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryPolicyService {

    private final DeliveryPolicyClient deliveryPolicyClient;


    public List<DeliveryPolicy> findAll() {
        return deliveryPolicyClient.getAllDeliveryPolices();
    }

    public DeliveryPolicy createDeliveryPolicy(DeliveryPolicyRequestDto deliveryPolicyRequestDto) {
        return deliveryPolicyClient.createDeliveryPolicy(deliveryPolicyRequestDto);
    }

    public void toggleDeliveryPolicy(Long id) { deliveryPolicyClient.toggleStandardPolicy(id); }

    public void deleteDeliveryPolicy(Long id) { deliveryPolicyClient.deleteDeliveryPolicy(id); }

}
