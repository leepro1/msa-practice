package com.msa.apigateway_service.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.apigateway_service.util.JWTUtil;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends
    AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public AuthorizationHeaderFilter(JWTUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
        this.objectMapper = new ObjectMapper();
    }

    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (!request.getHeaders().containsKey("access")) {
                return this.onError(response, "access이 없습니다.", HttpStatus.UNAUTHORIZED);
            }

            String accessToken = request.getHeaders().get("access").get(0);

            try {
                jwtUtil.isExpired(accessToken);
            } catch (Exception e) {
                return this.onError(response, "access 토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED);
            }

            String category = jwtUtil.getCategory(accessToken);

            if (!category.equals("access")) {
                return this.onError(response, "Invalid access token", HttpStatus.UNAUTHORIZED);
            }

            String userId = jwtUtil.getUserId(accessToken);
            String username = jwtUtil.getUsername(accessToken);

            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("X-User-Id", userId)
                .header("X-Username", username)
                .build();
            exchange = exchange.mutate().request(modifiedRequest).build();

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerHttpResponse response, String errorMessage,
        HttpStatus httpStatus) {
        response.setStatusCode(httpStatus);
        response.getHeaders().add("Content-Type", "application/json");

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", errorMessage);

        return Mono.defer(() -> {
            try {
                byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
                return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
            } catch (JsonProcessingException e) {
                byte[] fallbackMessage = "{\"error\": \"Internal server error\"}".getBytes(
                    StandardCharsets.UTF_8);
                return response.writeWith(
                    Mono.just(response.bufferFactory().wrap(fallbackMessage)));
            }
        });
    }
}
