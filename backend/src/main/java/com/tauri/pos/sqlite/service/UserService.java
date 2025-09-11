package com.tauri.pos.sqlite.service;

import com.tauri.pos.sqlite.model.User;

import java.util.List;


public interface UserService {
    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(Integer userid);

    User getUserByUsernameAndHashedPassword(String username,String hashedPassword);

    User authenticateUser(String username, String password);

    User updateUserById(Integer userid, User user);

    void deleteUserById(Integer userid);
    
    long countAdminUsers();
}
