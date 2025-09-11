package com.tauri.pos.config;

import com.tauri.pos.mysql.model.AdjustmentDetail;
import com.tauri.pos.mysql.service.AdjustmentDetailService;
import com.tauri.pos.sqlite.model.LocalOrderItem;
import com.tauri.pos.sqlite.service.LocalOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class MultiDatabaseTestController {
    
    private final LocalOrderItemService localOrderItemService;
    
    @Autowired(required = false)
    private AdjustmentDetailService adjustmentDetailService;
    
    public MultiDatabaseTestController(LocalOrderItemService localOrderItemService) {
        this.localOrderItemService = localOrderItemService;
    }
    
    @GetMapping("/databases")
    public ResponseEntity<Map<String, Object>> testDatabases() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Test SQLite connection
            var sqliteItems = localOrderItemService.getAllLocalOrderItem();
            result.put("sqlite", Map.of(
                "status", "connected",
                "items_count", sqliteItems.size(),
                "message", "SQLite database is working"
            ));
        } catch (Exception e) {
            result.put("sqlite", Map.of(
                "status", "error",
                "message", "SQLite connection failed: " + e.getMessage()
            ));
        }
        
        try {
            // Test MySQL connection
            if (adjustmentDetailService != null) {
                var mysqlDetails = adjustmentDetailService.getAllAdjustmentDetails();
                result.put("mysql", Map.of(
                    "status", "connected",
                    "details_count", mysqlDetails.size(),
                    "message", "MySQL database is working"
                ));
            } else {
                result.put("mysql", Map.of(
                    "status", "disabled",
                    "message", "MySQL configuration not available"
                ));
            }
        } catch (Exception e) {
            result.put("mysql", Map.of(
                "status", "error",
                "message", "MySQL connection failed: " + e.getMessage()
            ));
        }
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/adjustment-detail")
    public ResponseEntity<AdjustmentDetail> createTestAdjustmentDetail() {
        try {
            if (adjustmentDetailService == null) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(null);
            }
            
            AdjustmentDetail testDetail = AdjustmentDetail.builder()
                .adjustmentId(1L)
                .productId(1L)
                .warehouseId(1L)
                .binId(1L)
                .beforeAdjustmentQty(100)
                .adjustmentQty(10)
                .afterAdjustmentQty(110)
                .createdBy(1L)
                .updatedBy(1L)
                .build();
                
            AdjustmentDetail created = adjustmentDetailService.createAdjustmentDetail(testDetail);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
