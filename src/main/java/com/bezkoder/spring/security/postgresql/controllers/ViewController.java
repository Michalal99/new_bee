package com.bezkoder.spring.security.postgresql.controllers;

import com.bezkoder.spring.security.postgresql.payload.request.LoginRequest;
import com.bezkoder.spring.security.postgresql.payload.request.SignupRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.HttpURLConnection;

@RestController
//@Controller
@RequestMapping("/api/auth")
public class ViewController {


    //    @GetMapping("login")
//    public ModelAndView getWelcomePageAsModel() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("test");
//        return modelAndView;
//    }
    @GetMapping("/login")
    public ModelAndView getWelcomePageAsModel(Model model) {
        model.addAttribute("loginRequest",new LoginRequest());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
    @GetMapping("/signup")
    public ModelAndView getSignUpView(Model model) {
        model.addAttribute("signupRequest",new SignupRequest());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup");
        return modelAndView;
    }


//    @PostMapping("catch")
//    public String postBody(@RequestBody String fullname)
//    {
//        return "Hello" + fullname;
//    }

}