package com.bee.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.bee.exception.EmailNotExistException;
import com.bee.models.ERole;
import com.bee.models.Role;
import com.bee.models.User;
import com.bee.payload.request.LoginRequest;
import com.bee.payload.request.PasswordChangeRequest;
import com.bee.payload.request.SignupRequest;
import com.bee.repository.PasswordResetTokenRepository;
import com.bee.repository.RoleRepository;
import com.bee.repository.UserRepository;
import com.bee.security.PasswordTokenSecurity;
import com.bee.security.jwt.JwtUtils;
import com.bee.security.services.UserDetailsImpl;
import com.bee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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

    @Autowired
    UserService userService;

    @Autowired
    JavaMailSenderImpl mailSender;

//    @Autowired
//    PasswordTokenSecurity passwordTokenSecurity;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;


    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logoutPage(HttpSession session ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var test = session.getId();
        session.invalidate();
        return "redirect:/api/auth/login";
    }

    @RequestMapping(value="/welcome", method = RequestMethod.GET)
    public String printWelcome(ModelMap model , HttpSession session ) {

        var y = session.getAttribute("token");
       // var y = model.get("token");
        var user = jwtUtils.getUserNameFromJwtToken((String)y);
        return "hello";
    }

//    @GetMapping("/forget")
//    public String passwordForget(){
//        return "forgotPassword";
//    }

    @PostMapping("/forget")
    public String sendEmailToUser(HttpServletRequest request, @RequestParam String email)
    {
       var Optuser = userRepository.findByEmail(email);
       if(Optuser.isEmpty())
           throw new EmailNotExistException();
       var user = Optuser.get();
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        mailSender.send(userService.constructResetTokenEmail("/api/auth/changePassword", request.getLocale(), token, user));

        return "redirect:/api/auth/login";
    }




    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute("passwordChangeRequest")  @Valid  PasswordChangeRequest passwordChangeRequest,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session,
                                 HttpServletRequest request)
    {


        if(passwordChangeRequest.getPassword().compareTo(passwordChangeRequest.getPassword_confirm()) != 0)
        {
            redirectAttributes.addFlashAttribute("error","Password must be the same!");
            return "redirect:"+request.getRequestURL() + "?token=" + session.getAttribute("changePasswordToken");
        }

        var test = passwordResetTokenRepository.findByToken((String)session.getAttribute("changePasswordToken"));
        var myUser = test.getUser();
        myUser.setPassword(encoder.encode(passwordChangeRequest.getPassword()));
        userRepository.save(myUser);

        return "redirect:/api/auth/login";
    }

    @PostMapping("/signin")
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
        //return new ModelAndView("hello");
        return "redirect:/api/auth/welcome";

    }

//    @PostMapping("/signup")
//    public String registerUser(@ModelAttribute("signupRequest") @Valid SignupRequest signUpRequest, RedirectAttributes redirectAtributes)
//    {
//
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            redirectAtributes.addFlashAttribute("error","Error: Username is already taken!");
//            return "redirect:/api/auth/signup";
//        }
////
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            redirectAtributes.addFlashAttribute("error","Error: Email is already in use!");
//            return "redirect:/api/auth/signup";
//        }
//
//        // Create new user's account
//        User user = new User(signUpRequest.getUsername(),
//                signUpRequest.getEmail(),
//                encoder.encode(signUpRequest.getPassword()));
//
//
//        Set<String> strRoles = new HashSet<>();
//        strRoles.add(signUpRequest.getRole());
//        signUpRequest.setRoles(strRoles);
//        // brzydki fragment do poprawienia
//
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//
//            strRoles.forEach(rol -> {
//                switch (rol) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    case "mod":
//                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
//
//        user.setRoles(roles);
//        userRepository.save(user);
//
//        return "redirect:/api/auth/login";
//       // return new ModelAndView("login");
//    }
    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("signupRequest") @Valid SignupRequest signUpRequest,
                               RedirectAttributes redirectAtributes, HttpServletRequest request)
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
        if(userRepository.findAll().isEmpty())
        {
            roles.add(roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
        }

        else {
            roles.add(roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
        }

//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//
//            strRoles.forEach(rol -> {
//                switch (rol) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    case "mod":
//                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }

        user.setRoles(roles);
        userRepository.save(user);

      //  return "redirect:/api/auth/login";
//        var Optuser = userRepository.findByEmail(email);
//
//        if(Optuser.isEmpty())
//            throw new EmailNotExistException();
//        var user = Optuser.get();

//        var user = new User();
//        user.setEmail(signUpRequest.getEmail());
        String token = UUID.randomUUID().toString();
//        user.setUsername(signUpRequest.getUsername());
//        user.setPassword(signUpRequest.getPassword());
        userService.createConfirmationTokenForUser(user, token);
        mailSender.send(userService.constructResetTokenEmail("/api/auth/register", request.getLocale(), token, user));
        return "redirect:/api/auth/login";
    }
}
