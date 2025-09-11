package com.tauri.pos.sqlite.model;

import lombok.*;
import com.tauri.pos.shared.enums.SyncStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalOrderItem {
    private Long Id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
    private Long remoteId;
    private SyncStatus syncStatus ;
    private LocalDateTime lastSync;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
