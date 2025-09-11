package com.tauri.pos.sqlite.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userid;
    private String username;
    private String hashedPassword;
    private String permission;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
