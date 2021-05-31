package com.bee.models;

import org.apache.commons.lang3.time.DateUtils;

import javax.persistence.*;
import java.util.Date;
@Entity
//@Table(	name = "register_token")
public class RegisterConfirmationToken  {
//  // @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
////    @JoinColumn(nullable = false, name = "user_id")
////    private User user;
////
////    public RegisterConfirmationToken(String token, User user) {
////        super(token);
////        this.user = user;
////    }
////
////    @Override
////    public User getUser() {
////        return user;
////    }
////    public RegisterConfirmationToken(){
////
////    }
//  @Id
//  @GeneratedValue(strategy = GenerationType.AUTO)
//  private Long id;
//
//  @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
//  @JoinColumn(nullable = false, name = "user_id")
//  private User user;
//
//    public RegisterConfirmationToken(String token, User user) {
//        super(token);
//        this.user = user;
//    }
//
//    @Override
//    public User getUser() {
//        return user;
//    }
//    public RegisterConfirmationToken(){
//
//    }
private static final int EXPIRATION = 60 * 24;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;



  private String token;

  public RegisterConfirmationToken(String token, User user) {
    this.token = token;
    this.user = user;
    this.expiryDate = DateUtils.addMinutes(new Date(),EXPIRATION);
  }

  public RegisterConfirmationToken() {
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
  public String getToken() {
    return token;
  }
}
