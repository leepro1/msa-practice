package com.msa.user_service.dto.response;

import com.msa.user_service.dto.CustomUserDetails;
import lombok.Builder;

@Builder
public record LoginUserResponse(

    Long id,
    String username
) {

    public static LoginUserResponse of(CustomUserDetails data) {
        return LoginUserResponse.builder()
            .id(data.getId())
            .username(data.getUsername())
            .build();
    }
}
