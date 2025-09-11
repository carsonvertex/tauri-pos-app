package com.tauri.pos.mysql.persistance.dao;

import com.tauri.pos.mysql.persistance.eo.ProductDescriptionEntity;
import com.tauri.pos.mysql.persistance.eo.ProductDescriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDescriptionDao extends JpaRepository<ProductDescriptionEntity, ProductDescriptionId> {
    List<ProductDescriptionEntity> findByProductId(Integer productId);
    List<ProductDescriptionEntity> findBySiteId(Integer siteId);
    List<ProductDescriptionEntity> findByLanguageId(Integer languageId);
    void deleteByProductId(Integer productId);
    void deleteBySiteId(Integer siteId);
    void deleteByLanguageId(Integer languageId);
}
