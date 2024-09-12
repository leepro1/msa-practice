package com.msa.order_service.dto.request;

public record OrderRequest(

    String productId,
    Integer quantity,
    Integer unitPrice
    
) {

}
