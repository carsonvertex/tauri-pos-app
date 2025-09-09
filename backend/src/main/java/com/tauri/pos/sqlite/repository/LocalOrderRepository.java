package com.tauri.pos.sqlite.repository;

import com.tauri.pos.sqlite.model.LocalOrder;
import com.tauri.pos.shared.enums.OrderStatus;
import com.tauri.pos.shared.enums.SyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocalOrderRepository extends JpaRepository<LocalOrder, Long> {
    
    Optional<LocalOrder> findByRemoteId(Long remoteId);
    
    Optional<LocalOrder> findByOrderNumber(String orderNumber);
    
    List<LocalOrder> findBySyncStatus(SyncStatus syncStatus);
    
    List<LocalOrder> findByStatus(OrderStatus status);
    
    @Query("SELECT o FROM LocalOrder o WHERE o.syncStatus IN :statuses")
    List<LocalOrder> findBySyncStatusIn(@Param("statuses") List<SyncStatus> statuses);
    
    @Query("SELECT o FROM LocalOrder o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    List<LocalOrder> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                          @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT o FROM LocalOrder o WHERE o.customerName LIKE %:customerName%")
    List<LocalOrder> findByCustomerNameContaining(@Param("customerName") String customerName);
    
    @Query("SELECT COUNT(o) FROM LocalOrder o WHERE o.syncStatus = :status")
    Long countBySyncStatus(@Param("status") SyncStatus status);
    
    @Query("SELECT COUNT(o) FROM LocalOrder o WHERE o.status = :status")
    Long countByStatus(@Param("status") OrderStatus status);
}
