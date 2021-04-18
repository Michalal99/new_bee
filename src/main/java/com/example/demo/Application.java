package com.example.demo;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    //@GetMapping
    //public String hello(){
     //   return "Hello World!!!";
    //}

    @Autowired
    private UserDAO userDAO;

    @GetMapping
    public User addUser() {
        User user = new User("LandoNorris", "lnorris@mcl.com", "p3McL!", true);
        userDAO.addUser(user);
        return userDAO.getUser(user.getId());
    }
}