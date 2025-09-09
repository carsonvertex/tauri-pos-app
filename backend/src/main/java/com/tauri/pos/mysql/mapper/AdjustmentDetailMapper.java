package com.tauri.pos.mysql.mapper;

import com.tauri.pos.mysql.model.AdjustmentDetail;
import com.tauri.pos.mysql.persistance.eo.AdjustmentDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdjustmentDetailMapper {
    AdjustmentDetailMapper INSTANCE = Mappers.getMapper(AdjustmentDetailMapper.class);

    AdjustmentDetailEntity adjustmentDetailToAdjustmentDetailEntity(AdjustmentDetail adjustmentDetail);

    AdjustmentDetail adjustmentDetailEntityToAdjustmentDetail(AdjustmentDetailEntity adjustmentDetailEntity);
}
