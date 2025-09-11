package com.tauri.pos.sqlite.controller;

import com.tauri.pos.sqlite.persistance.dao.LocalProductBarcodeDao;
import com.tauri.pos.sqlite.persistance.eo.LocalProductBarcodeEntity;
import com.tauri.pos.sqlite.persistance.eo.LocalProductBarcodeId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/local-product-barcodes")
@CrossOrigin(origins = "*")
public class LocalProductBarcodeController {

    @Autowired
    private LocalProductBarcodeDao localProductBarcodeDao;

    // Get all local product barcodes
    @GetMapping
    public ResponseEntity<List<LocalProductBarcodeEntity>> getAllLocalProductBarcodes() {
        List<LocalProductBarcodeEntity> barcodes = localProductBarcodeDao.findAll();
        return ResponseEntity.ok(barcodes);
    }

    // Get local product barcode by product ID and barcode
    @GetMapping("/{productId}/{barcode}")
    public ResponseEntity<LocalProductBarcodeEntity> getLocalProductBarcodeById(
            @PathVariable Integer productId, 
            @PathVariable String barcode) {
        LocalProductBarcodeId id = new LocalProductBarcodeId(productId, barcode);
        return localProductBarcodeDao.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new local product barcode
    @PostMapping
    public ResponseEntity<LocalProductBarcodeEntity> createLocalProductBarcode(@RequestBody LocalProductBarcodeEntity barcode) {
        LocalProductBarcodeEntity savedBarcode = localProductBarcodeDao.save(barcode);
        return ResponseEntity.ok(savedBarcode);
    }

    // Update local product barcode
    @PutMapping("/{productId}/{barcode}")
    public ResponseEntity<LocalProductBarcodeEntity> updateLocalProductBarcode(
            @PathVariable Integer productId, 
            @PathVariable String barcode, 
            @RequestBody LocalProductBarcodeEntity barcodeEntity) {
        LocalProductBarcodeId id = new LocalProductBarcodeId(productId, barcode);
        if (!localProductBarcodeDao.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        barcodeEntity.setProductId(productId);
        barcodeEntity.setBarcode(barcode);
        LocalProductBarcodeEntity updatedBarcode = localProductBarcodeDao.save(barcodeEntity);
        return ResponseEntity.ok(updatedBarcode);
    }

    // Delete local product barcode
    @DeleteMapping("/{productId}/{barcode}")
    public ResponseEntity<Void> deleteLocalProductBarcode(
            @PathVariable Integer productId, 
            @PathVariable String barcode) {
        LocalProductBarcodeId id = new LocalProductBarcodeId(productId, barcode);
        if (!localProductBarcodeDao.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        localProductBarcodeDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Get local product barcodes by product ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<LocalProductBarcodeEntity>> getLocalProductBarcodesByProductId(@PathVariable Integer productId) {
        List<LocalProductBarcodeEntity> barcodes = localProductBarcodeDao.findByProductId(productId);
        return ResponseEntity.ok(barcodes);
    }

    // Get local product barcodes by barcode
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<List<LocalProductBarcodeEntity>> getLocalProductBarcodesByBarcode(@PathVariable String barcode) {
        List<LocalProductBarcodeEntity> barcodes = localProductBarcodeDao.findByBarcode(barcode);
        return ResponseEntity.ok(barcodes);
    }

    // Get local product barcodes by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<LocalProductBarcodeEntity>> getLocalProductBarcodesByStatus(@PathVariable Integer status) {
        List<LocalProductBarcodeEntity> barcodes = localProductBarcodeDao.findByStatus(status);
        return ResponseEntity.ok(barcodes);
    }

    // Get total count of local product barcodes
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getLocalProductBarcodesCount() {
        Long count = localProductBarcodeDao.countAllBarcodes();
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
}
