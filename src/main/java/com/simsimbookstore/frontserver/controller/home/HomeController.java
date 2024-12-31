package com.simsimbookstore.frontserver.controller.home;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping()
@Controller

public class HomeController {

    @GetMapping({"/index","/"})
    public ModelAndView index(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("isAuthenticated", Objects.nonNull(principal));
        return modelAndView;
    }
}
