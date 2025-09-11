package com.tauri.pos.sqlite.persistance.dao;

import com.tauri.pos.sqlite.persistance.eo.LocalProductBarcodeEntity;
import com.tauri.pos.sqlite.persistance.eo.LocalProductBarcodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalProductBarcodeDao extends JpaRepository<LocalProductBarcodeEntity, LocalProductBarcodeId> {

    List<LocalProductBarcodeEntity> findByProductId(Integer productId);
    
    List<LocalProductBarcodeEntity> findByBarcode(String barcode);
    
    List<LocalProductBarcodeEntity> findByStatus(Integer status);
    
    @Query("SELECT COUNT(lpb) FROM LocalProductBarcodeEntity lpb")
    Long countAllBarcodes();
}
