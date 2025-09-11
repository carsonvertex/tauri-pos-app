package com.tauri.pos.mysql.service.impl;

import com.tauri.pos.mysql.mapper.ProductMapper;
import com.tauri.pos.mysql.model.Product;
import com.tauri.pos.mysql.persistance.dao.ProductDao;
import com.tauri.pos.mysql.persistance.eo.ProductEntity;
import com.tauri.pos.mysql.service.ProductService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@ConditionalOnProperty(name = "spring.datasource.mysql.jdbc-url")
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public Product createProduct(Product product) {
        ProductEntity entity = ProductMapper.INSTANCE.productToProductEntity(product);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return ProductMapper.INSTANCE.productEntityToProduct(
                productDao.save(entity)
        );
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> getAllProducts() {
        return productDao.findAll().stream()
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Product getProductById(Long productId) {
        return productDao.findById(productId)
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .orElse(null);
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public Product updateProductById(Long productId, Product product) {
        return productDao.findById(productId)
                .map(entity -> {
                    ProductEntity updatedEntity = ProductMapper.INSTANCE.productToProductEntity(product);
                    updatedEntity.setProductId(productId);
                    updatedEntity.setUpdatedAt(LocalDateTime.now());
                    return ProductMapper.INSTANCE.productEntityToProduct(productDao.save(updatedEntity));
                })
                .orElse(null);
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public void deleteProductById(Long productId) {
        productDao.findById(productId)
                .map(entity -> {
                    productDao.deleteById(productId);
                    return true;
                });
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> getProductsByStatus(Integer status) {
        return productDao.findByStatus(status).stream()
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> getProductsByBrandId(Integer brandId) {
        return productDao.findByBrandId(brandId).stream()
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> getProductsByEbayId(Integer ebayId) {
        return productDao.findByEbayId(ebayId).stream()
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> getProductsByModelNumber(String modelNumber) {
        return productDao.findByModelNumber(modelNumber).stream()
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> getProductsBySku(String sku) {
        return productDao.findBySku(sku).stream()
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> getProductsByBarcode(String barcode) {
        return productDao.findByBarcode(barcode).stream()
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> getProductsByWeightClassId(Integer weightClassId) {
        return productDao.findByWeightClassId(weightClassId).stream()
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> getProductsByCreatedBy(Integer createdBy) {
        return productDao.findByCreatedBy(createdBy).stream()
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> getProductsByUpdatedBy(Integer updatedBy) {
        return productDao.findByUpdatedBy(updatedBy).stream()
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> getProductsByStatusAndBrandId(Integer status, Integer brandId) {
        return productDao.findByStatusAndBrandId(status, brandId).stream()
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> searchProductsBySkuOrModelNumber(String searchTerm) {
        return productDao.findBySkuOrModelNumberContaining(searchTerm, searchTerm).stream()
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> getAvailableProductsByDate(LocalDate date) {
        return productDao.findAvailableProductsByDate(date).stream()
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsBySku(String sku) {
        return productDao.existsBySku(sku);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsByBarcode(String barcode) {
        return productDao.existsByBarcode(barcode);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> getProductsBySkus(List<String> skus) {
        return productDao.findBySkuIn(skus).stream()
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> getProductsByBarcodes(List<String> barcodes) {
        return productDao.findByBarcodeIn(barcodes).stream()
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public Product updateProductStatus(Long productId, Integer status) {
        return productDao.findById(productId)
                .map(entity -> {
                    entity.setStatus(status);
                    entity.setUpdatedAt(LocalDateTime.now());
                    return ProductMapper.INSTANCE.productEntityToProduct(productDao.save(entity));
                })
                .orElse(null);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Product> getProductsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return productDao.findAll().stream()
                .filter(entity -> {
                    LocalDateTime createdAt = entity.getCreatedAt();
                    return createdAt != null && 
                           createdAt.isAfter(startDate) && 
                           createdAt.isBefore(endDate);
                })
                .map(ProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }
}
