package com.msa.user_service.dto.response;

import com.msa.user_service.entity.User;
import java.util.Date;
import lombok.Builder;

@Builder
public record UserCreateResponse(

    Long id,
    String userId,
    String name,
    String email,
    String password,
    Date createdAt

) {

    public static UserCreateResponse of(User data) {
        return UserCreateResponse.builder()
            .id(data.getId())
            .userId(data.getUserId())
            .name(data.getName())
            .email(data.getEmail())
            .password(data.getPassword())
            .createdAt(data.getCreatedAt())
            .build();
    }
}