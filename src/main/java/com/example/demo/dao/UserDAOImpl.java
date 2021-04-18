package com.example.demo.dao;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.demo.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession() ;
    }
    public void addUser(User user) {
        getCurrentSession().save(user);
    }
    public void updateUser(User user) {
        User userToUpdate = getUser(user.getId());
        userToUpdate.setUserName(user.getUserName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setIsAdmin(user.getIsAdmin());
    }
    public User getUser(String id) {
        return (User)getCurrentSession().get(User.class, id);
    }
    public void deleteUser(String id) {
        User user = getUser(id);
        if(user != null) {
            getCurrentSession().delete(user);
        }
    }
    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        return getCurrentSession().createQuery("from User").list();
    }

}
