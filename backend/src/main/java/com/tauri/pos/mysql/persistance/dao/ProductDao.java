package com.tauri.pos.mysql.persistance.dao;

import com.tauri.pos.mysql.persistance.eo.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByStatus(Integer status);
    List<ProductEntity> findByBrandId(Integer brandId);
    List<ProductEntity> findBySku(String sku);
    List<ProductEntity> findByBarcode(String barcode);
}
