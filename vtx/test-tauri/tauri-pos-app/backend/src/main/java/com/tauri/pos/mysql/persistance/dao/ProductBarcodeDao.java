package com.tauri.pos.mysql.persistance.dao;

import com.tauri.pos.mysql.persistance.eo.ProductBarcodeEntity;
import com.tauri.pos.mysql.persistance.eo.ProductBarcodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductBarcodeDao extends JpaRepository<ProductBarcodeEntity, ProductBarcodeId> {
    List<ProductBarcodeEntity> findByProductId(Integer productId);
    List<ProductBarcodeEntity> findByBarcode(String barcode);
    List<ProductBarcodeEntity> findByStatus(Integer status);
    void deleteByProductId(Integer productId);
    void deleteByBarcode(String barcode);
}
