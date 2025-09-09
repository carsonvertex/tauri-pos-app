package com.tauri.pos.sqlite.mapper;

import com.tauri.pos.sqlite.model.LocalOrderItem;
import com.tauri.pos.sqlite.persistance.eo.LocalOrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocalOrderItemMapper {
    LocalOrderItemMapper INSTANCE = Mappers.getMapper(LocalOrderItemMapper.class);

    LocalOrderItemEntity localOrderItemToLocalOrderItemEntity(LocalOrderItem localOrderItem);

    LocalOrderItem localOrderItemEntityToLocalOrderItem(LocalOrderItemEntity localOrderItemEntity);
}
