package com.tauri.pos.mysql.persistance.eo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "status", nullable = false)
    private Integer status; // tinyint(1) - boolean-like integer

    @Column(name = "brand_id", nullable = false)
    private Integer brandId; // int

    @Column(name = "ebay_id", nullable = false)
    private Integer ebayId; // int

    @Column(name = "model_number", nullable = false, length = 64)
    private String modelNumber; // varchar(64)

    @Column(name = "sku", nullable = false, length = 10)
    private String sku; // varchar(10)

    @Column(name = "date_available")
    private LocalDate dateAvailable; // date

    @Column(name = "date_backorder")
    private LocalDate dateBackorder; // date

    @Column(name = "date_preorder")
    private LocalDate datePreorder; // date

    @Column(name = "qty_preorder", nullable = false)
    private Integer qtyPreorder; // int

    @Column(name = "barcode", length = 50)
    private String barcode; // varchar(50) - nullable

    @Column(name = "weight", nullable = false, precision = 15, scale = 2)
    private BigDecimal weight; // decimal(15,2)

    @Column(name = "weight_class_id", nullable = false)
    private Integer weightClassId; // int

    @Column(name = "created_at")
    private LocalDateTime createdAt; // timestamp

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // timestamp

    @Column(name = "created_by", nullable = false)
    private Integer createdBy; // int

    @Column(name = "updated_by", nullable = false)
    private Integer updatedBy; // int
}
