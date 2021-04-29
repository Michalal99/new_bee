package com.bee.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.bee.models.ERole;
import com.bee.models.Role;
import com.bee.models.User;
import com.bee.payload.request.LoginRequest;
import com.bee.payload.request.SignupRequest;
import com.bee.repository.RoleRepository;
import com.bee.repository.UserRepository;
import com.bee.security.constraint.ValidPassword;
import com.bee.security.jwt.JwtUtils;
import com.bee.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @ValidPassword
    String pass;

//    @ValidPassword
//    String pass;

    //public ResponseEntity<?> logout ()
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logoutPage(HttpSession session ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var test = session.getId();
        session.invalidate();
        return "redirect:/api/auth/login";
    }
    @RequestMapping(value="/welcome", method = RequestMethod.GET)
    public String printWelcome(ModelMap model   ) {

        var y = model.get("token");
        var user = jwtUtils.getUserNameFromJwtToken((String)y);
        return "hello";
    }


    @PostMapping("/signin")
    //public ResponseEntity<?> authenticateUser(@Valid @RequestBody String httpResponse, ModelMap user)
   // public String authenticateUser(@Valid @RequestBody String httpResponse, RedirectAttributes redirectAttributes,HttpSession session)
    public String authenticateUser(@ModelAttribute("loginRequest")  LoginRequest loginRequest, RedirectAttributes redirectAttributes,HttpSession session )
    {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        //redirectAttributes.addAttribute("username",username);
        redirectAttributes.addFlashAttribute("token",jwt);
        redirectAttributes.addFlashAttribute("username",loginRequest.getUsername());
        session.setAttribute("username",loginRequest.getUsername());
        session.setAttribute("token",jwt);
        return "redirect:/api/auth/welcome";

    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("signupRequest") @Valid SignupRequest signUpRequest, RedirectAttributes redirectAtributes)
    {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            redirectAtributes.addFlashAttribute("error","Error: Username is already taken!");
            return "redirect:/api/auth/signup";
        }
//
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            redirectAtributes.addFlashAttribute("error","Error: Email is already in use!");
            return "redirect:/api/auth/signup";
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));


        Set<String> strRoles = new HashSet<>();
        strRoles.add(signUpRequest.getRole());
        signUpRequest.setRoles(strRoles);
        // brzydki fragment do poprawienia

        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {

            strRoles.forEach(rol -> {
                switch (rol) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return "redirect:/api/auth/login";
       // return new ModelAndView("login");
    }
}
