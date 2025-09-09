package com.tauri.pos.sqlite.service;

import com.tauri.pos.sqlite.model.*;
import com.tauri.pos.sqlite.repository.LocalOrderRepository;
import com.tauri.pos.sqlite.repository.LocalProductRepository;
import com.tauri.pos.shared.enums.OrderStatus;
import com.tauri.pos.shared.enums.SyncStatus;
import com.tauri.pos.sync.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfflineService {
    
    private static final Logger logger = LoggerFactory.getLogger(OfflineService.class);
    
    @Autowired
    private LocalProductRepository localProductRepository;
    
    @Autowired
    private LocalOrderRepository localOrderRepository;
    
    @Autowired
    private SyncService syncService;
    
    /**
     * Create a new product in offline mode
     */
    @Transactional
    public LocalProduct createProduct(String name, BigDecimal price, Integer stock, String description, String category) {
        logger.info("Creating product offline: {}", name);
        
        LocalProduct product = new LocalProduct(name, price, stock);
        product.setDescription(description);
        product.setCategory(category);
        product.setSyncStatus(SyncStatus.PENDING);
        
        LocalProduct savedProduct = localProductRepository.save(product);
        logger.info("Product created offline with ID: {}", savedProduct.getId());
        
        return savedProduct;
    }
    
    /**
     * Update a product in offline mode
     */
    @Transactional
    public LocalProduct updateProduct(Long id, String name, BigDecimal price, Integer stock, String description, String category) {
        logger.info("Updating product offline: {}", id);
        
        Optional<LocalProduct> productOpt = localProductRepository.findById(id);
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found: " + id);
        }
        
        LocalProduct product = productOpt.get();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setDescription(description);
        product.setCategory(category);
        product.setSyncStatus(SyncStatus.PENDING);
        
        LocalProduct savedProduct = localProductRepository.save(product);
        logger.info("Product updated offline: {}", savedProduct.getId());
        
        return savedProduct;
    }
    
    /**
     * Get all products (offline-first)
     */
    public List<LocalProduct> getAllProducts() {
        return localProductRepository.findAll();
    }
    
    /**
     * Get product by ID
     */
    public Optional<LocalProduct> getProductById(Long id) {
        return localProductRepository.findById(id);
    }
    
    /**
     * Search products by name
     */
    public List<LocalProduct> searchProducts(String name) {
        return localProductRepository.findByNameContaining(name);
    }
    
    /**
     * Get low stock products
     */
    public List<LocalProduct> getLowStockProducts(Integer threshold) {
        return localProductRepository.findLowStockProducts(threshold);
    }
    
    /**
     * Create a new order in offline mode
     */
    @Transactional
    public LocalOrder createOrder(String customerName, String customerEmail, List<OrderItemRequest> items) {
        logger.info("Creating order offline for customer: {}", customerName);
        
        String orderNumber = generateOrderNumber();
        BigDecimal total = calculateOrderTotal(items);
        
        LocalOrder order = new LocalOrder(orderNumber, total);
        order.setCustomerName(customerName);
        order.setCustomerEmail(customerEmail);
        order.setStatus(OrderStatus.PENDING);
        order.setSyncStatus(SyncStatus.PENDING);
        
        LocalOrder savedOrder = localOrderRepository.save(order);
        
        // Create order items
        for (OrderItemRequest itemRequest : items) {
            Optional<LocalProduct> productOpt = localProductRepository.findById(itemRequest.getProductId());
            if (productOpt.isPresent()) {
                LocalProduct product = productOpt.get();
                
                // Check stock
                if (product.getStock() < itemRequest.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product: " + product.getName());
                }
                
                // Create order item
                LocalOrderItem orderItem = new LocalOrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setProduct(product);
                orderItem.setQuantity(itemRequest.getQuantity());
                orderItem.setPrice(product.getPrice());
                orderItem.setSyncStatus(SyncStatus.PENDING);
                
                // Update product stock
                product.setStock(product.getStock() - itemRequest.getQuantity());
                product.setSyncStatus(SyncStatus.PENDING);
                localProductRepository.save(product);
                
                logger.info("Order item created: {} x {}", itemRequest.getQuantity(), product.getName());
            }
        }
        
        logger.info("Order created offline with number: {}", savedOrder.getOrderNumber());
        return savedOrder;
    }
    
    /**
     * Complete an order (mark as paid)
     */
    @Transactional
    public LocalOrder completeOrder(Long orderId) {
        logger.info("Completing order offline: {}", orderId);
        
        Optional<LocalOrder> orderOpt = localOrderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found: " + orderId);
        }
        
        LocalOrder order = orderOpt.get();
        order.setStatus(OrderStatus.COMPLETED);
        order.setSyncStatus(SyncStatus.PENDING);
        
        LocalOrder savedOrder = localOrderRepository.save(order);
        logger.info("Order completed offline: {}", savedOrder.getOrderNumber());
        
        return savedOrder;
    }
    
    /**
     * Get all orders
     */
    public List<LocalOrder> getAllOrders() {
        return localOrderRepository.findAll();
    }
    
    /**
     * Get order by ID
     */
    public Optional<LocalOrder> getOrderById(Long id) {
        return localOrderRepository.findById(id);
    }
    
    /**
     * Get orders by status
     */
    public List<LocalOrder> getOrdersByStatus(OrderStatus status) {
        return localOrderRepository.findByStatus(status);
    }
    
    /**
     * Get pending sync count
     */
    public long getPendingSyncCount() {
        long pendingProducts = localProductRepository.countBySyncStatus(SyncStatus.PENDING);
        long pendingOrders = localOrderRepository.countBySyncStatus(SyncStatus.PENDING);
        return pendingProducts + pendingOrders;
    }
    
    /**
     * Get sync status summary
     */
    public SyncStatusSummary getSyncStatusSummary() {
        SyncStatusSummary summary = new SyncStatusSummary();
        
        summary.setPendingProducts(localProductRepository.countBySyncStatus(SyncStatus.PENDING));
        summary.setPendingOrders(localOrderRepository.countBySyncStatus(SyncStatus.PENDING));
        summary.setFailedProducts(localProductRepository.countBySyncStatus(SyncStatus.FAILED));
        summary.setFailedOrders(localOrderRepository.countBySyncStatus(SyncStatus.FAILED));
        summary.setSyncedProducts(localProductRepository.countBySyncStatus(SyncStatus.SYNCED));
        summary.setSyncedOrders(localOrderRepository.countBySyncStatus(SyncStatus.SYNCED));
        
        return summary;
    }
    
    /**
     * Force sync with remote server
     */
    public void forceSync() {
        logger.info("Force sync requested");
        if (syncService.isOnline()) {
            syncService.syncProductsToRemote();
            syncService.syncOrdersToRemote();
            syncService.syncProductsFromRemote();
            syncService.syncOrdersFromRemote();
        } else {
            logger.warn("Cannot sync - remote server is offline");
        }
    }
    
    // Helper methods
    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private BigDecimal calculateOrderTotal(List<OrderItemRequest> items) {
        return items.stream()
                .map(item -> {
                    Optional<LocalProduct> productOpt = localProductRepository.findById(item.getProductId());
                    if (productOpt.isPresent()) {
                        return productOpt.get().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                    }
                    return BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // DTO for order item requests
    public static class OrderItemRequest {
        private Long productId;
        private Integer quantity;
        
        public OrderItemRequest() {}
        
        public OrderItemRequest(Long productId, Integer quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }
        
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
    
    // DTO for sync status summary
    public static class SyncStatusSummary {
        private long pendingProducts;
        private long pendingOrders;
        private long failedProducts;
        private long failedOrders;
        private long syncedProducts;
        private long syncedOrders;
        
        // Getters and setters
        public long getPendingProducts() { return pendingProducts; }
        public void setPendingProducts(long pendingProducts) { this.pendingProducts = pendingProducts; }
        
        public long getPendingOrders() { return pendingOrders; }
        public void setPendingOrders(long pendingOrders) { this.pendingOrders = pendingOrders; }
        
        public long getFailedProducts() { return failedProducts; }
        public void setFailedProducts(long failedProducts) { this.failedProducts = failedProducts; }
        
        public long getFailedOrders() { return failedOrders; }
        public void setFailedOrders(long failedOrders) { this.failedOrders = failedOrders; }
        
        public long getSyncedProducts() { return syncedProducts; }
        public void setSyncedProducts(long syncedProducts) { this.syncedProducts = syncedProducts; }
        
        public long getSyncedOrders() { return syncedOrders; }
        public void setSyncedOrders(long syncedOrders) { this.syncedOrders = syncedOrders; }
        
        public long getTotalPending() { return pendingProducts + pendingOrders; }
        public long getTotalFailed() { return failedProducts + failedOrders; }
        public long getTotalSynced() { return syncedProducts + syncedOrders; }
    }
}
