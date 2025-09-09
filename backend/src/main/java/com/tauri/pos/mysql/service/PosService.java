package com.tauri.pos.mysql.service;

import com.tauri.pos.mysql.model.Product;
import com.tauri.pos.mysql.model.Order;
import com.tauri.pos.mysql.model.OrderItem;
import com.tauri.pos.mysql.repository.ProductRepository;
import com.tauri.pos.mysql.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PosService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private OrderRepository orderRepository;

    // Product methods
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStockQuantity(productDetails.getStockQuantity());
        product.setCategory(productDetails.getCategory());
        
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Order methods
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        // Generate order number if not provided
        if (order.getOrderNumber() == null || order.getOrderNumber().isEmpty()) {
            order.setOrderNumber(generateOrderNumber());
        }
        
        // Calculate total if not provided
        if (order.getTotalAmount() == null) {
            order.setTotalAmount(BigDecimal.ZERO);
        }
        
        return orderRepository.save(order);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setOrderNumber(orderDetails.getOrderNumber());
        order.setTotalAmount(orderDetails.getTotalAmount());
        order.setStatus(orderDetails.getStatus());
        order.setCustomerName(orderDetails.getCustomerName());
        
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // Sample data initialization
    public void initializeSampleData() {
        // Check if products already exist
        if (productRepository.count() == 0) {
            // Create sample products
            Product product1 = new Product();
            product1.setName("Coffee");
            product1.setDescription("Fresh brewed coffee");
            product1.setPrice(new BigDecimal("3.50"));
            product1.setStockQuantity(100);
            product1.setCategory("Beverages");
            productRepository.save(product1);

            Product product2 = new Product();
            product2.setName("Sandwich");
            product2.setDescription("Delicious sandwich");
            product2.setPrice(new BigDecimal("8.99"));
            product2.setStockQuantity(50);
            product2.setCategory("Food");
            productRepository.save(product2);

            Product product3 = new Product();
            product3.setName("Cookie");
            product3.setDescription("Fresh baked cookie");
            product3.setPrice(new BigDecimal("2.25"));
            product3.setStockQuantity(75);
            product3.setCategory("Desserts");
            productRepository.save(product3);

            System.out.println("✅ Sample products created successfully!");
        } else {
            System.out.println("ℹ️ Products already exist, skipping sample data creation.");
        }
    }

    // Helper methods
    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
