//package com.bezkoder.spring.security.postgresql.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.example.demo.dao.UserDAO;
//import com.example.demo.model.User;
//
//@Service
//@Transactional
//public class UserServiceImpl {
//
//    @Autowired
//    private UserDAO userDAO;
//
//    public void addUser(User user) {
//        userDAO.addUser(user);
//    }
//
//    public void updateUser(User user) {
//        userDAO.updateUser(user);
//    }
//
//    public User getUser(String id) {
//        return userDAO.getUser(id);
//    }
//
//    public void deleteUser(String id) {
//        userDAO.deleteUser(id);
//    }
//
//    public List<User> getUsers() {
//        return userDAO.getUsers() ;
//    }
//
//}
