package com.simsimbookstore.frontserver.controller.myPage;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.user.dto.UserResponse;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RequestMapping("users/myPage")
@Controller
public class UserInfoController {
    private final UserService userService;

    @GetMapping("/userInfo")
    public ModelAndView userInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        UserResponse user = userService.findUserByUserId(customUserDetails.getUserId());

        ModelAndView modelAndView = new ModelAndView("/users/myPage/userInfo");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
