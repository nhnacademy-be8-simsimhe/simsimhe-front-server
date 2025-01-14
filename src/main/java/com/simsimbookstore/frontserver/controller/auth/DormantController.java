package com.simsimbookstore.frontserver.controller.auth;

import com.simsimbookstore.frontserver.users.dooray.service.DoorayService;
import com.simsimbookstore.frontserver.users.user.dto.UserResponse;
import com.simsimbookstore.frontserver.users.user.dto.UserStatus;
import com.simsimbookstore.frontserver.users.user.dto.UserStatusUpdateRequestDto;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class DormantController {
    private final DoorayService doorayService;
    private final UserService userService;

    @GetMapping("/dormant")
    public ModelAndView dormant(
            @RequestParam("userId") Long userId,
            HttpSession session
    ) {
        if (Objects.isNull(userId)){
            return new ModelAndView("redirect:/");
        }

        String code = doorayService.sendReleaseCode();
        ModelAndView modelAndView = new ModelAndView("/users/dormant");
        session.setAttribute("userId", userId);
        session.setAttribute("code", code);
//        modelAndView.addObject("code", code);
        return modelAndView;
    }

    @PostMapping("/dormant/verify")
    public ResponseEntity<?> dormantConfirm(
            @RequestParam("code") String inputCode,
            HttpSession session
    ) {
        Map<String, String> response = new HashMap<>();
        if (Objects.isNull(session)){
            response.put("status", "forbidden");
            response.put("message", "세션이 만료되었습니다.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        if (inputCode.equals(session.getAttribute("code"))){
            response.put("status", "success");
            response.put("message", "휴면 유저 해제 성공!");

            Long userId = (Long) session.getAttribute("userId");
            UserStatusUpdateRequestDto requestDto = UserStatusUpdateRequestDto.builder()
                    .status(UserStatus.ACTIVE)
                    .build();
            userService.updateUserStatus(userId, requestDto);
        }
        else{
            response.put("status", "failed");
            response.put("message", "잘못된 인증 번호입니다!");
        }

        return ResponseEntity.ok(response);
    }
}
