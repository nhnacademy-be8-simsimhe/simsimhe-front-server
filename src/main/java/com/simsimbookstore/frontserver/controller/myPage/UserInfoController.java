package com.simsimbookstore.frontserver.controller.myPage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("users/myPage")
@Controller
public class UserInfoController {

    @GetMapping("/userInfo")
    public String userInfo() {
        return "/users/myPage/userInfo";
    }
}
