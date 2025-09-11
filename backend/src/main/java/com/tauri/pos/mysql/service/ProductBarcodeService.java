package com.tauri.pos.mysql.service;

import com.tauri.pos.mysql.model.ProductBarcode;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductBarcodeService {
    
    // Basic CRUD operations
    ProductBarcode createProductBarcode(ProductBarcode productBarcode);
    List<ProductBarcode> getAllProductBarcodes();
    ProductBarcode getProductBarcodeById(Integer productId, String barcode);
    ProductBarcode updateProductBarcode(Integer productId, String barcode, ProductBarcode productBarcode);
    void deleteProductBarcode(Integer productId, String barcode);
    
    // Find by individual fields
    List<ProductBarcode> getProductBarcodesByProductId(Integer productId);
    List<ProductBarcode> getProductBarcodesByBarcode(String barcode);
    List<ProductBarcode> getProductBarcodesByStatus(Integer status);
    List<ProductBarcode> getProductBarcodesByCreatedBy(Integer createdBy);
    List<ProductBarcode> getProductBarcodesByUpdatedBy(Integer updatedBy);
    
    // Complex searches
    List<ProductBarcode> getProductBarcodesByProductIdAndStatus(Integer productId, Integer status);
    List<ProductBarcode> searchProductBarcodesByBarcodeContaining(String barcode);
    List<ProductBarcode> getProductBarcodesByStatusAndCreatedBy(Integer status, Integer createdBy);
    
    // Validation methods
    boolean existsByProductIdAndBarcode(Integer productId, String barcode);
    boolean existsByBarcode(String barcode);
    
    // Bulk operations
    List<ProductBarcode> getProductBarcodesByProductIds(List<Integer> productIds);
    List<ProductBarcode> getProductBarcodesByBarcodes(List<String> barcodes);
    
    // Delete operations
    void deleteProductBarcodesByProductId(Integer productId);
    void deleteProductBarcodesByBarcode(String barcode);
    
    // Count operations
    long countProductBarcodesByProductId(Integer productId);
    long countProductBarcodesByBarcode(String barcode);
    
    // Status management
    ProductBarcode updateProductBarcodeStatus(Integer productId, String barcode, Integer status);
    
    // Date range operations
    List<ProductBarcode> getProductBarcodesByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
