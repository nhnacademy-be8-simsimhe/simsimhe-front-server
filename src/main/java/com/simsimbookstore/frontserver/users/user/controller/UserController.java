package com.simsimbookstore.frontserver.users.user.controller;


import com.simsimbookstore.frontserver.users.user.request.LocalUserRequest;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class UserController {
    private final UserService userService;

//    @GetMapping("/{userId}/view")
//    public String findUser(@PathVariable Long userId) {
//        String s = userService.findUserByUserId(userId);
//        return "index";
//    }


    @PostMapping("/localUser/register")
    public ResponseEntity<?> registerUser(
            @ModelAttribute LocalUserRequest localUserRequest,
            HttpServletResponse response
    ){
        boolean isExists = userService.existsByLoginId(localUserRequest.getLoginId());
        if (isExists) {
            Map<String,String> errorMap = new HashMap<>();
            errorMap.put("error","이미 존재하는 아이디입니다.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMap);
        }

        String s = userService.addLocalUser(localUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



//    // 테스트 코드
//    @PostMapping("jwt/{loginId}")
//    public String generateJwt(@PathVariable String loginId) {
//        String jwt = userService.generateJwt(loginId);
//        return jwt;
//    }
}
