package com.tauri.pos.mysql.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    private Long productId;
    private Integer status; // tinyint(1) - boolean-like integer
    private Integer brandId; // int
    private Integer ebayId; // int
    private String modelNumber; // varchar(64)
    private String sku; // varchar(10)
    private LocalDate dateAvailable; // date
    private LocalDate dateBackorder; // date
    private LocalDate datePreorder; // date
    private Integer qtyPreorder; // int
    private String barcode; // varchar(50) - nullable
    private BigDecimal weight; // decimal(15,2)
    private Integer weightClassId; // int
    private LocalDateTime createdAt; // timestamp
    private LocalDateTime updatedAt; // timestamp
    private Integer createdBy; // int
    private Integer updatedBy; // int
}
