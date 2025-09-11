package com.tauri.pos.sqlite.controller;

import com.tauri.pos.shared.dto.AuthResponse;
import com.tauri.pos.shared.service.JwtService;
import com.tauri.pos.sqlite.model.User;
import com.tauri.pos.sqlite.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * Create a new user
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Get all users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get a user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticateUser(
            @RequestParam String username,
            @RequestParam String password) {
        try {
            User user = userService.authenticateUser(username, password);
            if (user != null) {
                // Generate JWT token
                String token = jwtService.generateToken(user.getUsername(), user.getUserid(), user.getPermission());
                
                AuthResponse authResponse = AuthResponse.builder()
                        .token(token)
                        .username(user.getUsername())
                        .permission(user.getPermission())
                        .userId(user.getUserid())
                        .message("Authentication successful")
                        .build();
                
                return ResponseEntity.ok(authResponse);
            } else {
                AuthResponse authResponse = AuthResponse.builder()
                        .message("Invalid username or password")
                        .build();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse);
            }
        } catch (Exception e) {
            AuthResponse authResponse = AuthResponse.builder()
                    .message("Internal server error")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(authResponse);
        }
    }

    /**
     * Update a user by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Integer id, 
            @RequestBody User user) {
        try {
            User updatedUser = userService.updateUserById(id, user);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Delete a user by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("User Controller is running!");
    }
}
