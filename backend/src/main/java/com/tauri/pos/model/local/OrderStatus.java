package com.tauri.pos.model.local;

public enum OrderStatus {
    PENDING,    // Order created but not completed
    COMPLETED,  // Order completed and paid
    CANCELLED,  // Order cancelled
    REFUNDED    // Order refunded
}
