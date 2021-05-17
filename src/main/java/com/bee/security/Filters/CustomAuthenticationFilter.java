package com.bee.security.Filters;

import com.bee.models.User;
import com.bee.repository.UserRepository;
import com.bee.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class CustomAuthenticationFilter extends GenericFilterBean {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepo;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String jwt = (String)request.getSession().getAttribute("token");
        if (jwt != null && jwtUtils.validateJwtToken(jwt))
        {
            String name = jwtUtils.getUserNameFromJwtToken(jwt);
            var user = userRepo.findByUsername(name).get();
            Optional<User> optionalCustomPrincipal = userRepo.findByUsername(name);
           if(user.isAuthorized())
           {
               CustomAuthentication authentication = new CustomAuthentication(optionalCustomPrincipal.get());
               SecurityContextHolder.getContext().setAuthentication(authentication);
           }


        }
        filterChain.doFilter(request, response);
    }
}
