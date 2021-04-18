package com.example.demo.service;
import java.util.List;
import com.example.demo.model.User;

public interface UserService {

    public void addUser(User user);
    public void updateUser(User user);
    public User getUser(String id);
    public void deleteUser(String id);
    public List<User> getUsers();

}
