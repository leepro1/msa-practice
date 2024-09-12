package com.msa.catalog_service.dto.request;

public record CatalogRequest(

    String productId,
    Integer quantity,
    Integer unitPrice,
    Integer totalPrice,

    String orderId,
    String userId

) {

}
