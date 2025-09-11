package com.tauri.pos.mysql.mapper;

import com.tauri.pos.mysql.model.Product;
import com.tauri.pos.mysql.persistance.eo.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductEntity productToProductEntity(Product product);

    Product productEntityToProduct(ProductEntity productEntity);
}
