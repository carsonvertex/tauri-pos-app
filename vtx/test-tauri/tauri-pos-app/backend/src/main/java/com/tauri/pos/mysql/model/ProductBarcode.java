package com.tauri.pos.mysql.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductBarcode {
    private Integer productId; // int - part of composite primary key
    private String barcode; // varchar(45) - part of composite primary key
    private Integer status; // tinyint(1) - boolean-like integer
    private LocalDateTime createdAt; // timestamp - nullable
    private LocalDateTime updatedAt; // timestamp - nullable
    private Integer createdBy; // int
    private Integer updatedBy; // int
}
