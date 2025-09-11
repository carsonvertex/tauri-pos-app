package com.tauri.pos.sqlite.persistance.dao;

import com.tauri.pos.sqlite.persistance.eo.LocalProductDescriptionEntity;
import com.tauri.pos.sqlite.persistance.eo.LocalProductDescriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalProductDescriptionDao extends JpaRepository<LocalProductDescriptionEntity, LocalProductDescriptionId> {

    List<LocalProductDescriptionEntity> findByProductId(Integer productId);
    
    List<LocalProductDescriptionEntity> findBySiteId(Integer siteId);
    
    List<LocalProductDescriptionEntity> findByLanguageId(Integer languageId);
    
    @Query("SELECT COUNT(lpd) FROM LocalProductDescriptionEntity lpd")
    Long countAllDescriptions();
}
