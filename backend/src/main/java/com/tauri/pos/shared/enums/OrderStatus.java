package com.tauri.pos.shared.enums;

public enum OrderStatus {
    PENDING,    // Order created but not completed
    COMPLETED,  // Order completed and paid
    CANCELLED,  // Order cancelled
    REFUNDED    // Order refunded
}
