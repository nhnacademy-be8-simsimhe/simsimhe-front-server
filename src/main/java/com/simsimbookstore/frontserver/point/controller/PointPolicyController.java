package com.simsimbookstore.frontserver.point.controller;

import com.simsimbookstore.frontserver.point.dto.PointPolicy;
import com.simsimbookstore.frontserver.point.dto.PointPolicyRequestDto;
import com.simsimbookstore.frontserver.point.dto.PointPolicyResponseDto;
import com.simsimbookstore.frontserver.point.service.PointPolicyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/pointPolicies")
public class PointPolicyController {

    private final PointPolicyService pointPolicyService;

    @GetMapping
    public String getAllPolicies(Model model) {
        List<PointPolicyResponseDto> policies = pointPolicyService.getAll();
        System.out.println("Policies: " + policies); // 디버깅: 데이터 확인
        model.addAttribute("policies", policies);
        return "admin/point/policy/list";
    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("policy", new PointPolicyRequestDto());
        model.addAttribute("earningMethods", PointPolicy.EarningMethod.values());
        model.addAttribute("earningTypes", PointPolicy.EarningType.values());
        return "admin/point/policy/create";
    }

    @PostMapping
    public String createPolicy(@ModelAttribute PointPolicyRequestDto dto) {
        pointPolicyService.create(dto);
        return "redirect:/admin/pointPolicies";

    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        PointPolicyResponseDto policy = pointPolicyService.getById(id);
        model.addAttribute("policy", policy);
        model.addAttribute("earningMethods", PointPolicy.EarningMethod.values());
        model.addAttribute("earningTypes", PointPolicy.EarningType.values());
        return "admin/point/policy/edit";
    }

    @PostMapping("/update/{id}")
    public String updatePolicy(@PathVariable Long id,
                               @ModelAttribute("policy") PointPolicyRequestDto dto) {
        // 디버깅
        System.out.println("Received isAvailable = " + dto.isAvailable());
        // 서비스 호출
        pointPolicyService.update(id, dto);
        return "redirect:/admin/pointPolicies";
    }

    @PostMapping("/delete/{id}")
    public String deletePolicy(@PathVariable Long id) {
        pointPolicyService.delete(id);
        return "redirect:/admin/pointPolicies";

    }
}
