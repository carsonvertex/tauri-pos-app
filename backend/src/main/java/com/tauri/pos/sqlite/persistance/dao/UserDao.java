package com.tauri.pos.sqlite.persistance.dao;

import com.tauri.pos.sqlite.persistance.eo.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
    UserEntity findByUsernameAndHashedPassword(String username, String hashedPassword);
    void deleteByUsername(String username);
}
