package com.bee.controller;

import com.bee.models.User;
import com.bee.repository.UserRepository;
import com.bee.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")

public class TestController {
//    @Autowired
//    JwtUtils jwtUtils;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("@accessService.hasAccess(#session) and hasRole('USER')")
    public String userAccess(HttpSession session) {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("@accessService.hasAccess(#session) and hasRole('MODERATOR')")
    public String moderatorAccess(HttpSession session) {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("@accessService.hasAccess(#session) and hasRole('ADMIN')")
    public String adminAccess(HttpSession session) {
        return "Admin Board.";
    }

//	@GetMapping("/register")
//	public String register() {
//		return "Public Content.";
//	}
}


