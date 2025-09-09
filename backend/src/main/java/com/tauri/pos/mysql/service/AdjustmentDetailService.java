package com.tauri.pos.mysql.service;

import com.tauri.pos.mysql.model.AdjustmentDetail;

import java.util.List;

public interface AdjustmentDetailService {
    AdjustmentDetail createAdjustmentDetail(AdjustmentDetail adjustmentDetail);

    List<AdjustmentDetail> getAllAdjustmentDetails();

    AdjustmentDetail getAdjustmentDetailById(Long adjustmentDetailId);

    AdjustmentDetail updateAdjustmentDetailById(Long adjustmentDetailId, AdjustmentDetail adjustmentDetail);

    void deleteAdjustmentDetailById(Long adjustmentDetailId);

    List<AdjustmentDetail> getAdjustmentDetailsByAdjustmentId(Long adjustmentId);

    List<AdjustmentDetail> getAdjustmentDetailsByProductId(Long productId);

    List<AdjustmentDetail> getAdjustmentDetailsByWarehouseId(Long warehouseId);

    List<AdjustmentDetail> getAdjustmentDetailsByBinId(Long binId);
}
