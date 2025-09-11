package com.tauri.pos.sqlite.controller;

import com.tauri.pos.sqlite.model.LocalOrderItem;
import com.tauri.pos.sqlite.service.LocalOrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/local-orderItem")
@CrossOrigin(origins = "*")
public class LocalOrderItemController {
    private final LocalOrderItemService localOrderItemService;

    public LocalOrderItemController(LocalOrderItemService localOrderItemService) {
        this.localOrderItemService = localOrderItemService;
    }

    /**
     * Create a new local order item
     */
    @PostMapping
    public ResponseEntity<LocalOrderItem> createLocalOrderItem(@RequestBody LocalOrderItem localOrderItem) {
        try {
            LocalOrderItem createdItem = localOrderItemService.createLocalOrderItem(localOrderItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Get all local order items
     */
    @GetMapping
    public ResponseEntity<List<LocalOrderItem>> getAllLocalOrderItems() {
        try {
            List<LocalOrderItem> items = localOrderItemService.getAllLocalOrderItem();
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get a local order item by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<LocalOrderItem> getLocalOrderItemById(@PathVariable Long id) {
        try {
            LocalOrderItem item = localOrderItemService.getLocalOrderItemById(id);
            if (item != null) {
                return ResponseEntity.ok(item);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update a local order item by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<LocalOrderItem> updateLocalOrderItem(
            @PathVariable Long id, 
            @RequestBody LocalOrderItem localOrderItem) {
        try {
            LocalOrderItem updatedItem = localOrderItemService.updateLocalOrderItemById(id, localOrderItem);
            if (updatedItem != null) {
                return ResponseEntity.ok(updatedItem);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Delete a local order item by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocalOrderItem(@PathVariable Long id) {
        try {
            localOrderItemService.deleteLocalOrderItemById(id);
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
        return ResponseEntity.ok("Local Order Item Controller is running!");
    }
}
