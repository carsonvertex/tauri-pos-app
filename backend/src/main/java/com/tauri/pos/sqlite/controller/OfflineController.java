package com.tauri.pos.sqlite.controller;

import com.tauri.pos.sqlite.model.LocalOrder;
import com.tauri.pos.sqlite.model.LocalProduct;
import com.tauri.pos.shared.enums.OrderStatus;
import com.tauri.pos.sqlite.service.OfflineService;
import com.tauri.pos.sqlite.service.OfflineService.OrderItemRequest;
import com.tauri.pos.sqlite.service.OfflineService.SyncStatusSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/offline")
@CrossOrigin(origins = "*")
public class OfflineController {
    
    @Autowired
    private OfflineService offlineService;
    
    // Product endpoints
    @GetMapping("/products")
    public ResponseEntity<List<LocalProduct>> getAllProducts() {
        List<LocalProduct> products = offlineService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/products/{id}")
    public ResponseEntity<LocalProduct> getProductById(@PathVariable Long id) {
        Optional<LocalProduct> product = offlineService.getProductById(id);
        return product.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/products/search")
    public ResponseEntity<List<LocalProduct>> searchProducts(@RequestParam String name) {
        List<LocalProduct> products = offlineService.searchProducts(name);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/products/low-stock")
    public ResponseEntity<List<LocalProduct>> getLowStockProducts(@RequestParam(defaultValue = "10") Integer threshold) {
        List<LocalProduct> products = offlineService.getLowStockProducts(threshold);
        return ResponseEntity.ok(products);
    }
    
    @PostMapping("/products")
    public ResponseEntity<LocalProduct> createProduct(@RequestBody CreateProductRequest request) {
        try {
            LocalProduct product = offlineService.createProduct(
                request.getName(),
                request.getPrice(),
                request.getStock(),
                request.getDescription(),
                request.getCategory()
            );
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/products/{id}")
    public ResponseEntity<LocalProduct> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request) {
        try {
            LocalProduct product = offlineService.updateProduct(
                id,
                request.getName(),
                request.getPrice(),
                request.getStock(),
                request.getDescription(),
                request.getCategory()
            );
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Order endpoints
    @GetMapping("/orders")
    public ResponseEntity<List<LocalOrder>> getAllOrders() {
        List<LocalOrder> orders = offlineService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/orders/{id}")
    public ResponseEntity<LocalOrder> getOrderById(@PathVariable Long id) {
        Optional<LocalOrder> order = offlineService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/orders/status/{status}")
    public ResponseEntity<List<LocalOrder>> getOrdersByStatus(@PathVariable OrderStatus status) {
        List<LocalOrder> orders = offlineService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }
    
    @PostMapping("/orders")
    public ResponseEntity<LocalOrder> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            LocalOrder order = offlineService.createOrder(
                request.getCustomerName(),
                request.getCustomerEmail(),
                request.getItems()
            );
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/orders/{id}/complete")
    public ResponseEntity<LocalOrder> completeOrder(@PathVariable Long id) {
        try {
            LocalOrder order = offlineService.completeOrder(id);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Sync endpoints
    @GetMapping("/sync/status")
    public ResponseEntity<SyncStatusSummary> getSyncStatus() {
        SyncStatusSummary summary = offlineService.getSyncStatusSummary();
        return ResponseEntity.ok(summary);
    }
    
    @GetMapping("/sync/pending-count")
    public ResponseEntity<Long> getPendingSyncCount() {
        long count = offlineService.getPendingSyncCount();
        return ResponseEntity.ok(count);
    }
    
    @PostMapping("/sync/force")
    public ResponseEntity<String> forceSync() {
        try {
            offlineService.forceSync();
            return ResponseEntity.ok("Sync completed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Sync failed: " + e.getMessage());
        }
    }
    
    // DTOs
    public static class CreateProductRequest {
        private String name;
        private BigDecimal price;
        private Integer stock;
        private String description;
        private String category;
        
        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        
        public Integer getStock() { return stock; }
        public void setStock(Integer stock) { this.stock = stock; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
    }
    
    public static class UpdateProductRequest {
        private String name;
        private BigDecimal price;
        private Integer stock;
        private String description;
        private String category;
        
        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        
        public Integer getStock() { return stock; }
        public void setStock(Integer stock) { this.stock = stock; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
    }
    
    public static class CreateOrderRequest {
        private String customerName;
        private String customerEmail;
        private List<OrderItemRequest> items;
        
        // Getters and setters
        public String getCustomerName() { return customerName; }
        public void setCustomerName(String customerName) { this.customerName = customerName; }
        
        public String getCustomerEmail() { return customerEmail; }
        public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
        
        public List<OrderItemRequest> getItems() { return items; }
        public void setItems(List<OrderItemRequest> items) { this.items = items; }
    }
}
