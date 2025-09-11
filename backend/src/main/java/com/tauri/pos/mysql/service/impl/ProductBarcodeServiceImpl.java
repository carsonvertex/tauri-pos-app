package com.tauri.pos.mysql.service.impl;

import com.tauri.pos.mysql.mapper.ProductBarcodeMapper;
import com.tauri.pos.mysql.model.ProductBarcode;
import com.tauri.pos.mysql.persistance.dao.ProductBarcodeDao;
import com.tauri.pos.mysql.persistance.eo.ProductBarcodeEntity;
import com.tauri.pos.mysql.service.ProductBarcodeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@ConditionalOnProperty(name = "spring.datasource.mysql.jdbc-url")
public class ProductBarcodeServiceImpl implements ProductBarcodeService {
    private final ProductBarcodeDao productBarcodeDao;

    public ProductBarcodeServiceImpl(ProductBarcodeDao productBarcodeDao) {
        this.productBarcodeDao = productBarcodeDao;
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public ProductBarcode createProductBarcode(ProductBarcode productBarcode) {
        ProductBarcodeEntity entity = ProductBarcodeMapper.INSTANCE.productBarcodeToProductBarcodeEntity(productBarcode);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return ProductBarcodeMapper.INSTANCE.productBarcodeEntityToProductBarcode(
                productBarcodeDao.save(entity)
        );
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductBarcode> getAllProductBarcodes() {
        return productBarcodeDao.findAll().stream()
                .map(ProductBarcodeMapper.INSTANCE::productBarcodeEntityToProductBarcode)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ProductBarcode getProductBarcodeById(Integer productId, String barcode) {
        return productBarcodeDao.findByProductIdAndBarcode(productId, barcode)
                .map(ProductBarcodeMapper.INSTANCE::productBarcodeEntityToProductBarcode)
                .orElse(null);
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public ProductBarcode updateProductBarcode(Integer productId, String barcode, ProductBarcode productBarcode) {
        return productBarcodeDao.findByProductIdAndBarcode(productId, barcode)
                .map(entity -> {
                    ProductBarcodeEntity updatedEntity = ProductBarcodeMapper.INSTANCE.productBarcodeToProductBarcodeEntity(productBarcode);
                    updatedEntity.setProductId(productId);
                    updatedEntity.setBarcode(barcode);
                    updatedEntity.setUpdatedAt(LocalDateTime.now());
                    return ProductBarcodeMapper.INSTANCE.productBarcodeEntityToProductBarcode(productBarcodeDao.save(updatedEntity));
                })
                .orElse(null);
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public void deleteProductBarcode(Integer productId, String barcode) {
        productBarcodeDao.findByProductIdAndBarcode(productId, barcode)
                .map(entity -> {
                    productBarcodeDao.deleteByProductIdAndBarcode(productId, barcode);
                    return true;
                });
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductBarcode> getProductBarcodesByProductId(Integer productId) {
        return productBarcodeDao.findByProductId(productId).stream()
                .map(ProductBarcodeMapper.INSTANCE::productBarcodeEntityToProductBarcode)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductBarcode> getProductBarcodesByBarcode(String barcode) {
        return productBarcodeDao.findByBarcode(barcode).stream()
                .map(ProductBarcodeMapper.INSTANCE::productBarcodeEntityToProductBarcode)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductBarcode> getProductBarcodesByStatus(Integer status) {
        return productBarcodeDao.findByStatus(status).stream()
                .map(ProductBarcodeMapper.INSTANCE::productBarcodeEntityToProductBarcode)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductBarcode> getProductBarcodesByCreatedBy(Integer createdBy) {
        return productBarcodeDao.findByCreatedBy(createdBy).stream()
                .map(ProductBarcodeMapper.INSTANCE::productBarcodeEntityToProductBarcode)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductBarcode> getProductBarcodesByUpdatedBy(Integer updatedBy) {
        return productBarcodeDao.findByUpdatedBy(updatedBy).stream()
                .map(ProductBarcodeMapper.INSTANCE::productBarcodeEntityToProductBarcode)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductBarcode> getProductBarcodesByProductIdAndStatus(Integer productId, Integer status) {
        return productBarcodeDao.findByProductIdAndStatus(productId, status).stream()
                .map(ProductBarcodeMapper.INSTANCE::productBarcodeEntityToProductBarcode)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductBarcode> searchProductBarcodesByBarcodeContaining(String barcode) {
        return productBarcodeDao.findByBarcodeContaining(barcode).stream()
                .map(ProductBarcodeMapper.INSTANCE::productBarcodeEntityToProductBarcode)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductBarcode> getProductBarcodesByStatusAndCreatedBy(Integer status, Integer createdBy) {
        return productBarcodeDao.findByStatusAndCreatedBy(status, createdBy).stream()
                .map(ProductBarcodeMapper.INSTANCE::productBarcodeEntityToProductBarcode)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsByProductIdAndBarcode(Integer productId, String barcode) {
        return productBarcodeDao.existsByProductIdAndBarcode(productId, barcode);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean existsByBarcode(String barcode) {
        return productBarcodeDao.existsByBarcode(barcode);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductBarcode> getProductBarcodesByProductIds(List<Integer> productIds) {
        return productBarcodeDao.findByProductIdIn(productIds).stream()
                .map(ProductBarcodeMapper.INSTANCE::productBarcodeEntityToProductBarcode)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductBarcode> getProductBarcodesByBarcodes(List<String> barcodes) {
        return productBarcodeDao.findByBarcodeIn(barcodes).stream()
                .map(ProductBarcodeMapper.INSTANCE::productBarcodeEntityToProductBarcode)
                .toList();
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public void deleteProductBarcodesByProductId(Integer productId) {
        productBarcodeDao.deleteByProductId(productId);
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public void deleteProductBarcodesByBarcode(String barcode) {
        productBarcodeDao.deleteByBarcode(barcode);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public long countProductBarcodesByProductId(Integer productId) {
        return productBarcodeDao.countByProductId(productId);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public long countProductBarcodesByBarcode(String barcode) {
        return productBarcodeDao.countByBarcode(barcode);
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public ProductBarcode updateProductBarcodeStatus(Integer productId, String barcode, Integer status) {
        return productBarcodeDao.findByProductIdAndBarcode(productId, barcode)
                .map(entity -> {
                    entity.setStatus(status);
                    entity.setUpdatedAt(LocalDateTime.now());
                    return ProductBarcodeMapper.INSTANCE.productBarcodeEntityToProductBarcode(productBarcodeDao.save(entity));
                })
                .orElse(null);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProductBarcode> getProductBarcodesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return productBarcodeDao.findAll().stream()
                .filter(entity -> {
                    LocalDateTime createdAt = entity.getCreatedAt();
                    return createdAt != null && 
                           createdAt.isAfter(startDate) && 
                           createdAt.isBefore(endDate);
                })
                .map(ProductBarcodeMapper.INSTANCE::productBarcodeEntityToProductBarcode)
                .toList();
    }
}
