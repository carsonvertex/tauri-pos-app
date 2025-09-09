package com.tauri.pos.sqlite.persistance.dao;

import com.tauri.pos.sqlite.persistance.eo.LocalOrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalOrderItemDao extends JpaRepository<LocalOrderItemEntity, Long> {
    LocalOrderItemEntity findAllByProductId(Long productId);
    void deleteAllByProductId(Long productId);
}
