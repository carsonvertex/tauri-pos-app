package com.tauri.pos.mysql.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDescription {
    private Integer productId; // int - part of composite primary key
    private Integer siteId; // int - part of composite primary key
    private Integer languageId; // int - part of composite primary key
    private String name; // varchar(255) - nullable
    private String description; // text - nullable
    private String feature; // text - nullable
    private String specification; // text - nullable
    private String include; // text - nullable
    private String required; // text - nullable
    private LocalDateTime createdAt; // timestamp - nullable
    private LocalDateTime updatedAt; // timestamp - nullable
    private Integer createdBy; // int
    private Integer updatedBy; // int
}
