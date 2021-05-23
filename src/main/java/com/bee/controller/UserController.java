package com.bee.controller;

import com.bee.models.User;
import com.bee.repository.RoleRepository;
import com.bee.repository.UserRepository;
import com.bee.security.jwt.JwtUtils;
import com.bee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    @GetMapping
    public String showUser(Model model , HttpSession session){
        var y = session.getAttribute("token");
        var userName = jwtUtils.getUserNameFromJwtToken((String)y);
        var user = userService.findUserByUserName(userName);
        model.addAttribute("user", user);

        return "user";
    }
}
