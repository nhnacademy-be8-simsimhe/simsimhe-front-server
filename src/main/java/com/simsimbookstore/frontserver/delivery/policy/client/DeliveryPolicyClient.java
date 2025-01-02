package com.simsimbookstore.frontserver.delivery.policy.client;

import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryPolicy;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryPolicyRequestDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "DeliveryPolicy-api-server", url = "http://localhost:8000/api/admin/delivery-policies")
public interface DeliveryPolicyClient {

    @GetMapping
    List<DeliveryPolicy> getAllDeliveryPolices();

    @PostMapping
    DeliveryPolicy createDeliveryPolicy(
            @RequestBody @Valid DeliveryPolicyRequestDto deliveryPolicyRequestDto);

    @PostMapping("/{id}/toggle")
    void toggleStandardPolicy(@PathVariable Long id);

    @DeleteMapping("/{id}")
    void deleteDeliveryPolicy(@PathVariable Long id);

}
