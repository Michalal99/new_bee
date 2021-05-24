package com.bee.service;

import com.bee.models.PasswordResetToken;
//import com.bee.models.RegisterConfirmationToken;
//import com.bee.models.RegisterConfirmationToken;
import com.bee.models.RegisterConfirmationToken;
import com.bee.models.User;
import com.bee.repository.PasswordResetTokenRepository;
//import com.bee.repository.RegisterConfirmationTokenRepository;
//import com.bee.repository.RegisterConfirmationTokenRepository;
//import com.bee.repository.RegisterConfirmationTokenRepository;
import com.bee.repository.RegisterConfirmationTokenRepository;
import com.bee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private RegisterConfirmationTokenRepository registerTokenRepository;

    public User findUserByUserName(String username){
        return userRepo.findByUsername(username).orElseThrow();
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.deleteByUser(user);
        passwordTokenRepository.save(myToken);
    }

    public void createConfirmationTokenForUser(User user, String token) {
        RegisterConfirmationToken myToken = new RegisterConfirmationToken(token, user);
        registerTokenRepository.deleteByUser(user);
        registerTokenRepository.save(myToken);
    }

    public SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, User user) {
       // String url = contextPath + "/changePassword?token=" + token;
        String url = contextPath + "?token=" + token;
        String message = "message";
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }
    private SimpleMailMessage constructEmail(String subject, String body,
                                             User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom("support.email");
        return email;
    }
}
