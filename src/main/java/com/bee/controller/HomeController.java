package com.bee.controller;

import com.bee.security.jwt.JwtUtils;
import com.bee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        var y = session.getAttribute("token");
        var userActive = false;
        if(y != null){
            userActive = true;
        }

        model.addAttribute("userActive", userActive);

        return "home";
    }
}
