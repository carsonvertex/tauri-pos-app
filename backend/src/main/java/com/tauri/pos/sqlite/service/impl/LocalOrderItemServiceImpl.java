package com.tauri.pos.sqlite.service.impl;

import com.tauri.pos.sqlite.mapper.LocalOrderItemMapper;
import com.tauri.pos.sqlite.model.LocalOrderItem;
import com.tauri.pos.sqlite.persistance.dao.LocalOrderItemDao;
import com.tauri.pos.sqlite.persistance.eo.LocalOrderItemEntity;
import com.tauri.pos.sqlite.service.LocalOrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalOrderItemServiceImpl implements LocalOrderItemService {
    private final LocalOrderItemDao localOrderItemDao;

    public LocalOrderItemServiceImpl(LocalOrderItemDao localOrderItemDao) {
        this.localOrderItemDao = localOrderItemDao;
    }

    @Override
    public LocalOrderItem createLocalOrderItem(LocalOrderItem localOrderItem) {
        return LocalOrderItemMapper.INSTANCE.localOrderItemEntityToLocalOrderItem(
                localOrderItemDao.save(LocalOrderItemMapper.INSTANCE.localOrderItemToLocalOrderItemEntity(localOrderItem))
        );
    }

    @Override
    public List<LocalOrderItem> getAllLocalOrderItem() {
        System.out.println("DEBUG: Getting all local order items...");
        List<LocalOrderItemEntity> entities = localOrderItemDao.findAll();
        System.out.println("DEBUG: Found " + entities.size() + " entities");
        for (LocalOrderItemEntity entity : entities) {
            System.out.println("DEBUG: Entity ID: " + entity.getId() + ", Order ID: " + entity.getOrderId() + ", Product ID: " + entity.getProductId());
        }
        return entities.stream()
                .map(LocalOrderItemMapper.INSTANCE::localOrderItemEntityToLocalOrderItem)
                .toList();
    }

    @Override
    public LocalOrderItem getLocalOrderItemById(Long Id) {
        return localOrderItemDao.findById(Id)
                .map(LocalOrderItemMapper.INSTANCE::localOrderItemEntityToLocalOrderItem)
                .orElse(null);
    }

    @Override
    public LocalOrderItem updateLocalOrderItemById(Long Id,LocalOrderItem localOrderItem) {
        return localOrderItemDao.findById(Id)
                .map(entity -> {
                    LocalOrderItemEntity updatedEntity = LocalOrderItemMapper.INSTANCE.localOrderItemToLocalOrderItemEntity(localOrderItem);
                    updatedEntity.setId(Id);
                    return LocalOrderItemMapper.INSTANCE.localOrderItemEntityToLocalOrderItem(localOrderItemDao.save(updatedEntity));
                })
                .orElse(null);    }

    @Override
    public void deleteLocalOrderItemById(Long Id) {
        localOrderItemDao.findById(Id)
                .map(entity -> {
                    localOrderItemDao.deleteById(Id);
                    return true;
                });
    }
}
