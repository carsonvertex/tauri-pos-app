package com.tauri.pos.mysql.mapper;

import com.tauri.pos.mysql.model.ProductDescription;
import com.tauri.pos.mysql.persistance.eo.ProductDescriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductDescriptionMapper {
    ProductDescriptionMapper INSTANCE = Mappers.getMapper(ProductDescriptionMapper.class);

    ProductDescriptionEntity productDescriptionToProductDescriptionEntity(ProductDescription productDescription);

    ProductDescription productDescriptionEntityToProductDescription(ProductDescriptionEntity productDescriptionEntity);
}
