package com.msa.order_service.dto.response;

import com.msa.order_service.entity.Order;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record OrderResponse(

    String productId,
    Integer quantity,
    Integer unitPrice,
    Integer totalPrice,

    String orderId,
    String userId

) {

    public static OrderResponse of(Order data) {
        return OrderResponse.builder()
            .productId(data.getProductId())
            .quantity(data.getQuantity())
            .unitPrice(data.getUnitPrice())
            .totalPrice(data.getTotalPrice())
            .orderId(data.getOrderId())
            .userId(data.getUserId())
            .build();

    }
}
