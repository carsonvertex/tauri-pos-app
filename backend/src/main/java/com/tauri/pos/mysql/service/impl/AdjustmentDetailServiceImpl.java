package com.tauri.pos.mysql.service.impl;

import com.tauri.pos.mysql.mapper.AdjustmentDetailMapper;
import com.tauri.pos.mysql.model.AdjustmentDetail;
import com.tauri.pos.mysql.persistance.dao.AdjustmentDetailDao;
import com.tauri.pos.mysql.persistance.eo.AdjustmentDetailEntity;
import com.tauri.pos.mysql.service.AdjustmentDetailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@ConditionalOnProperty(name = "spring.datasource.mysql.jdbc-url")
public class AdjustmentDetailServiceImpl implements AdjustmentDetailService {
    private final AdjustmentDetailDao adjustmentDetailDao;

    public AdjustmentDetailServiceImpl(AdjustmentDetailDao adjustmentDetailDao) {
        this.adjustmentDetailDao = adjustmentDetailDao;
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public AdjustmentDetail createAdjustmentDetail(AdjustmentDetail adjustmentDetail) {
        AdjustmentDetailEntity entity = AdjustmentDetailMapper.INSTANCE.adjustmentDetailToAdjustmentDetailEntity(adjustmentDetail);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return AdjustmentDetailMapper.INSTANCE.adjustmentDetailEntityToAdjustmentDetail(
                adjustmentDetailDao.save(entity)
        );
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AdjustmentDetail> getAllAdjustmentDetails() {
        return adjustmentDetailDao.findAll().stream()
                .map(AdjustmentDetailMapper.INSTANCE::adjustmentDetailEntityToAdjustmentDetail)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public AdjustmentDetail getAdjustmentDetailById(Long adjustmentDetailId) {
        AdjustmentDetail adjustmentDetail = adjustmentDetailDao.findById(adjustmentDetailId)
                .map(AdjustmentDetailMapper.INSTANCE::adjustmentDetailEntityToAdjustmentDetail)
                .orElse(null);
        System.out.println("adjustmentDetail by id: " + adjustmentDetail);
        return adjustmentDetail;
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public AdjustmentDetail updateAdjustmentDetailById(Long adjustmentDetailId, AdjustmentDetail adjustmentDetail) {
        return adjustmentDetailDao.findById(adjustmentDetailId)
                .map(entity -> {
                    AdjustmentDetailEntity updatedEntity = AdjustmentDetailMapper.INSTANCE.adjustmentDetailToAdjustmentDetailEntity(adjustmentDetail);
                    updatedEntity.setAdjustmentDetailId(adjustmentDetailId);
                    updatedEntity.setUpdatedAt(LocalDateTime.now());
                    return AdjustmentDetailMapper.INSTANCE.adjustmentDetailEntityToAdjustmentDetail(adjustmentDetailDao.save(updatedEntity));
                })
                .orElse(null);
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public void deleteAdjustmentDetailById(Long adjustmentDetailId) {
        adjustmentDetailDao.findById(adjustmentDetailId)
                .map(entity -> {
                    adjustmentDetailDao.deleteById(adjustmentDetailId);
                    return true;
                });
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AdjustmentDetail> getAdjustmentDetailsByAdjustmentId(Long adjustmentId) {
        return adjustmentDetailDao.findByAdjustmentId(adjustmentId).stream()
                .map(AdjustmentDetailMapper.INSTANCE::adjustmentDetailEntityToAdjustmentDetail)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AdjustmentDetail> getAdjustmentDetailsByProductId(Long productId) {
        return adjustmentDetailDao.findByProductId(productId).stream()
                .map(AdjustmentDetailMapper.INSTANCE::adjustmentDetailEntityToAdjustmentDetail)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AdjustmentDetail> getAdjustmentDetailsByWarehouseId(Long warehouseId) {
        return adjustmentDetailDao.findByWarehouseId(warehouseId).stream()
                .map(AdjustmentDetailMapper.INSTANCE::adjustmentDetailEntityToAdjustmentDetail)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AdjustmentDetail> getAdjustmentDetailsByBinId(Long binId) {
        return adjustmentDetailDao.findByBinId(binId).stream()
                .map(AdjustmentDetailMapper.INSTANCE::adjustmentDetailEntityToAdjustmentDetail)
                .toList();
    }
}
