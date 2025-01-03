package com.simsimbookstore.frontserver.controller.auth;


import com.simsimbookstore.frontserver.users.localUser.dto.LocalUserRegisterRequestDto;
import com.simsimbookstore.frontserver.users.localUser.service.LocalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class RegisterController {
    private final LocalUserService localUserService;

    @PostMapping("/localUser/register")
    public ResponseEntity<?> registerUser(
            @ModelAttribute LocalUserRegisterRequestDto localUserRegisterRequestDto
    ){
        boolean isExists = localUserService.existsByLoginId(localUserRegisterRequestDto.getLoginId());
        if (isExists) {
            Map<String,String> errorMap = new HashMap<>();
            errorMap.put("error","이미 존재하는 아이디입니다.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMap);
        }

        String s = localUserService.addLocalUser(localUserRegisterRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
