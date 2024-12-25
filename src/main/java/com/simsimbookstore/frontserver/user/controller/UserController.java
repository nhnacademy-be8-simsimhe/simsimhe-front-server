package com.simsimbookstore.frontserver.user.controller;


import com.simsimbookstore.frontserver.user.request.LocalUserRequest;
import com.simsimbookstore.frontserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String registerForm() {
        return "userRegisterForm";
    }

    @GetMapping("/{userId}/view")
    public String findUser(@PathVariable Long userId) {
        String s = userService.findUserByUserId(userId);

        return "index";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute LocalUserRequest localUserRequest){
        String s = userService.addLocalUser(localUserRequest);
        log.info("user info : {}",s);
        return "index";
    }

    // 테스트 코드
    @PostMapping("jwt/{loginId}")
    public String generateJwt(@PathVariable String loginId) {
        String jwt = userService.generateJwt(loginId);
        return jwt;
    }
}
