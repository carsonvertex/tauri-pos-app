package com.tauri.pos.mysql.service;

import com.tauri.pos.mysql.model.ProductBarcode;

import java.util.List;

public interface ProductBarcodeService {
    ProductBarcode createProductBarcode(ProductBarcode productBarcode);
    List<ProductBarcode> getAllProductBarcodes();
    ProductBarcode getProductBarcodeById(Integer productId, String barcode);
    ProductBarcode updateProductBarcodeById(Integer productId, String barcode, ProductBarcode productBarcode);
    void deleteProductBarcodeById(Integer productId, String barcode);
    List<ProductBarcode> getProductBarcodesByProductId(Integer productId);
    List<ProductBarcode> getProductBarcodesByBarcode(String barcode);
    List<ProductBarcode> getProductBarcodesByStatus(Integer status);
}
