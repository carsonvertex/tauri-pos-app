package com.tauri.pos.sqlite.service.impl;

import com.tauri.pos.sqlite.mapper.UserMapper;
import com.tauri.pos.sqlite.model.User;
import com.tauri.pos.sqlite.persistance.dao.UserDao;
import com.tauri.pos.sqlite.persistance.eo.UserEntity;
import com.tauri.pos.sqlite.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User createUser(User user) {
        // Check if username already exists
        UserEntity existingUser = userDao.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("Username already exists: " + user.getUsername());
        }
        
        // Hash the password before saving
        UserEntity userEntity = UserMapper.INSTANCE.userToUserEntity(user);
        userEntity.setHashedPassword(passwordEncoder.encode(user.getHashedPassword()));
        
        return UserMapper.INSTANCE.userEntityToUser(
                userDao.save(userEntity)
        );
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll().stream()
                .map(UserMapper.INSTANCE::userEntityToUser)
                .toList();
    }

    @Override
    public User getUserById(Integer userid) {
        return userDao.findById(userid)
                .map(UserMapper.INSTANCE::userEntityToUser)
                .orElse(null);
    }

    @Override
    public User getUserByUsernameAndHashedPassword(String username, String hashedPassword) {
        UserEntity userEntity = userDao.findByUsernameAndHashedPassword(username, hashedPassword);
        return UserMapper.INSTANCE.userEntityToUser(userEntity);
    }

    @Override
    public User authenticateUser(String username, String password) {
        // Find user by username first
        UserEntity userEntity = userDao.findByUsername(username);
        if (userEntity == null) {
            return null; // User not found
        }
        
        // Check if the provided password matches the stored hash
        if (passwordEncoder.matches(password, userEntity.getHashedPassword())) {
            return UserMapper.INSTANCE.userEntityToUser(userEntity);
        }
        
        return null; 
    }

    @Override
    public User updateUserById(Integer userid, User user) {
        return userDao.findById(userid)
                .map(entity -> {
                    // Check if username already exists (excluding current user)
                    if (!entity.getUsername().equals(user.getUsername())) {
                        UserEntity existingUser = userDao.findByUsername(user.getUsername());
                        if (existingUser != null) {
                            throw new RuntimeException("Username already exists: " + user.getUsername());
                        }
                    }
                    
                    // Check if trying to change from admin to user when there's only one admin
                    if ("admin".equals(entity.getPermission()) && "user".equals(user.getPermission())) {
                        long adminCount = countAdminUsers();
                        if (adminCount <= 1) {
                            throw new RuntimeException("Cannot change permission from admin to user. There must be at least one admin user in the system.");
                        }
                    }
                    
                    // Update fields
                    entity.setUsername(user.getUsername());
                    entity.setPermission(user.getPermission());
                    
                    // Only update password if provided
                    if (user.getHashedPassword() != null && !user.getHashedPassword().trim().isEmpty()) {
                        entity.setHashedPassword(passwordEncoder.encode(user.getHashedPassword()));
                    }
                    
                    return UserMapper.INSTANCE.userEntityToUser(userDao.save(entity));
                })
                .orElse(null);
    }

    @Override
    public void deleteUserById(Integer userid) {
        userDao.findById(userid)
                .map(entity -> {
                    // Check if trying to delete an admin user
                    if ("admin".equals(entity.getPermission())) {
                        long adminCount = countAdminUsers();
                        if (adminCount <= 1) {
                            throw new RuntimeException("Cannot delete admin user. There must be at least one admin user in the system.");
                        }
                    }
                    
                    userDao.deleteById(userid);
                    return true;
                });
    }
    
    @Override
    public long countAdminUsers() {
        return userDao.findAll().stream()
                .mapToLong(entity -> "admin".equals(entity.getPermission()) ? 1 : 0)
                .sum();
    }
}
