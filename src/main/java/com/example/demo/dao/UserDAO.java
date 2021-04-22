package com.example.demo.dao;
import java.util.List;
import com.example.demo.model.User;

public interface UserDAO {

    public void addUser(User user);
    public void updateUser(User user);
    public User getUser(String id);
    public void deleteUser(String id);
    public List<User> getUsers();

}
