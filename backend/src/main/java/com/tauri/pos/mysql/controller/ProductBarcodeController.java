package com.tauri.pos.mysql.controller;

import com.tauri.pos.mysql.model.ProductBarcode;
import com.tauri.pos.mysql.service.ProductBarcodeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/product-barcodes")
@CrossOrigin(origins = "*")
@ConditionalOnProperty(name = "spring.datasource.mysql.jdbc-url")
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
            ProductBarcode updatedProductBarcode = productBarcodeService.updateProductBarcode(productId, barcode, productBarcode);
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
            productBarcodeService.deleteProductBarcode(productId, barcode);
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
     * Search product barcodes by barcode containing
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductBarcode>> searchProductBarcodes(@RequestParam String barcode) {
        try {
            List<ProductBarcode> productBarcodes = productBarcodeService.searchProductBarcodesByBarcodeContaining(barcode);
            return ResponseEntity.ok(productBarcodes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get product barcodes by product ID and status
     */
    @GetMapping("/product/{productId}/status/{status}")
    public ResponseEntity<List<ProductBarcode>> getProductBarcodesByProductIdAndStatus(
            @PathVariable Integer productId,
            @PathVariable Integer status) {
        try {
            List<ProductBarcode> productBarcodes = productBarcodeService.getProductBarcodesByProductIdAndStatus(productId, status);
            return ResponseEntity.ok(productBarcodes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update product barcode status
     */
    @PatchMapping("/{productId}/{barcode}/status")
    public ResponseEntity<ProductBarcode> updateProductBarcodeStatus(
            @PathVariable Integer productId,
            @PathVariable String barcode,
            @RequestParam Integer status) {
        try {
            ProductBarcode updatedProductBarcode = productBarcodeService.updateProductBarcodeStatus(productId, barcode, status);
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
     * Check if product barcode exists
     */
    @GetMapping("/exists/{productId}/{barcode}")
    public ResponseEntity<Boolean> existsByProductIdAndBarcode(
            @PathVariable Integer productId,
            @PathVariable String barcode) {
        try {
            boolean exists = productBarcodeService.existsByProductIdAndBarcode(productId, barcode);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Check if barcode exists for any product
     */
    @GetMapping("/exists/barcode/{barcode}")
    public ResponseEntity<Boolean> existsByBarcode(@PathVariable String barcode) {
        try {
            boolean exists = productBarcodeService.existsByBarcode(barcode);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get product barcodes by multiple product IDs
     */
    @PostMapping("/bulk/products")
    public ResponseEntity<List<ProductBarcode>> getProductBarcodesByProductIds(@RequestBody List<Integer> productIds) {
        try {
            List<ProductBarcode> productBarcodes = productBarcodeService.getProductBarcodesByProductIds(productIds);
            return ResponseEntity.ok(productBarcodes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Get product barcodes by multiple barcodes
     */
    @PostMapping("/bulk/barcodes")
    public ResponseEntity<List<ProductBarcode>> getProductBarcodesByBarcodes(@RequestBody List<String> barcodes) {
        try {
            List<ProductBarcode> productBarcodes = productBarcodeService.getProductBarcodesByBarcodes(barcodes);
            return ResponseEntity.ok(productBarcodes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Delete all product barcodes by product ID
     */
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProductBarcodesByProductId(@PathVariable Integer productId) {
        try {
            productBarcodeService.deleteProductBarcodesByProductId(productId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete all product barcodes by barcode
     */
    @DeleteMapping("/barcode/{barcode}")
    public ResponseEntity<Void> deleteProductBarcodesByBarcode(@PathVariable String barcode) {
        try {
            productBarcodeService.deleteProductBarcodesByBarcode(barcode);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Count barcodes for a product
     */
    @GetMapping("/count/product/{productId}")
    public ResponseEntity<Long> countProductBarcodesByProductId(@PathVariable Integer productId) {
        try {
            long count = productBarcodeService.countProductBarcodesByProductId(productId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Count products for a barcode
     */
    @GetMapping("/count/barcode/{barcode}")
    public ResponseEntity<Long> countProductBarcodesByBarcode(@PathVariable String barcode) {
        try {
            long count = productBarcodeService.countProductBarcodesByBarcode(barcode);
            return ResponseEntity.ok(count);
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
