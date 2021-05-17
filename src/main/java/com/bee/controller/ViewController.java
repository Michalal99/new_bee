package com.bee.controller;

import com.bee.payload.request.LoginRequest;
import com.bee.payload.request.PasswordChangeRequest;
import com.bee.payload.request.SignupRequest;
import com.bee.repository.PasswordResetTokenRepository;
import com.bee.repository.RegisterConfirmationTokenRepository;
import com.bee.repository.UserRepository;
import com.bee.security.PasswordTokenSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Locale;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
//@Controller

@RequestMapping("/api/auth")
public class ViewController {


@Autowired
PasswordTokenSecurity passwordTokenSecurity;

@Autowired
    RegisterConfirmationTokenRepository registerRepo;

@Autowired
    UserRepository userRepo;

//    @GetMapping("/mod")
//    @PreAuthorize("@accessService.hasAccess(#session) and hasRole('MODERATOR')")
//    public String moderatorAccess(HttpSession session) {
//        return "Moderator Board.";
//    }


    @GetMapping("/login")
    public ModelAndView getWelcomePageAsModel(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Login&Register/login");
        return modelAndView;
    }

    @GetMapping("/signup")
    public ModelAndView getSignUpView(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Login&Register/signup");
        return modelAndView;
    }


    @GetMapping("/changePassword")
    public ModelAndView showChangePasswordPage(Model model, @RequestParam("token") String token, HttpSession session) {

        String result = passwordTokenSecurity.validatePasswordResetToken(token);
        if(result != null)
        {
            model.addAttribute("loginRequest", new LoginRequest());
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("Login&Register/login");
            return modelAndView;
        }
        session.setAttribute("changePasswordToken",token); // trzymaj token dla raporatowania bledow
        model.addAttribute("updatePassword", new PasswordChangeRequest());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Login&Register/updatePassword");
        return modelAndView;

    }

    @GetMapping("/register")
    public ModelAndView confirmRegistration(Model model, @RequestParam("token") String token, HttpSession session) {

//        String result = passwordTokenSecurity.validatePasswordResetToken(token);
//        if(result != null)
//        {
//            model.addAttribute("loginRequest", new LoginRequest());
//            ModelAndView modelAndView = new ModelAndView();
//            modelAndView.setViewName("login");
//            return modelAndView;
//        }

        //session.setAttribute("registerToken",token); // trzymaj token dla raporatowania bledow
        //model.addAttribute("login", new PasswordChangeRequest());
        var user = registerRepo.findByToken(token).getUser();
        user.setAuthorized(true);
        userRepo.save(user);
        registerRepo.deleteByUser(user);
        model.addAttribute("loginRequest", new LoginRequest());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Login&Register/login");
        return modelAndView;

    }
}