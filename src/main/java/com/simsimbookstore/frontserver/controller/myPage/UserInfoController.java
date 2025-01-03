package com.simsimbookstore.frontserver.controller.myPage;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.user.dto.UserResponse;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RequestMapping("users/myPage/user")
@Controller
public class UserInfoController {
    private final UserService userService;

    @GetMapping("/userInfo")
    public ModelAndView userInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            HttpServletRequest request
    ) {
        ModelAndView modelAndView = new ModelAndView("/users/myPage/user/userInfo");


        UserResponse user = userService.findUserByUserId(customUserDetails.getUserId());
        modelAndView.addObject("user", user);
        modelAndView.addObject("requestURI", request.getRequestURI());

        return modelAndView;
    }
}
