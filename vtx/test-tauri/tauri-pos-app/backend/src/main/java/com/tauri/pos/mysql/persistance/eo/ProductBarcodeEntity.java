package com.tauri.pos.mysql.persistance.eo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_barcode")
@IdClass(ProductBarcodeId.class)
public class ProductBarcodeEntity {

    @Id
    @Column(name = "product_id", nullable = false)
    private Integer productId; // int - part of composite primary key

    @Id
    @Column(name = "barcode", nullable = false, length = 45)
    private String barcode; // varchar(45) - part of composite primary key

    @Column(name = "status", nullable = false)
    private Integer status; // tinyint(1) - boolean-like integer

    @Column(name = "created_at")
    private LocalDateTime createdAt; // timestamp - nullable

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // timestamp - nullable

    @Column(name = "created_by", nullable = false)
    private Integer createdBy; // int

    @Column(name = "updated_by", nullable = false)
    private Integer updatedBy; // int
}
