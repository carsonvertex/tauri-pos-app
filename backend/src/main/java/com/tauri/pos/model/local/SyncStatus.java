package com.tauri.pos.model.local;

public enum SyncStatus {
    PENDING,    // Needs to be synced
    SYNCING,    // Currently being synced
    SYNCED,     // Successfully synced
    FAILED,     // Sync failed
    CONFLICT    // Sync conflict detected
}
