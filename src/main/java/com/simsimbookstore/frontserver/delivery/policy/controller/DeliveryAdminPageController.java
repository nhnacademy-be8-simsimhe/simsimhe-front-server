package com.simsimbookstore.frontserver.delivery.policy.controller;

import com.simsimbookstore.frontserver.delivery.policy.client.DeliveryAdminClient;
import com.simsimbookstore.frontserver.delivery.policy.dto.Delivery;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryDetailResponseDto;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryResponseDto;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryStateUpdateRequestDto;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryTrackingNumberRequestDto;
import com.simsimbookstore.frontserver.order.dto.DeliveryRequestDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/deliveries")
@RequiredArgsConstructor
public class DeliveryAdminPageController {

    private final DeliveryAdminClient deliveryAdminClient;

    /**
     * 전체 목록 조회 + 페이지네이션
     */
    @GetMapping
    public String getAllDeliveries(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "15") int size,
                                   Model model) {
        PageResponse<DeliveryResponseDto> pageResponse = deliveryAdminClient.findAll(page, size);
        model.addAttribute("pageResponse", pageResponse);
        return "admin/delivery/deliveryList";  // Thymeleaf 템플릿명
    }

    /**
     * 상태별 필터 조회
     */
    @GetMapping("/filter")
    public String getDeliveriesByState(@RequestParam("state") Delivery.DeliveryState state,
                                       @RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "15") int size,
                                       Model model) {
        PageResponse<DeliveryResponseDto> pageResponse = deliveryAdminClient.getDeliveriesByState(state, page, size);
        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("filterState", state);
        return "admin/delivery/deliveryList";  // 동일 뷰 재사용
    }

    /**
     * 상세 조회 페이지
     */
    @GetMapping("/{id}")
    public String getDeliveryDetail(@PathVariable Long id, Model model) {
        DeliveryDetailResponseDto detailDto = deliveryAdminClient.findById(id);
        model.addAttribute("detail", detailDto);
        return "admin/delivery/deliveryDetail";  // Thymeleaf 템플릿명
    }

    /**
     * 상태 변경 (POST Form submit)
     */
    @PostMapping("/{id}/update-state")
    public String updateDeliveryState(@PathVariable Long id,
                                      @RequestParam("newState") Delivery.DeliveryState newState) {
        DeliveryStateUpdateRequestDto requestDto = new DeliveryStateUpdateRequestDto(newState);
        deliveryAdminClient.updateState(id, requestDto);

        // 업데이트 후 상세 페이지로 리다이렉트
        return "redirect:/admin/deliveries/" + id;
    }

    /**
     * 트래킹 번호로 단건 조회
     */
    @GetMapping("/tracking")
    public String getByTrackingPage() {
        // 단순 조회 폼만 보여주는 페이지
        return "admin/delivery/trackingSearch";
    }

    @PostMapping("/tracking")
    public String findByTrackingNumber(@RequestParam Integer trackingNumber, Model model) {
        DeliveryResponseDto dto = deliveryAdminClient.findByTrackingNumber(trackingNumber);
        model.addAttribute("delivery", dto);
        return "admin/delivery/trackingResult";
    }

    /**
     * 트래킹 번호 수정 폼 페이지 (GET)
     */
    @GetMapping("/{id}/tracking-form")
    public String getTrackingForm(@PathVariable Long id, Model model) {
        // 상세 조회해서 현재 트래킹 번호를 표시해줄 수도 있음
        DeliveryDetailResponseDto delivery = deliveryAdminClient.findById(id);
        model.addAttribute("delivery", delivery);
        return "admin/delivery/trackingForm"; // Thymeleaf 템플릿
    }

    /**
     * 트래킹 번호 수정 (POST)
     */
    @PostMapping("/{id}/tracking-form")
    public String updateTrackingNumber(
            @PathVariable Long id,
            @RequestParam("trackingNumber") Integer trackingNumber) {

        DeliveryTrackingNumberRequestDto requestDto = new DeliveryTrackingNumberRequestDto(trackingNumber);
        deliveryAdminClient.updateTrackingNumber(id, requestDto);

        // 수정 후 상세 페이지로 리다이렉트
        return "redirect:/admin/deliveries/" + id;
    }
}
