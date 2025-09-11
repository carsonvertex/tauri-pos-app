package com.tauri.pos.sqlite.persistance.dao;

import com.tauri.pos.sqlite.persistance.eo.LocalProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalProductDao extends JpaRepository<LocalProductEntity, Long> {

    List<LocalProductEntity> findByStatus(Integer status);
    
    List<LocalProductEntity> findBySku(String sku);
    
    List<LocalProductEntity> findByBarcode(String barcode);
    
    List<LocalProductEntity> findByBrandId(Integer brandId);
    
    @Query("SELECT COUNT(lp) FROM LocalProductEntity lp")
    Long countAllProducts();
}
