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

import java.time.LocalDateTime;
import java.util.List;

@Service
@ConditionalOnProperty(name = "spring.datasource.mysql.enabled", havingValue = "true", matchIfMissing = false)
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
}
