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
@ConditionalOnProperty(name = "spring.datasource.mysql.enabled", havingValue = "true", matchIfMissing = false)
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
        return productBarcodeDao.findById(new com.tauri.pos.mysql.persistance.eo.ProductBarcodeId(productId, barcode))
                .map(ProductBarcodeMapper.INSTANCE::productBarcodeEntityToProductBarcode)
                .orElse(null);
    }

    @Override
    @Transactional(transactionManager = "mysqlTransactionManager")
    public ProductBarcode updateProductBarcodeById(Integer productId, String barcode, ProductBarcode productBarcode) {
        return productBarcodeDao.findById(new com.tauri.pos.mysql.persistance.eo.ProductBarcodeId(productId, barcode))
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
    public void deleteProductBarcodeById(Integer productId, String barcode) {
        productBarcodeDao.findById(new com.tauri.pos.mysql.persistance.eo.ProductBarcodeId(productId, barcode))
                .map(entity -> {
                    productBarcodeDao.deleteById(new com.tauri.pos.mysql.persistance.eo.ProductBarcodeId(productId, barcode));
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
}
