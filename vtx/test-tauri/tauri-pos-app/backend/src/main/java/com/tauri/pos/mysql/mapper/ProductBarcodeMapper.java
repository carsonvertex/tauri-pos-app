package com.tauri.pos.mysql.mapper;

import com.tauri.pos.mysql.model.ProductBarcode;
import com.tauri.pos.mysql.persistance.eo.ProductBarcodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductBarcodeMapper {
    ProductBarcodeMapper INSTANCE = Mappers.getMapper(ProductBarcodeMapper.class);

    ProductBarcodeEntity productBarcodeToProductBarcodeEntity(ProductBarcode productBarcode);

    ProductBarcode productBarcodeEntityToProductBarcode(ProductBarcodeEntity productBarcodeEntity);
}
