package com.tauri.pos.mysql.service;

import com.tauri.pos.mysql.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long productId);
    Product updateProductById(Long productId, Product product);
    void deleteProductById(Long productId);
    List<Product> getProductsByStatus(Integer status);
    List<Product> getProductsByBrandId(Integer brandId);
    List<Product> getProductsBySku(String sku);
    List<Product> getProductsByBarcode(String barcode);
}
