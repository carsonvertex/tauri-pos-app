package com.tauri.pos.mysql.controller;

import com.tauri.pos.mysql.model.ProductDescription;
import com.tauri.pos.mysql.service.ProductDescriptionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-descriptions")
@CrossOrigin(origins = "*")
@ConditionalOnProperty(name = "spring.datasource.mysql.enabled", havingValue = "true", matchIfMissing = false)
public class ProductDescriptionController {
    private final ProductDescriptionService productDescriptionService;

    public ProductDescriptionController(ProductDescriptionService productDescriptionService) {
        this.productDescriptionService = productDescriptionService;
    }

    /**
     * Create a new product description
     */
    @PostMapping
    public ResponseEntity<ProductDescription> createProductDescription(@RequestBody ProductDescription productDescription) {
        try {
            ProductDescription createdProductDescription = productDescriptionService.createProductDescription(productDescription);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDescription);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Get all product descriptions
     */
    @GetMapping
    public ResponseEntity<List<ProductDescription>> getAllProductDescriptions() {
        try {
            List<ProductDescription> productDescriptions = productDescriptionService.getAllProductDescriptions();
            return ResponseEntity.ok(productDescriptions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get a product description by triple composite key (productId + siteId + languageId)
     */
    @GetMapping("/{productId}/{siteId}/{languageId}")
    public ResponseEntity<ProductDescription> getProductDescriptionById(
            @PathVariable Integer productId,
            @PathVariable Integer siteId,
            @PathVariable Integer languageId) {
        try {
            ProductDescription productDescription = productDescriptionService.getProductDescriptionById(productId, siteId, languageId);
            if (productDescription != null) {
                return ResponseEntity.ok(productDescription);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update a product description by triple composite key
     */
    @PutMapping("/{productId}/{siteId}/{languageId}")
    public ResponseEntity<ProductDescription> updateProductDescription(
            @PathVariable Integer productId,
            @PathVariable Integer siteId,
            @PathVariable Integer languageId,
            @RequestBody ProductDescription productDescription) {
        try {
            ProductDescription updatedProductDescription = productDescriptionService.updateProductDescriptionById(productId, siteId, languageId, productDescription);
            if (updatedProductDescription != null) {
                return ResponseEntity.ok(updatedProductDescription);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Delete a product description by triple composite key
     */
    @DeleteMapping("/{productId}/{siteId}/{languageId}")
    public ResponseEntity<Void> deleteProductDescription(
            @PathVariable Integer productId,
            @PathVariable Integer siteId,
            @PathVariable Integer languageId) {
        try {
            productDescriptionService.deleteProductDescriptionById(productId, siteId, languageId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get product descriptions by product ID
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductDescription>> getProductDescriptionsByProductId(@PathVariable Integer productId) {
        try {
            List<ProductDescription> productDescriptions = productDescriptionService.getProductDescriptionsByProductId(productId);
            return ResponseEntity.ok(productDescriptions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get product descriptions by site ID
     */
    @GetMapping("/site/{siteId}")
    public ResponseEntity<List<ProductDescription>> getProductDescriptionsBySiteId(@PathVariable Integer siteId) {
        try {
            List<ProductDescription> productDescriptions = productDescriptionService.getProductDescriptionsBySiteId(siteId);
            return ResponseEntity.ok(productDescriptions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get product descriptions by language ID
     */
    @GetMapping("/language/{languageId}")
    public ResponseEntity<List<ProductDescription>> getProductDescriptionsByLanguageId(@PathVariable Integer languageId) {
        try {
            List<ProductDescription> productDescriptions = productDescriptionService.getProductDescriptionsByLanguageId(languageId);
            return ResponseEntity.ok(productDescriptions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Product Description Controller is running!");
    }
}
