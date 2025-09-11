package com.tauri.pos.mysql.controller;

import com.tauri.pos.mysql.model.AdjustmentDetail;
import com.tauri.pos.mysql.service.AdjustmentDetailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adjustment-detail")
@CrossOrigin(origins = "*")
@ConditionalOnProperty(name = "spring.datasource.mysql.enabled", havingValue = "true", matchIfMissing = false)
public class AdjustmentDetailController {
    private final AdjustmentDetailService adjustmentDetailService;

    public AdjustmentDetailController(AdjustmentDetailService adjustmentDetailService) {
        this.adjustmentDetailService = adjustmentDetailService;
    }

    /**
     * Create a new adjustment detail
     */
    @PostMapping
    public ResponseEntity<AdjustmentDetail> createAdjustmentDetail(@RequestBody AdjustmentDetail adjustmentDetail) {
        try {
            AdjustmentDetail createdDetail = adjustmentDetailService.createAdjustmentDetail(adjustmentDetail);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Get all adjustment details
     */
    @GetMapping
    public ResponseEntity<List<AdjustmentDetail>> getAllAdjustmentDetails() {
        System.out.println("getAllAdjustmentDetails");
        try {
            List<AdjustmentDetail> details = adjustmentDetailService.getAllAdjustmentDetails();
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get an adjustment detail by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdjustmentDetail> getAdjustmentDetailById(@PathVariable Long id) {
        System.out.println("getAdjustmentDetailById for ID: " + id);
        try {
            AdjustmentDetail detail = adjustmentDetailService.getAdjustmentDetailById(id);
            if (detail != null) {
                System.out.println("Returned detail: " + detail);
                return ResponseEntity.ok(detail);
            } else {
                System.out.println("AdjustmentDetail not found for ID: " + id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Internal Server Error for ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update an adjustment detail by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdjustmentDetail> updateAdjustmentDetail(
            @PathVariable Long id, 
            @RequestBody AdjustmentDetail adjustmentDetail) {
        try {
            AdjustmentDetail updatedDetail = adjustmentDetailService.updateAdjustmentDetailById(id, adjustmentDetail);
            if (updatedDetail != null) {
                return ResponseEntity.ok(updatedDetail);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Delete an adjustment detail by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdjustmentDetail(@PathVariable Long id) {
        try {
            adjustmentDetailService.deleteAdjustmentDetailById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get adjustment details by adjustment ID
     */
    @GetMapping("/adjustment/{adjustmentId}")
    public ResponseEntity<List<AdjustmentDetail>> getAdjustmentDetailsByAdjustmentId(@PathVariable Long adjustmentId) {
        try {
            List<AdjustmentDetail> details = adjustmentDetailService.getAdjustmentDetailsByAdjustmentId(adjustmentId);
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get adjustment details by product ID
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<AdjustmentDetail>> getAdjustmentDetailsByProductId(@PathVariable Long productId) {
        try {
            List<AdjustmentDetail> details = adjustmentDetailService.getAdjustmentDetailsByProductId(productId);
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get adjustment details by warehouse ID
     */
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<AdjustmentDetail>> getAdjustmentDetailsByWarehouseId(@PathVariable Long warehouseId) {
        try {
            List<AdjustmentDetail> details = adjustmentDetailService.getAdjustmentDetailsByWarehouseId(warehouseId);
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get adjustment details by bin ID
     */
    @GetMapping("/bin/{binId}")
    public ResponseEntity<List<AdjustmentDetail>> getAdjustmentDetailsByBinId(@PathVariable Long binId) {
        try {
            List<AdjustmentDetail> details = adjustmentDetailService.getAdjustmentDetailsByBinId(binId);
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Adjustment Detail Controller is running!");
    }
}
