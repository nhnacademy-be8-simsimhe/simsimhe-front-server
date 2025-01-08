package com.simsimbookstore.frontserver.controller.myPage;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.address.dto.AddressRequestDto;
import com.simsimbookstore.frontserver.users.address.dto.AddressResponseDto;
import com.simsimbookstore.frontserver.users.address.service.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
    public String addAddressInfo(
            @ModelAttribute AddressRequestDto addressRequestDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        AddressResponseDto addressResponseDto = addressService.addAddress(customUserDetails.getUserId(), addressRequestDto);

        return "redirect:/users/myPage/address/addressInfo";
    }

    @PostMapping("/delete")
    public String addressDelete(@RequestParam Long addressId) {
        addressService.deleteAddress(addressId);
        return "redirect:/users/myPage/address/addressInfo";
    }
}
