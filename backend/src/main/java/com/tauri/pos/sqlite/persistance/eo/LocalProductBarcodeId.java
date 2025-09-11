package com.tauri.pos.sqlite.persistance.eo;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalProductBarcodeId implements Serializable {

    private Integer productId;
    private String barcode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalProductBarcodeId that = (LocalProductBarcodeId) o;
        return Objects.equals(productId, that.productId) &&
               Objects.equals(barcode, that.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, barcode);
    }
}
