package com.bee.security.services;

import com.bee.repository.UserRepository;
import com.bee.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;



@Service
public class AccessService {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepo;

    public boolean hasAccess(HttpSession session) {

        String token = (String)session.getAttribute("token");
        String name = jwtUtils.getUserNameFromJwtToken(token);
        var user =userRepo.findByUsername(name);
        return user.get().isAuthorized();
        //return parameter == 1;
    }
}