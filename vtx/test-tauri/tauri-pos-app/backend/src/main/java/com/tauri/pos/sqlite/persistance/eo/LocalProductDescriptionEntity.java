package com.tauri.pos.sqlite.persistance.eo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "local_product_description")
@IdClass(LocalProductDescriptionId.class)
public class LocalProductDescriptionEntity {

    @Id
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Id
    @Column(name = "site_id", nullable = false)
    private Integer siteId;

    @Id
    @Column(name = "language_id", nullable = false)
    private Integer languageId;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "feature", columnDefinition = "TEXT")
    private String feature;

    @Column(name = "specification", columnDefinition = "TEXT")
    private String specification;

    @Column(name = "include", columnDefinition = "TEXT")
    private String include;

    @Column(name = "required", columnDefinition = "TEXT")
    private String required;

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
