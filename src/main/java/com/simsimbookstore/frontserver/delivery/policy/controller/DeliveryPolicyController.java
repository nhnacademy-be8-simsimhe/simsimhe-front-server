package com.simsimbookstore.frontserver.delivery.policy.controller;


import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryPolicy;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryPolicyRequestDto;
import com.simsimbookstore.frontserver.delivery.policy.service.DeliveryPolicyService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/delivery-policies")
public class DeliveryPolicyController {

    private final DeliveryPolicyService deliveryPolicyService;

    /**
     * 모든 배달 정책 조회
     */
    @GetMapping
    public String getAllDeliveryPolicies(Model model) {
        List<DeliveryPolicy> deliveryPolicies = deliveryPolicyService.findAll();
        model.addAttribute("deliveryPolicies", deliveryPolicies);
        return "admin/delivery/deliveryPolicyList";
    }

    /**
     * 배달 정책 추가 폼
     */
    @GetMapping("/create")
    public String createDeliveryPolicyForm(Model model) {
        model.addAttribute("deliveryPolicy", new DeliveryPolicyRequestDto());
        return "admin/delivery/deliveryPolicyCreate";
    }


    /**
     * 배달 정책 추가
     */
    @PostMapping("/create")
    public String createDeliveryPolicy(@ModelAttribute("deliveryPolicy") @Valid DeliveryPolicyRequestDto requestDto,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/delivery/deliveryPolicyCreate";
        }
        deliveryPolicyService.createDeliveryPolicy(requestDto);
        return "redirect:/admin/delivery-policies";
    }

    /**
     * 배달 정책 활성/비활성화
     */
    @PostMapping("/toggle/{id}")
    public String toggleDeliveryPolicy(@PathVariable("id") Long id) {
        deliveryPolicyService.toggleDeliveryPolicy(id);
        return "redirect:/admin/delivery-policies";
    }

    /**
     * 배달 정책 삭제
     */
    @PostMapping("/delete/{id}")
    public String deleteDeliveryPolicy(@PathVariable("id") Long id) {
        deliveryPolicyService.deleteDeliveryPolicy(id);
        return "redirect:/admin/delivery-policies";
    }
}
