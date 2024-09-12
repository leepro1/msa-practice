package com.msa.catalog_service.dto.response;

import com.msa.catalog_service.entity.Catalog;
import java.util.Date;
import lombok.Builder;

@Builder
public record CatalogResponse(

    String productId,
    String productName,
    Integer unitPrice,
    Integer stock,
    Date createdAt

) {

    public static CatalogResponse of(Catalog data) {
        return CatalogResponse.builder()
            .productId(data.getProductId())
            .productName(data.getProductName())
            .unitPrice(data.getUnitPrice())
            .stock(data.getStock())
            .createdAt(data.getCreatedAt())
            .build();

    }
}
