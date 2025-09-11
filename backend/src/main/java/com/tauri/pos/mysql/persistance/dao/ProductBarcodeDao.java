package com.tauri.pos.mysql.persistance.dao;

import com.tauri.pos.mysql.persistance.eo.ProductBarcodeEntity;
import com.tauri.pos.mysql.persistance.eo.ProductBarcodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductBarcodeDao extends JpaRepository<ProductBarcodeEntity, ProductBarcodeId> {
    
    // Find by individual fields
    List<ProductBarcodeEntity> findByProductId(Integer productId);
    List<ProductBarcodeEntity> findByBarcode(String barcode);
    List<ProductBarcodeEntity> findByStatus(Integer status);
    List<ProductBarcodeEntity> findByCreatedBy(Integer createdBy);
    List<ProductBarcodeEntity> findByUpdatedBy(Integer updatedBy);
    
    // Find by composite key
    Optional<ProductBarcodeEntity> findByProductIdAndBarcode(Integer productId, String barcode);
    
    // Custom queries for complex searches
    @Query("SELECT pb FROM ProductBarcodeEntity pb WHERE pb.productId = :productId AND pb.status = :status")
    List<ProductBarcodeEntity> findByProductIdAndStatus(@Param("productId") Integer productId, @Param("status") Integer status);
    
    @Query("SELECT pb FROM ProductBarcodeEntity pb WHERE pb.barcode LIKE %:barcode%")
    List<ProductBarcodeEntity> findByBarcodeContaining(@Param("barcode") String barcode);
    
    @Query("SELECT pb FROM ProductBarcodeEntity pb WHERE pb.status = :status AND pb.createdBy = :createdBy")
    List<ProductBarcodeEntity> findByStatusAndCreatedBy(@Param("status") Integer status, @Param("createdBy") Integer createdBy);
    
    // Check if product-barcode combination exists
    boolean existsByProductIdAndBarcode(Integer productId, String barcode);
    
    // Check if barcode exists for any product
    boolean existsByBarcode(String barcode);
    
    // Find by multiple product IDs
    List<ProductBarcodeEntity> findByProductIdIn(List<Integer> productIds);
    
    // Find by multiple barcodes
    List<ProductBarcodeEntity> findByBarcodeIn(List<String> barcodes);
    
    // Delete by product ID
    void deleteByProductId(Integer productId);
    
    // Delete by barcode
    void deleteByBarcode(String barcode);
    
    // Delete by composite key
    void deleteByProductIdAndBarcode(Integer productId, String barcode);
    
    // Count barcodes for a product
    long countByProductId(Integer productId);
    
    // Count products for a barcode
    long countByBarcode(String barcode);
}
