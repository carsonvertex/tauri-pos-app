package com.tauri.pos.sqlite.controller;

import com.tauri.pos.sqlite.persistance.dao.LocalProductDao;
import com.tauri.pos.sqlite.persistance.eo.LocalProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/local-products")
@CrossOrigin(origins = "*")
public class LocalProductController {

    @Autowired
    private LocalProductDao localProductDao;

    // Get all local products
    @GetMapping
    public ResponseEntity<List<LocalProductEntity>> getAllLocalProducts() {
        List<LocalProductEntity> products = localProductDao.findAll();
        return ResponseEntity.ok(products);
    }

    // Get local product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<LocalProductEntity> getLocalProductById(@PathVariable Long productId) {
        return localProductDao.findById(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new local product
    @PostMapping
    public ResponseEntity<LocalProductEntity> createLocalProduct(@RequestBody LocalProductEntity product) {
        LocalProductEntity savedProduct = localProductDao.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    // Update local product
    @PutMapping("/{productId}")
    public ResponseEntity<LocalProductEntity> updateLocalProduct(@PathVariable Long productId, @RequestBody LocalProductEntity product) {
        if (!localProductDao.existsById(productId)) {
            return ResponseEntity.notFound().build();
        }
        product.setProductId(productId);
        LocalProductEntity updatedProduct = localProductDao.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete local product
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteLocalProduct(@PathVariable Long productId) {
        if (!localProductDao.existsById(productId)) {
            return ResponseEntity.notFound().build();
        }
        localProductDao.deleteById(productId);
        return ResponseEntity.noContent().build();
    }

    // Get local products by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<LocalProductEntity>> getLocalProductsByStatus(@PathVariable Integer status) {
        List<LocalProductEntity> products = localProductDao.findByStatus(status);
        return ResponseEntity.ok(products);
    }

    // Get local products by SKU
    @GetMapping("/sku/{sku}")
    public ResponseEntity<List<LocalProductEntity>> getLocalProductsBySku(@PathVariable String sku) {
        List<LocalProductEntity> products = localProductDao.findBySku(sku);
        return ResponseEntity.ok(products);
    }

    // Get local products by barcode
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<List<LocalProductEntity>> getLocalProductsByBarcode(@PathVariable String barcode) {
        List<LocalProductEntity> products = localProductDao.findByBarcode(barcode);
        return ResponseEntity.ok(products);
    }

    // Get total count of local products
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getLocalProductsCount() {
        Long count = localProductDao.countAllProducts();
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
}
