package com.msa.order_service.service;


import com.msa.order_service.dto.request.OrderRequest;
import com.msa.order_service.dto.response.OrderResponse;
import java.util.List;

public interface OrderService {

    OrderResponse createOrder(String userId, OrderRequest request);

    OrderResponse getOrdersByOrderId(String orderId);

    List<OrderResponse> getAllOrdersByUserId(String userId);

}
