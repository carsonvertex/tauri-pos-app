package com.tauri.pos.mysql.persistance.eo;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDescriptionId implements Serializable {
    private Integer productId;
    private Integer siteId;
    private Integer languageId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDescriptionId that = (ProductDescriptionId) o;
        return Objects.equals(productId, that.productId) && 
               Objects.equals(siteId, that.siteId) && 
               Objects.equals(languageId, that.languageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, siteId, languageId);
    }
}
