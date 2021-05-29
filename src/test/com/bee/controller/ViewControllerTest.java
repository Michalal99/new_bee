package com.bee.controller;

//import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.bee.models.ERole;
import com.bee.models.Role;
import com.bee.models.User;
import com.bee.payload.request.LoginRequest;
import com.bee.payload.request.SignupRequest;
import com.bee.repository.RegisterConfirmationTokenRepository;
import com.bee.repository.UserRepository;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ViewControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userRepo;

    @Autowired
    RegisterConfirmationTokenRepository registerTokenRepo;


    @Test
    void getLoginView() throws Exception {
        //   var request = get("/api/auth/login");

        var result = mvc.perform(get("/api/auth/login")).andExpect(status().is(200)).andReturn();
        //var test = result.getModelAndView().;
      //  var test = result.getResponse().getContentAsString();
        assertEquals("Login&Register/login", Objects.requireNonNull(result.getModelAndView()).getViewName());
        var loginRequest = (LoginRequest)(Objects.requireNonNull(result.getModelAndView()).getModelMap().get("loginRequest"));
        assertNull(loginRequest.getPassword());
        assertNull(loginRequest.getUsername());
        assertTrue( result.getResponse().getContentAsString().contains("Username"));
        assertTrue( result.getResponse().getContentAsString().contains("Password"));
        assertTrue( result.getResponse().getContentAsString().contains("submit"));

       // var test = result;
    }

    @Test
    void getSignUpView() throws Exception{
        var result = mvc.perform(get("/api/auth/signup")).andExpect(status().is(200)).andReturn();
        var response = result.getResponse().getContentAsString();
        assertEquals("Login&Register/signup", Objects.requireNonNull(result.getModelAndView()).getViewName());
        var signupRequest = (SignupRequest) Objects.requireNonNull(result.getModelAndView()).getModel().get("signupRequest");
        assertNull(signupRequest.getPassword_confirm());
        assertNull(signupRequest.getEmail());
        assertNull(signupRequest.getPassword());
        assertNull(signupRequest.getUsername());

        assertTrue(response.contains("Username"));
        assertTrue(response.contains("Password"));
        assertTrue(response.contains("Email"));
        assertTrue(response.contains("Password Confirm"));
        assertTrue(response.contains("Sign in"));
    }

    @Test
    void SignupUser() throws Exception{
        userRepo.deleteAll();
        var admin = new User();
        admin.setUsername("admin");
        admin.setEmail("email@email");
        admin.setPassword("123456789Ab!");

        String password = admin.getPassword();
       // Mockito.when(userRepo.save(any(User.class))).thenReturn(user);
       var result = mvc.perform(post("/api/auth/signup")
            .param("username",admin.getUsername())
            .param("email",admin.getEmail())
            .param("password",admin.getPassword())
            .param("password_confirm",admin.getPassword())
       ).andExpect(status().is(302)).andExpect(redirectedUrl("/api/auth/login")).andReturn();
        assertTrue(userRepo.existsByUsername(admin.getUsername()));
        admin = userRepo.findByUsername(admin.getUsername()).orElse(null);
        assertNotNull(admin);
        assertTrue(admin.getRoles().stream().anyMatch(n->n.getName().toString().equals("ROLE_ADMIN")));
        assertFalse(admin.isAuthorized());

        mvc.perform(get("/admin/access")).andExpect(status().is(401)); // próba dostania sie do admina bez logowania
        var authToken= loginUser(admin.getUsername(), password);
        mvc.perform(get("/admin/access").sessionAttr("token",authToken)).andExpect(status().is(401)); // proba dostania sie do admina bez autoryazacji
        String token = registerTokenRepo.findByUser(admin).getToken();
        mvc.perform(get("/api/auth/register?token="+token)).andExpect(status().is(200));
        admin = userRepo.findByUsername(admin.getUsername()).orElse(null);
        assertTrue(admin.isAuthorized());

         authToken = loginUser(admin.getUsername(), password);
        mvc.perform(get("/admin/access").sessionAttr("token",authToken)).andExpect(status().is(200)); // wejscie na admina z autoryzacja
        //mvc.perform(get())


   //    var test = result.getResponse().getStatus();
   //    verify(userRepo).save(user);//,times(0));
   //     var check= 0;
    }

    String loginUser(String username, String password) throws Exception{

        ;
        var result = mvc.perform(post("/api/auth/login")
        .param("username",username)
        .param("password",password)).andExpect(status().is(302)).andReturn();
        assertEquals(username,result.getRequest().getSession().getAttribute("username"));
        return (String)result.getRequest().getSession().getAttribute("token");

    }
}