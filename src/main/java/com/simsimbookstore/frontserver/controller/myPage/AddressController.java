package com.simsimbookstore.frontserver.controller.myPage;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.address.dto.AddressRequestDto;
import com.simsimbookstore.frontserver.users.address.dto.AddressResponseDto;
import com.simsimbookstore.frontserver.users.address.service.AddressService;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/users/myPage/address")
@Controller
public class AddressController {
    private final AddressService addressService;

    // 주소 목록 조회
    @GetMapping("/addressInfo")
    public ModelAndView getAddressInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            HttpServletRequest request
    ) {
        ModelAndView modelAndView = new ModelAndView("users/myPage/address/addressInfo");
        List<AddressResponseDto> address = addressService.getAddress(customUserDetails.getUserId());

        modelAndView.addObject("addresses", address);
        modelAndView.addObject("requestURI", request.getRequestURI());
        return modelAndView;
    }

    // 주소 추가 폼
    @GetMapping("/register")
    public String registerForm() {
        return "users/myPage/address/addressRegisterForm";
    }

    // 주소 추가
    @PostMapping("/register")
    public ResponseEntity<?> addAddressInfo(
            @ModelAttribute AddressRequestDto addressRequestDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Map<String, String> response = new HashMap<>();

        try {
            // Backend 호출: 성공 시 AddressResponseDto 반환
            AddressResponseDto addressResponseDto = addressService.addAddress(customUserDetails.getUserId(), addressRequestDto);
            response.put("status","success");
            response.put("url", "/users/myPage/address/addressInfo");
            return ResponseEntity.ok(response);

        } catch (FeignException.BadRequest e) {
            String errorMessage = e.contentUTF8();
            response.put("status", "forbidden");
            response.put("message", errorMessage);
            response.put("url", "/users/myPage/address/addressInfo");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {
            // 기타 에러 처리
            response.put("status", "failed");
            response.put("url", "/users/myPage/address/addressInfo");
            response.put("message", "알 수 없는 오류가 발생했습니다. 다시 시도해주세요.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/delete")
    public String addressDelete(@RequestParam Long addressId) {
        addressService.deleteAddress(addressId);
        return "redirect:/users/myPage/address/addressInfo";
    }
}
