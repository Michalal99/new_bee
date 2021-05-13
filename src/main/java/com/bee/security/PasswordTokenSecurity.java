package com.bee.security;

import com.bee.models.PasswordResetToken;
import com.bee.repository.PasswordResetTokenRepository;
//import com.bee.repository.RegisterConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class PasswordTokenSecurity {
    @Autowired
    PasswordResetTokenRepository passwordTokenRepo;


    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepo.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
