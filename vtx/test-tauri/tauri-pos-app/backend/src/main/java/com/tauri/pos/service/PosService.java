package com.tauri.pos.service;

import com.tauri.pos.model.Product;
import com.tauri.pos.model.Order;
import com.tauri.pos.model.OrderItem;
import com.tauri.pos.repository.ProductRepository;
import com.tauri.pos.repository.OrderRepository;
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
            order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        
        // Calculate total amount
        BigDecimal total = BigDecimal.ZERO;
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                total = total.add(item.getTotalPrice());
            }
        }
        order.setTotalAmount(total);
        
        return orderRepository.save(order);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setStatus(status);
        return orderRepository.save(order);
    }

    // Initialize with sample data
    public void initializeSampleData() {
        if (productRepository.count() == 0) {
            Product p1 = new Product("Laptop", "High-performance laptop", new BigDecimal("999.99"), 10, "Electronics");
            Product p2 = new Product("Mouse", "Wireless mouse", new BigDecimal("29.99"), 50, "Electronics");
            Product p3 = new Product("Coffee", "Premium coffee beans", new BigDecimal("12.99"), 100, "Beverages");
            
            productRepository.save(p1);
            productRepository.save(p2);
            productRepository.save(p3);
        }
    }
}
