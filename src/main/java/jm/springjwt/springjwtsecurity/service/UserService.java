package jm.springjwt.springjwtsecurity.service;

import jm.springjwt.springjwtsecurity.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByName(String name);

    void removeUser(Long id);

}
