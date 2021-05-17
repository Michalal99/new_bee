package com.bee.security;


import com.bee.security.jwt.AuthEntryPointJwt;
import com.bee.security.jwt.AuthTokenFilter;
import com.bee.security.jwt.CustomOAuth2UserService;
import com.bee.security.jwt.OAuth2LoginSuccessHandler;
import com.bee.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

//    @Autowired
//    DataSource dataSource;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
    {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
       // authenticationManagerBuilder.jdbcAuthentication().dataSource(dataSource).
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()

                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
              //  .authorizeRequests().antMatchers("/teams").authenticated()
               // 	.authorizeRequests().antMatchers("/**").permitAll()
               // .antMatchers("/**").permitAll()
                .antMatchers("/css/**").permitAll();
               // .anyRequest().authenticated();

        http.antMatcher("/teams/**").authorizeRequests() //
                .anyRequest().authenticated() //
                .and()
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // http.addFilterBefore(new CustomAuthenticationFilter(), ConcurrentSessionFilter.class);
        // http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //	http.addFilterAfter(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
