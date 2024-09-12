package com.msa.order_service.dto.response;

import com.msa.order_service.entity.Order;
import java.time.LocalDate;
import java.util.Date;
import lombok.Builder;

@Builder
public record OrderResponse(

    String productId,
    Integer quantity,
    Integer unitPrice,
    Integer totalPrice,
    LocalDate createdAt,

    String orderId,
    String userId

) {

    public static OrderResponse of(Order data) {
        return OrderResponse.builder()
            .productId(data.getProductId())
            .quantity(data.getQuantity())
            .unitPrice(data.getUnitPrice())
            .totalPrice(data.getTotalPrice())
            .createdAt(data.getCreatedAt())
            .orderId(data.getOrderId())
            .userId(data.getUserId())
            .build();

    }
}
