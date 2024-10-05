package com.msa.user_service.client;

import com.msa.user_service.dto.response.OrderResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @GetMapping("/order-service/{userId}/orders-asd")
    List<OrderResponse> getOrders(@PathVariable("userId") String userId);

}
