package com.tauri.pos.mysql.controller;

import com.tauri.pos.mysql.model.Product;
import com.tauri.pos.mysql.service.ProductService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@ConditionalOnProperty(name = "spring.datasource.mysql.jdbc-url")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Create a new product
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product createdProduct = productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Get all products
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get a product by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product != null) {
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update a product by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id, 
            @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProductById(id, product);
            if (updatedProduct != null) {
                return ResponseEntity.ok(updatedProduct);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Delete a product by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get products by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Product>> getProductsByStatus(@PathVariable Integer status) {
        try {
            List<Product> products = productService.getProductsByStatus(status);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get products by brand ID
     */
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<Product>> getProductsByBrandId(@PathVariable Integer brandId) {
        try {
            List<Product> products = productService.getProductsByBrandId(brandId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get products by SKU
     */
    @GetMapping("/sku/{sku}")
    public ResponseEntity<List<Product>> getProductsBySku(@PathVariable String sku) {
        try {
            List<Product> products = productService.getProductsBySku(sku);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get products by barcode
     */
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<List<Product>> getProductsByBarcode(@PathVariable String barcode) {
        try {
            List<Product> products = productService.getProductsByBarcode(barcode);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get products by model number
     */
    @GetMapping("/model/{modelNumber}")
    public ResponseEntity<List<Product>> getProductsByModelNumber(@PathVariable String modelNumber) {
        try {
            List<Product> products = productService.getProductsByModelNumber(modelNumber);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get products by eBay ID
     */
    @GetMapping("/ebay/{ebayId}")
    public ResponseEntity<List<Product>> getProductsByEbayId(@PathVariable Integer ebayId) {
        try {
            List<Product> products = productService.getProductsByEbayId(ebayId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Search products by SKU or model number
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String term) {
        try {
            List<Product> products = productService.searchProductsBySkuOrModelNumber(term);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get products by status and brand ID
     */
    @GetMapping("/status/{status}/brand/{brandId}")
    public ResponseEntity<List<Product>> getProductsByStatusAndBrandId(
            @PathVariable Integer status, 
            @PathVariable Integer brandId) {
        try {
            List<Product> products = productService.getProductsByStatusAndBrandId(status, brandId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get available products by date
     */
    @GetMapping("/available")
    public ResponseEntity<List<Product>> getAvailableProducts(@RequestParam String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<Product> products = productService.getAvailableProductsByDate(localDate);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Update product status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Product> updateProductStatus(
            @PathVariable Long id, 
            @RequestParam Integer status) {
        try {
            Product updatedProduct = productService.updateProductStatus(id, status);
            if (updatedProduct != null) {
                return ResponseEntity.ok(updatedProduct);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Check if product exists by SKU
     */
    @GetMapping("/exists/sku/{sku}")
    public ResponseEntity<Boolean> existsBySku(@PathVariable String sku) {
        try {
            boolean exists = productService.existsBySku(sku);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Check if product exists by barcode
     */
    @GetMapping("/exists/barcode/{barcode}")
    public ResponseEntity<Boolean> existsByBarcode(@PathVariable String barcode) {
        try {
            boolean exists = productService.existsByBarcode(barcode);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get products by multiple SKUs
     */
    @PostMapping("/bulk/skus")
    public ResponseEntity<List<Product>> getProductsBySkus(@RequestBody List<String> skus) {
        try {
            List<Product> products = productService.getProductsBySkus(skus);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Get products by multiple barcodes
     */
    @PostMapping("/bulk/barcodes")
    public ResponseEntity<List<Product>> getProductsByBarcodes(@RequestBody List<String> barcodes) {
        try {
            List<Product> products = productService.getProductsByBarcodes(barcodes);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Product Controller is running!");
    }
}
