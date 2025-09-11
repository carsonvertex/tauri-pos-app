package com.tauri.pos.mysql.persistance.eo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adjustment_detail")

public class AdjustmentDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adjustment_detail_id")
    private Long adjustmentDetailId;

    @Column(name = "adjustment_id")
    private Long adjustmentId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "bin_id")
    private Long binId;

    @Column(name = "before_adjustment_qty")
    private Integer beforeAdjustmentQty;

    @Column(name = "adjustment_qty")
    private Integer adjustmentQty;

    @Column(name = "after_adjustment_qty")
    private Integer afterAdjustmentQty;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;
}
