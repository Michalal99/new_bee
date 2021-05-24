package com.bee.controller;

import com.bee.models.Role;
import com.bee.payload.request.AccessRequest;
import com.bee.repository.RoleRepository;
import com.bee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin")
@Controller

public class AdminBoardController {

    @Autowired
    RoleRepository roleRepo;
    @Autowired
    UserRepository userRepo;
    @GetMapping("/access")
    @PreAuthorize("@accessService.hasAccess(#session) and hasRole('ADMIN')")
    public ModelAndView manageAccess(HttpSession session) {

//        List<Role> selectableRoles = Arrays.asList(
//                new Role(ERole.ROLE_USER),
//                new Role(ERole.ROLE_MODERATOR),
//                new Role(ERole.ROLE_ADMIN)
//        );
        ModelAndView mav = new ModelAndView("Login&Register/access");

        List<Role> roles = roleRepo.findAll();
        mav.addObject("allRoles",roles);
       // model.addAttribute("allRoles",roles);
        return mav;
       // return "Login&Register/signup";
       // return "Login&Register/access";






 }

    @PostMapping("/access")
    @PreAuthorize("@accessService.hasAccess(#session) and hasRole('ADMIN')")
    public String changeUserAccess(HttpSession session, @ModelAttribute("accessRequest") AccessRequest accessRequest)
    {
        var test = accessRequest;
        var user = userRepo.findByUsername(test.getUsername()).get();
        var roles_strings = accessRequest.getRoles().stream().collect(Collectors.toSet());
        List<Role> Allroles = roleRepo.findAll();
        Set<Role> roles = new HashSet<>();
        for (var role : Allroles)
        {
            if(roles_strings.contains(role.toString()))
            {
                roles.add(role);
            }
        }
        user.setRoles(roles);
        userRepo.save(user);


//        Set<Role> roles = accessRequest.getRoles().stream().collect(Collectors.toSet());

//        user.setRoles(roles);

        return "Login&Register/access";
    }
}
