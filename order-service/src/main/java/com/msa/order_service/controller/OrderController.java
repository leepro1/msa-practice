package com.msa.order_service.controller;

import com.msa.order_service.dto.request.OrderRequest;
import com.msa.order_service.dto.response.OrderResponse;
import com.msa.order_service.message.KafkaProducer;
import com.msa.order_service.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final Environment env;
    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in Order Service On PORT %s",
            env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.welcome");
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderResponse> createOrder(@PathVariable("userId") String userId,
        @RequestBody OrderRequest request) {

        OrderResponse data = orderService.createOrder(userId, request);

        kafkaProducer.send("example-catalog-topic", data);

        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderResponse>> getOrder(@PathVariable("userId") String userId) {

        List<OrderResponse> data = orderService.getAllOrdersByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

}
