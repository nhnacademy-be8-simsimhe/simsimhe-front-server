package com.simsimbookstore.frontserver.controller.myPage;

import com.simsimbookstore.frontserver.point.dto.PointHistoryResponseDto;
import com.simsimbookstore.frontserver.point.service.PointHistoryService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.user.dto.UserResponse;
import com.simsimbookstore.frontserver.users.user.dto.UserStatus;
import com.simsimbookstore.frontserver.users.user.dto.UserStatusUpdateRequestDto;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import com.simsimbookstore.frontserver.util.PageResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RequestMapping("/users/myPage/user")
@Controller
public class UserInfoController {
    private final UserService userService;
    private final PointHistoryService pointHistoryService;

    @GetMapping("/userInfo")
    public ModelAndView userInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            HttpServletRequest request
    ) {
        ModelAndView modelAndView = new ModelAndView("users/myPage/user/userInfo");

        BigDecimal points = pointHistoryService.getPoints(customUserDetails.getUserId());
        UserResponse user = userService.findUserByUserId(customUserDetails.getUserId());
        modelAndView.addObject("user", user);
        modelAndView.addObject("requestURI", request.getRequestURI());
        modelAndView.addObject("points", points);

        return modelAndView;
    }

    @PostMapping("/userInfo/quit")
    public String userStatusUpdate(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam UserStatus userStatus
    ) {
        UserStatusUpdateRequestDto userStatusUpdateRequestDto = UserStatusUpdateRequestDto.builder()
                .status(userStatus)
                .build();
        userService.updateUserStatus(customUserDetails.getUserId(), userStatusUpdateRequestDto);
        return "redirect:/logout";
    }

    @GetMapping("/points")
    public String pointsHistory(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "15") int size,
                                Model model,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUserId();
        size = 15;
        // 포인트 히스토리 데이터 가져오기
        PageResponse<PointHistoryResponseDto> pointHistory = pointHistoryService.getPointHistory(userId, page, size);


        model.addAttribute("pointHistoryBody", pointHistory);
        return "users/myPage/points";
    }
}
