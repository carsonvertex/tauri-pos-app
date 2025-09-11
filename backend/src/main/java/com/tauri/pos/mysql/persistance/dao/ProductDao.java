package com.tauri.pos.mysql.persistance.dao;

import com.tauri.pos.mysql.persistance.eo.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDao extends JpaRepository<ProductEntity, Long> {
    
    // Find by various fields
    List<ProductEntity> findByStatus(Integer status);
    List<ProductEntity> findByBrandId(Integer brandId);
    List<ProductEntity> findByEbayId(Integer ebayId);
    List<ProductEntity> findByModelNumber(String modelNumber);
    List<ProductEntity> findBySku(String sku);
    List<ProductEntity> findByBarcode(String barcode);
    List<ProductEntity> findByWeightClassId(Integer weightClassId);
    
    // Find by created/updated by
    List<ProductEntity> findByCreatedBy(Integer createdBy);
    List<ProductEntity> findByUpdatedBy(Integer updatedBy);
    
    // Custom queries for complex searches
    @Query("SELECT p FROM ProductEntity p WHERE p.status = :status AND p.brandId = :brandId")
    List<ProductEntity> findByStatusAndBrandId(@Param("status") Integer status, @Param("brandId") Integer brandId);
    
    @Query("SELECT p FROM ProductEntity p WHERE p.sku LIKE %:sku% OR p.modelNumber LIKE %:modelNumber%")
    List<ProductEntity> findBySkuOrModelNumberContaining(@Param("sku") String sku, @Param("modelNumber") String modelNumber);
    
    @Query("SELECT p FROM ProductEntity p WHERE p.dateAvailable <= :date AND p.status = 1")
    List<ProductEntity> findAvailableProductsByDate(@Param("date") java.time.LocalDate date);
    
    // Check if product exists by SKU
    boolean existsBySku(String sku);
    
    // Check if product exists by barcode
    boolean existsByBarcode(String barcode);
    
    // Find by multiple SKUs
    List<ProductEntity> findBySkuIn(List<String> skus);
    
    // Find by multiple barcodes
    List<ProductEntity> findByBarcodeIn(List<String> barcodes);
}
