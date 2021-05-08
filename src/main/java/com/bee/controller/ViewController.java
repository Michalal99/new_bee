package com.bee.controller;

import com.bee.payload.request.LoginRequest;
import com.bee.payload.request.PasswordChangeRequest;
import com.bee.payload.request.SignupRequest;
import com.bee.repository.PasswordResetTokenRepository;
import com.bee.security.PasswordTokenSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@RestController
//@Controller
@RequestMapping("/api/auth")
public class ViewController {


@Autowired
PasswordTokenSecurity passwordTokenSecurity;

    @GetMapping("/login")
    public ModelAndView getWelcomePageAsModel(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/signup")
    public ModelAndView getSignUpView(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup");
        return modelAndView;
    }
    @GetMapping("/changePassword")
    public ModelAndView showChangePasswordPage(Model model, @RequestParam("token") String token, HttpSession session) {

        String result = passwordTokenSecurity.validatePasswordResetToken(token);
        if(result != null)
        {
            model.addAttribute("loginRequest", new LoginRequest());
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("login");
            return modelAndView;
        }
        session.setAttribute("changePasswordToken",token); // trzymaj token dla raporatowania bledow
        model.addAttribute("updatePassword", new PasswordChangeRequest());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("updatePassword");
        return modelAndView;

    }
}