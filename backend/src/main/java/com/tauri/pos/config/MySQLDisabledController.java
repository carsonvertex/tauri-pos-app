package com.tauri.pos.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/adjustment-detail")
@CrossOrigin(origins = "*")
@ConditionalOnMissingBean(name = "adjustmentDetailController")
public class MySQLDisabledController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllAdjustmentDetails() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Map.of(
                "error", "MySQL database is not configured",
                "message", "Please enable MySQL configuration in application.properties to use adjustment detail endpoints",
                "status", 503
            ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAdjustmentDetailById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Map.of(
                "error", "MySQL database is not configured",
                "message", "Please enable MySQL configuration in application.properties to use adjustment detail endpoints",
                "status", 503
            ));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAdjustmentDetail(@RequestBody Object adjustmentDetail) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Map.of(
                "error", "MySQL database is not configured",
                "message", "Please enable MySQL configuration in application.properties to use adjustment detail endpoints",
                "status", 503
            ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAdjustmentDetail(@PathVariable Long id, @RequestBody Object adjustmentDetail) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Map.of(
                "error", "MySQL database is not configured",
                "message", "Please enable MySQL configuration in application.properties to use adjustment detail endpoints",
                "status", 503
            ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAdjustmentDetail(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Map.of(
                "error", "MySQL database is not configured",
                "message", "Please enable MySQL configuration in application.properties to use adjustment detail endpoints",
                "status", 503
            ));
    }

    @GetMapping("/adjustment/{adjustmentId}")
    public ResponseEntity<Map<String, Object>> getAdjustmentDetailsByAdjustmentId(@PathVariable Long adjustmentId) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Map.of(
                "error", "MySQL database is not configured",
                "message", "Please enable MySQL configuration in application.properties to use adjustment detail endpoints",
                "status", 503
            ));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Map<String, Object>> getAdjustmentDetailsByProductId(@PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Map.of(
                "error", "MySQL database is not configured",
                "message", "Please enable MySQL configuration in application.properties to use adjustment detail endpoints",
                "status", 503
            ));
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<Map<String, Object>> getAdjustmentDetailsByWarehouseId(@PathVariable Long warehouseId) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Map.of(
                "error", "MySQL database is not configured",
                "message", "Please enable MySQL configuration in application.properties to use adjustment detail endpoints",
                "status", 503
            ));
    }

    @GetMapping("/bin/{binId}")
    public ResponseEntity<Map<String, Object>> getAdjustmentDetailsByBinId(@PathVariable Long binId) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Map.of(
                "error", "MySQL database is not configured",
                "message", "Please enable MySQL configuration in application.properties to use adjustment detail endpoints",
                "status", 503
            ));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Map.of(
                "error", "MySQL database is not configured",
                "message", "Please enable MySQL configuration in application.properties to use adjustment detail endpoints",
                "status", 503
            ));
    }
}
