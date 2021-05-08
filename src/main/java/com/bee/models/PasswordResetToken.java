package com.bee.models;

import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.DateUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = DateUtils.addMinutes(new Date(),EXPIRATION);
    }

    public PasswordResetToken() {
    }

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public Date getExpiryDate() {
        return expiryDate;
    }

    private Date expiryDate;

    public User getUser() {
        return user;
    }
}