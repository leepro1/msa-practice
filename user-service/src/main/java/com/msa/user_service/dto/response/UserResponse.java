package com.msa.user_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.msa.user_service.entity.User;
import java.util.List;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(

    String email,
    String name,
    String userId,

    List<OrderResponse> orders

) {

    public static UserResponse of(User user, List<OrderResponse> orders) {
        return UserResponse.builder()
            .email(user.getEmail())
            .name(user.getName())
            .userId(user.getUserId())
            .orders(orders)
            .build();
    }
}
