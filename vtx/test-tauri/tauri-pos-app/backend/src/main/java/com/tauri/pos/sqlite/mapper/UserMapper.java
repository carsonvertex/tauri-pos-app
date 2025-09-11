package com.tauri.pos.sqlite.mapper;

import com.tauri.pos.sqlite.model.User;
import com.tauri.pos.sqlite.persistance.eo.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity userToUserEntity(User user);

    User userEntityToUser(UserEntity userEntity);
}
