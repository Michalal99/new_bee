package com.bezkoder.spring.security.postgresql.controllers;

import java.util.List;

import com.bezkoder.spring.security.postgresql.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("api/")
public class UserController {

//    @Autowired
//    private User user;

//    @GetMapping("users")
//    public List<User> getUsers() {
//        return user.getUsers();
//    }

}
