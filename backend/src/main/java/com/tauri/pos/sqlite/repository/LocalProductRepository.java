package com.tauri.pos.sqlite.repository;

import com.tauri.pos.sqlite.model.LocalProduct;
import com.tauri.pos.shared.enums.SyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalProductRepository extends JpaRepository<LocalProduct, Long> {
    
    Optional<LocalProduct> findByRemoteId(Long remoteId);
    
    List<LocalProduct> findBySyncStatus(SyncStatus syncStatus);
    
    @Query("SELECT p FROM LocalProduct p WHERE p.syncStatus IN :statuses")
    List<LocalProduct> findBySyncStatusIn(@Param("statuses") List<SyncStatus> statuses);
    
    @Query("SELECT p FROM LocalProduct p WHERE p.name LIKE %:name%")
    List<LocalProduct> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT p FROM LocalProduct p WHERE p.stock <= :threshold")
    List<LocalProduct> findLowStockProducts(@Param("threshold") Integer threshold);
    
    @Query("SELECT COUNT(p) FROM LocalProduct p WHERE p.syncStatus = :status")
    Long countBySyncStatus(@Param("status") SyncStatus status);
}
