package com.tauri.pos.mysql.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdjustmentDetail {
    private Long adjustmentDetailId;
    private Long adjustmentId;
    private Long productId;
    private Long warehouseId;
    private Long binId;
    private Integer beforeAdjustmentQty;
    private Integer adjustmentQty;
    private Integer afterAdjustmentQty;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;
}
