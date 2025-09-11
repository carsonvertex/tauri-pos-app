package com.tauri.pos.mysql.persistance.eo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_description")
@IdClass(ProductDescriptionId.class)
public class ProductDescriptionEntity {

    @Id
    @Column(name = "product_id", nullable = false)
    private Integer productId; // int - part of composite primary key

    @Id
    @Column(name = "site_id", nullable = false)
    private Integer siteId; // int - part of composite primary key

    @Id
    @Column(name = "language_id", nullable = false)
    private Integer languageId; // int - part of composite primary key

    @Column(name = "name", length = 255)
    private String name; // varchar(255) - nullable

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // text - nullable

    @Column(name = "feature", columnDefinition = "TEXT")
    private String feature; // text - nullable

    @Column(name = "specification", columnDefinition = "TEXT")
    private String specification; // text - nullable

    @Column(name = "include", columnDefinition = "TEXT")
    private String include; // text - nullable

    @Column(name = "required", columnDefinition = "TEXT")
    private String required; // text - nullable

    @Column(name = "created_at")
    private LocalDateTime createdAt; // timestamp - nullable

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // timestamp - nullable

    @Column(name = "created_by", nullable = false)
    private Integer createdBy; // int

    @Column(name = "updated_by", nullable = false)
    private Integer updatedBy; // int
}
