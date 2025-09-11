package com.tauri.pos.mysql.controller;

import com.tauri.pos.mysql.model.ProductBarcode;
import com.tauri.pos.mysql.service.ProductBarcodeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-barcodes")
@CrossOrigin(origins = "*")
@ConditionalOnProperty(name = "spring.datasource.mysql.enabled", havingValue = "true", matchIfMissing = false)
public class ProductBarcodeController {
    private final ProductBarcodeService productBarcodeService;

    public ProductBarcodeController(ProductBarcodeService productBarcodeService) {
        this.productBarcodeService = productBarcodeService;
    }

    /**
     * Create a new product barcode
     */
    @PostMapping
    public ResponseEntity<ProductBarcode> createProductBarcode(@RequestBody ProductBarcode productBarcode) {
        try {
            ProductBarcode createdProductBarcode = productBarcodeService.createProductBarcode(productBarcode);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProductBarcode);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Get all product barcodes
     */
    @GetMapping
    public ResponseEntity<List<ProductBarcode>> getAllProductBarcodes() {
        try {
            List<ProductBarcode> productBarcodes = productBarcodeService.getAllProductBarcodes();
            return ResponseEntity.ok(productBarcodes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get a product barcode by composite key (productId + barcode)
     */
    @GetMapping("/{productId}/{barcode}")
    public ResponseEntity<ProductBarcode> getProductBarcodeById(
            @PathVariable Integer productId, 
            @PathVariable String barcode) {
        try {
            ProductBarcode productBarcode = productBarcodeService.getProductBarcodeById(productId, barcode);
            if (productBarcode != null) {
                return ResponseEntity.ok(productBarcode);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update a product barcode by composite key
     */
    @PutMapping("/{productId}/{barcode}")
    public ResponseEntity<ProductBarcode> updateProductBarcode(
            @PathVariable Integer productId,
            @PathVariable String barcode,
            @RequestBody ProductBarcode productBarcode) {
        try {
            ProductBarcode updatedProductBarcode = productBarcodeService.updateProductBarcodeById(productId, barcode, productBarcode);
            if (updatedProductBarcode != null) {
                return ResponseEntity.ok(updatedProductBarcode);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Delete a product barcode by composite key
     */
    @DeleteMapping("/{productId}/{barcode}")
    public ResponseEntity<Void> deleteProductBarcode(
            @PathVariable Integer productId,
            @PathVariable String barcode) {
        try {
            productBarcodeService.deleteProductBarcodeById(productId, barcode);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get product barcodes by product ID
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductBarcode>> getProductBarcodesByProductId(@PathVariable Integer productId) {
        try {
            List<ProductBarcode> productBarcodes = productBarcodeService.getProductBarcodesByProductId(productId);
            return ResponseEntity.ok(productBarcodes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get product barcodes by barcode
     */
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<List<ProductBarcode>> getProductBarcodesByBarcode(@PathVariable String barcode) {
        try {
            List<ProductBarcode> productBarcodes = productBarcodeService.getProductBarcodesByBarcode(barcode);
            return ResponseEntity.ok(productBarcodes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get product barcodes by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProductBarcode>> getProductBarcodesByStatus(@PathVariable Integer status) {
        try {
            List<ProductBarcode> productBarcodes = productBarcodeService.getProductBarcodesByStatus(status);
            return ResponseEntity.ok(productBarcodes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Product Barcode Controller is running!");
    }
}
