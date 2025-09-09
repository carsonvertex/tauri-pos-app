package com.tauri.pos.mysql.persistance.dao;

import com.tauri.pos.mysql.persistance.eo.AdjustmentDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdjustmentDetailDao extends JpaRepository<AdjustmentDetailEntity, Long> {
    List<AdjustmentDetailEntity> findByAdjustmentId(Long adjustmentId);
    List<AdjustmentDetailEntity> findByProductId(Long productId);
    List<AdjustmentDetailEntity> findByWarehouseId(Long warehouseId);
    List<AdjustmentDetailEntity> findByBinId(Long binId);
    void deleteByAdjustmentId(Long adjustmentId);
}
