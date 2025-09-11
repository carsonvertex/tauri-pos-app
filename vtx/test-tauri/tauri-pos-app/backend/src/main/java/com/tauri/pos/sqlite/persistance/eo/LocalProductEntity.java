package com.tauri.pos.sqlite.persistance.eo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "local_product")
public class LocalProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "status", nullable = false)
    private Integer status = 1;

    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "ebay_id")
    private Integer ebayId;

    @Column(name = "model_number", length = 255)
    private String modelNumber;

    @Column(name = "sku", length = 255)
    private String sku;

    @Column(name = "date_available")
    private LocalDate dateAvailable;

    @Column(name = "date_backorder")
    private LocalDate dateBackorder;

    @Column(name = "date_preorder")
    private LocalDate datePreorder;

    @Column(name = "qty_preorder")
    private Integer qtyPreorder = 0;

    @Column(name = "barcode", length = 45)
    private String barcode;

    @Column(name = "weight", precision = 10, scale = 3)
    private BigDecimal weight;

    @Column(name = "weight_class_id")
    private Integer weightClassId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "updated_by", nullable = false)
    private Integer updatedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
