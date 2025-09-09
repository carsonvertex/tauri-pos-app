package com.tauri.pos.sync.service;

import com.tauri.pos.sqlite.model.*;
import com.tauri.pos.sqlite.repository.LocalOrderRepository;
import com.tauri.pos.sqlite.repository.LocalProductRepository;
import com.tauri.pos.mysql.repository.OrderRepository;
import com.tauri.pos.mysql.repository.ProductRepository;
import com.tauri.pos.mysql.model.Order;
import com.tauri.pos.mysql.model.Product;
import com.tauri.pos.shared.enums.SyncStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SyncService {
    
    private static final Logger logger = LoggerFactory.getLogger(SyncService.class);
    
    @Autowired
    private LocalProductRepository localProductRepository;
    
    @Autowired
    private LocalOrderRepository localOrderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    @Value("${sync.remote.url}")
    private String remoteUrl;
    
    @Value("${sync.retry-attempts:3}")
    private int retryAttempts;
    
    private WebClient webClient;
    
    public SyncService() {
        this.webClient = WebClient.builder().build();
    }
    
    /**
     * Scheduled sync every 5 minutes - DISABLED
     */
    // @Scheduled(fixedRateString = "${sync.interval:300000}")
    // @Async
    public void performScheduledSync() {
        logger.info("Starting scheduled sync...");
        try {
            if (isOnline()) {
                syncProductsToRemote();
                syncOrdersToRemote();
                syncProductsFromRemote();
                syncOrdersFromRemote();
                logger.info("Scheduled sync completed successfully");
            } else {
                logger.info("Offline - skipping scheduled sync");
            }
        } catch (Exception e) {
            logger.error("Error during scheduled sync", e);
        }
    }
    
    /**
     * Check if remote server is online
     */
    public boolean isOnline() {
        try {
            webClient.get()
                    .uri(remoteUrl + "/health")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return true;
        } catch (Exception e) {
            logger.debug("Remote server is offline: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Sync local products to remote server
     */
    @Transactional
    public void syncProductsToRemote() {
        logger.info("Syncing products to remote server...");
        
        List<LocalProduct> pendingProducts = localProductRepository
                .findBySyncStatusIn(List.of(SyncStatus.PENDING, SyncStatus.FAILED));
        
        for (LocalProduct localProduct : pendingProducts) {
            try {
                localProduct.setSyncStatus(SyncStatus.SYNCING);
                localProductRepository.save(localProduct);
                
                Product remoteProduct = convertToRemoteProduct(localProduct);
                
                if (localProduct.getRemoteId() == null) {
                    // Create new product on remote
                    Product createdProduct = webClient.post()
                            .uri(remoteUrl + "/products")
                            .bodyValue(remoteProduct)
                            .retrieve()
                            .bodyToMono(Product.class)
                            .block();
                    
                    localProduct.setRemoteId(createdProduct.getId());
                } else {
                    // Update existing product on remote
                    webClient.put()
                            .uri(remoteUrl + "/products/" + localProduct.getRemoteId())
                            .bodyValue(remoteProduct)
                            .retrieve()
                            .bodyToMono(Product.class)
                            .block();
                }
                
                localProduct.setSyncStatus(SyncStatus.SYNCED);
                localProduct.setLastSync(LocalDateTime.now());
                localProductRepository.save(localProduct);
                
                logger.info("Successfully synced product: {}", localProduct.getName());
                
            } catch (Exception e) {
                logger.error("Failed to sync product: {}", localProduct.getName(), e);
                localProduct.setSyncStatus(SyncStatus.FAILED);
                localProductRepository.save(localProduct);
            }
        }
    }
    
    /**
     * Sync local orders to remote server
     */
    @Transactional
    public void syncOrdersToRemote() {
        logger.info("Syncing orders to remote server...");
        
        List<LocalOrder> pendingOrders = localOrderRepository
                .findBySyncStatusIn(List.of(SyncStatus.PENDING, SyncStatus.FAILED));
        
        for (LocalOrder localOrder : pendingOrders) {
            try {
                localOrder.setSyncStatus(SyncStatus.SYNCING);
                localOrderRepository.save(localOrder);
                
                Order remoteOrder = convertToRemoteOrder(localOrder);
                
                if (localOrder.getRemoteId() == null) {
                    // Create new order on remote
                    Order createdOrder = webClient.post()
                            .uri(remoteUrl + "/orders")
                            .bodyValue(remoteOrder)
                            .retrieve()
                            .bodyToMono(Order.class)
                            .block();
                    
                    localOrder.setRemoteId(createdOrder.getId());
                } else {
                    // Update existing order on remote
                    webClient.put()
                            .uri(remoteUrl + "/orders/" + localOrder.getRemoteId())
                            .bodyValue(remoteOrder)
                            .retrieve()
                            .bodyToMono(Order.class)
                            .block();
                }
                
                localOrder.setSyncStatus(SyncStatus.SYNCED);
                localOrder.setLastSync(LocalDateTime.now());
                localOrderRepository.save(localOrder);
                
                logger.info("Successfully synced order: {}", localOrder.getOrderNumber());
                
            } catch (Exception e) {
                logger.error("Failed to sync order: {}", localOrder.getOrderNumber(), e);
                localOrder.setSyncStatus(SyncStatus.FAILED);
                localOrderRepository.save(localOrder);
            }
        }
    }
    
    /**
     * Sync products from remote server
     */
    @Transactional
    public void syncProductsFromRemote() {
        logger.info("Syncing products from remote server...");
        
        try {
            List<Product> remoteProducts = webClient.get()
                    .uri(remoteUrl + "/products")
                    .retrieve()
                    .bodyToFlux(Product.class)
                    .collectList()
                    .block();
            
            for (Product remoteProduct : remoteProducts) {
                Optional<LocalProduct> existingProduct = localProductRepository
                        .findByRemoteId(remoteProduct.getId());
                
                if (existingProduct.isPresent()) {
                    LocalProduct localProduct = existingProduct.get();
                    // Update local product with remote data
                    updateLocalProductFromRemote(localProduct, remoteProduct);
                    localProductRepository.save(localProduct);
                } else {
                    // Create new local product from remote
                    LocalProduct newLocalProduct = convertToLocalProduct(remoteProduct);
                    localProductRepository.save(newLocalProduct);
                }
            }
            
            logger.info("Successfully synced {} products from remote", remoteProducts.size());
            
        } catch (Exception e) {
            logger.error("Failed to sync products from remote", e);
        }
    }
    
    /**
     * Sync orders from remote server
     */
    @Transactional
    public void syncOrdersFromRemote() {
        logger.info("Syncing orders from remote server...");
        
        try {
            List<Order> remoteOrders = webClient.get()
                    .uri(remoteUrl + "/orders")
                    .retrieve()
                    .bodyToFlux(Order.class)
                    .collectList()
                    .block();
            
            for (Order remoteOrder : remoteOrders) {
                Optional<LocalOrder> existingOrder = localOrderRepository
                        .findByRemoteId(remoteOrder.getId());
                
                if (existingOrder.isPresent()) {
                    LocalOrder localOrder = existingOrder.get();
                    // Update local order with remote data
                    updateLocalOrderFromRemote(localOrder, remoteOrder);
                    localOrderRepository.save(localOrder);
                } else {
                    // Create new local order from remote
                    LocalOrder newLocalOrder = convertToLocalOrder(remoteOrder);
                    localOrderRepository.save(newLocalOrder);
                }
            }
            
            logger.info("Successfully synced {} orders from remote", remoteOrders.size());
            
        } catch (Exception e) {
            logger.error("Failed to sync orders from remote", e);
        }
    }
    
    // Helper methods for conversion
    private Product convertToRemoteProduct(LocalProduct localProduct) {
        Product product = new Product();
        product.setId(localProduct.getRemoteId());
        product.setName(localProduct.getName());
        product.setPrice(localProduct.getPrice());
        product.setStockQuantity(localProduct.getStock());
        product.setDescription(localProduct.getDescription());
        product.setCategory(localProduct.getCategory());
        return product;
    }
    
    private LocalProduct convertToLocalProduct(Product remoteProduct) {
        LocalProduct localProduct = new LocalProduct();
        localProduct.setRemoteId(remoteProduct.getId());
        localProduct.setName(remoteProduct.getName());
        localProduct.setPrice(remoteProduct.getPrice());
        localProduct.setStock(remoteProduct.getStockQuantity());
        localProduct.setDescription(remoteProduct.getDescription());
        localProduct.setCategory(remoteProduct.getCategory());
        localProduct.setSyncStatus(SyncStatus.SYNCED);
        localProduct.setLastSync(LocalDateTime.now());
        return localProduct;
    }
    
    private void updateLocalProductFromRemote(LocalProduct localProduct, Product remoteProduct) {
        // Only update if remote is newer or if local has no pending changes
        if (localProduct.getSyncStatus() == SyncStatus.SYNCED) {
            localProduct.setName(remoteProduct.getName());
            localProduct.setPrice(remoteProduct.getPrice());
            localProduct.setStock(remoteProduct.getStockQuantity());
            localProduct.setDescription(remoteProduct.getDescription());
            localProduct.setCategory(remoteProduct.getCategory());
            localProduct.setLastSync(LocalDateTime.now());
        }
    }
    
    private Order convertToRemoteOrder(LocalOrder localOrder) {
        Order order = new Order();
        order.setId(localOrder.getRemoteId());
        order.setOrderNumber(localOrder.getOrderNumber());
        order.setTotalAmount(localOrder.getTotal());
        order.setCustomerName(localOrder.getCustomerName());
        return order;
    }
    
    private LocalOrder convertToLocalOrder(Order remoteOrder) {
        LocalOrder localOrder = new LocalOrder();
        localOrder.setRemoteId(remoteOrder.getId());
        localOrder.setOrderNumber(remoteOrder.getOrderNumber());
        localOrder.setTotal(remoteOrder.getTotalAmount());
        localOrder.setCustomerName(remoteOrder.getCustomerName());
        localOrder.setCustomerEmail(""); // Remote Order doesn't have email field
        localOrder.setSyncStatus(SyncStatus.SYNCED);
        localOrder.setLastSync(LocalDateTime.now());
        return localOrder;
    }
    
    private void updateLocalOrderFromRemote(LocalOrder localOrder, Order remoteOrder) {
        // Only update if remote is newer or if local has no pending changes
        if (localOrder.getSyncStatus() == SyncStatus.SYNCED) {
            localOrder.setOrderNumber(remoteOrder.getOrderNumber());
            localOrder.setTotal(remoteOrder.getTotalAmount());
            localOrder.setCustomerName(remoteOrder.getCustomerName());
            // Don't update email as remote Order doesn't have this field
            localOrder.setLastSync(LocalDateTime.now());
        }
    }
}
