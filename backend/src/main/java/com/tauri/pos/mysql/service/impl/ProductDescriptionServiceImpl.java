package com.tauri.pos.mysql.service.impl;

import com.tauri.pos.mysql.mapper.ProductDescriptionMapper;
import com.tauri.pos.mysql.model.ProductDescription;
import com.tauri.pos.mysql.persistance.dao.ProductDescriptionDao;
import com.tauri.pos.mysql.persistance.eo.ProductDescriptionEntity;
import com.tauri.pos.mysql.service.ProductDescriptionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@ConditionalOnProperty(name = "spring.datasource.mysql.jdbc-url")
public class ProductDescriptionServiceImpl implements ProductDescriptionService {
    private final ProductDescriptionDao productDescriptionDao;

    public ProductDescriptionServiceImpl(ProductDescriptionDao productDescriptionDao) {
        this.productDescriptionDao = productDescriptionDao;
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public ProductDescription createProductDescription(ProductDescription productDescription) {
        ProductDescriptionEntity entity = ProductDescriptionMapper.INSTANCE.productDescriptionToProductDescriptionEntity(productDescription);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return ProductDescriptionMapper.INSTANCE.productDescriptionEntityToProductDescription(
                productDescriptionDao.save(entity)
        );
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductDescription> getAllProductDescriptions() {
        return productDescriptionDao.findAll().stream()
                .map(ProductDescriptionMapper.INSTANCE::productDescriptionEntityToProductDescription)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ProductDescription getProductDescriptionById(Integer productId, Integer siteId, Integer languageId) {
        return productDescriptionDao.findById(new com.tauri.pos.mysql.persistance.eo.ProductDescriptionId(productId, siteId, languageId))
                .map(ProductDescriptionMapper.INSTANCE::productDescriptionEntityToProductDescription)
                .orElse(null);
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public ProductDescription updateProductDescriptionById(Integer productId, Integer siteId, Integer languageId, ProductDescription productDescription) {
        return productDescriptionDao.findById(new com.tauri.pos.mysql.persistance.eo.ProductDescriptionId(productId, siteId, languageId))
                .map(entity -> {
                    ProductDescriptionEntity updatedEntity = ProductDescriptionMapper.INSTANCE.productDescriptionToProductDescriptionEntity(productDescription);
                    updatedEntity.setProductId(productId);
                    updatedEntity.setSiteId(siteId);
                    updatedEntity.setLanguageId(languageId);
                    updatedEntity.setUpdatedAt(LocalDateTime.now());
                    return ProductDescriptionMapper.INSTANCE.productDescriptionEntityToProductDescription(productDescriptionDao.save(updatedEntity));
                })
                .orElse(null);
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public void deleteProductDescriptionById(Integer productId, Integer siteId, Integer languageId) {
        productDescriptionDao.findById(new com.tauri.pos.mysql.persistance.eo.ProductDescriptionId(productId, siteId, languageId))
                .map(entity -> {
                    productDescriptionDao.deleteById(new com.tauri.pos.mysql.persistance.eo.ProductDescriptionId(productId, siteId, languageId));
                    return true;
                });
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductDescription> getProductDescriptionsByProductId(Integer productId) {
        return productDescriptionDao.findByProductId(productId).stream()
                .map(ProductDescriptionMapper.INSTANCE::productDescriptionEntityToProductDescription)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductDescription> getProductDescriptionsBySiteId(Integer siteId) {
        return productDescriptionDao.findBySiteId(siteId).stream()
                .map(ProductDescriptionMapper.INSTANCE::productDescriptionEntityToProductDescription)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductDescription> getProductDescriptionsByLanguageId(Integer languageId) {
        return productDescriptionDao.findByLanguageId(languageId).stream()
                .map(ProductDescriptionMapper.INSTANCE::productDescriptionEntityToProductDescription)
                .toList();
    }
}
