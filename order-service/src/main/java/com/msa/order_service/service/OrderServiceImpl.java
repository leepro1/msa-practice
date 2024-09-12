package com.msa.order_service.service;

import com.msa.order_service.dto.request.OrderRequest;
import com.msa.order_service.dto.response.OrderResponse;
import com.msa.order_service.entity.Order;
import com.msa.order_service.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderRequest request) {

        Order order = Order.builder()

            .orderId(UUID.randomUUID().toString())
            .userId(request.userId())
            .productId(request.productId())
            .unitPrice(request.unitPrice())
            .quantity(request.quantity())
            .totalPrice(request.unitPrice() * request.quantity())
            .build();

        Order savedOrder = orderRepository.save(order);
        return OrderResponse.of(savedOrder);
    }

    @Override
    public OrderResponse getOrdersByOrderId(String orderId) {
        Order order = orderRepository.findByOrderId(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        return OrderResponse.of(order);

    }

    @Override
    public List<OrderResponse> getAllOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId).stream()
            .map(OrderResponse::of)
            .toList();
    }

}
