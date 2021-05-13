//package com.bee.models;
//
//import org.apache.commons.lang3.time.DateUtils;
//
//import javax.persistence.*;
//import java.util.Date;
////@Entity
//public abstract class Token {
//    protected static final int EXPIRATION = 60 * 24;
//
////    @Id
////    @GeneratedValue(strategy = GenerationType.AUTO)
////    private Long id;
//
//
//
//    protected String token;
//
//
//
//    protected Date expiryDate;
//
//    public Token(String token) {
//        this.token = token;
//        this.expiryDate = DateUtils.addMinutes(new Date(),EXPIRATION);
//    }
//
//    public Token() {
//    }
//
////    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
////    @JoinColumn(nullable = false, name = "user_id")
////    private User user;
//
//    public Date getExpiryDate() {
//        return expiryDate;
//    }
//
//
//
//    public abstract User getUser();
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public void setExpiryDate(Date expiryDate) {
//        this.expiryDate = expiryDate;
//    }
//
//}
