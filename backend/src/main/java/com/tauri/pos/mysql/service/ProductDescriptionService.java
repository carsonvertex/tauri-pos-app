package com.tauri.pos.mysql.service;

import com.tauri.pos.mysql.model.ProductDescription;

import java.util.List;

public interface ProductDescriptionService {
    ProductDescription createProductDescription(ProductDescription productDescription);
    List<ProductDescription> getAllProductDescriptions();
    ProductDescription getProductDescriptionById(Integer productId, Integer siteId, Integer languageId);
    ProductDescription updateProductDescriptionById(Integer productId, Integer siteId, Integer languageId, ProductDescription productDescription);
    void deleteProductDescriptionById(Integer productId, Integer siteId, Integer languageId);
    List<ProductDescription> getProductDescriptionsByProductId(Integer productId);
    List<ProductDescription> getProductDescriptionsBySiteId(Integer siteId);
    List<ProductDescription> getProductDescriptionsByLanguageId(Integer languageId);
}
