//package com.bezkoder.spring.security.postgresql.controllers;
//
//import com.bezkoder.spring.security.postgresql.payload.request.SignupRequest;
//import com.bezkoder.spring.security.postgresql.repository.RoleRepository;
//import com.bezkoder.spring.security.postgresql.repository.UserRepository;
//import com.bezkoder.spring.security.postgresql.security.jwt.JwtUtils;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.mock;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(AuthController.class)
//class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
//
//    private final RoleRepository roleRepository = mock(RoleRepository.class);
//
//    private final UserRepository userRepository = mock(UserRepository.class);
//
//    private final PasswordEncoder encoder = mock(PasswordEncoder.class);
//
//    private final JwtUtils jwtUtils = mock(JwtUtils.class);
//
//    @Test
//    void shouldFailRegistrationIfUserExists() throws Exception {
//
//        //given
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        String pasozyd = "pasozyd";
//        doReturn(false).when(userRepository).existsByUsername(pasozyd);
//
//        SignupRequest signupRequest = new SignupRequest();
//        signupRequest.setUsername(pasozyd);
//
//        //when & then
//        MvcResult mvcResult = mockMvc.perform(post("/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(signupRequest))
//        )
//                .andExpect(status().isBadRequest())
//                .andReturn();
//
//        System.out.println(mvcResult.getResponse());
//    }
//}