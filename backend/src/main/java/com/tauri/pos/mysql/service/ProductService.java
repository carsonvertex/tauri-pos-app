package com.tauri.pos.mysql.service;

import com.tauri.pos.mysql.model.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProductService {
    
    // Basic CRUD operations
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long productId);
    Product updateProductById(Long productId, Product product);
    void deleteProductById(Long productId);
    
    // Find by various fields
    List<Product> getProductsByStatus(Integer status);
    List<Product> getProductsByBrandId(Integer brandId);
    List<Product> getProductsByEbayId(Integer ebayId);
    List<Product> getProductsByModelNumber(String modelNumber);
    List<Product> getProductsBySku(String sku);
    List<Product> getProductsByBarcode(String barcode);
    List<Product> getProductsByWeightClassId(Integer weightClassId);
    
    // Find by created/updated by
    List<Product> getProductsByCreatedBy(Integer createdBy);
    List<Product> getProductsByUpdatedBy(Integer updatedBy);
    
    // Complex searches
    List<Product> getProductsByStatusAndBrandId(Integer status, Integer brandId);
    List<Product> searchProductsBySkuOrModelNumber(String searchTerm);
    List<Product> getAvailableProductsByDate(LocalDate date);
    
    // Validation methods
    boolean existsBySku(String sku);
    boolean existsByBarcode(String barcode);
    
    // Bulk operations
    List<Product> getProductsBySkus(List<String> skus);
    List<Product> getProductsByBarcodes(List<String> barcodes);
    
    // Product status management
    Product updateProductStatus(Long productId, Integer status);
    List<Product> getProductsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
