package com.msa.user_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

    @NotBlank(message = "email은 필수입니다.")
    @Size(min = 2, message = "email은 최소 2자 이상입니다.")
    @Email
    String email,

    @NotBlank(message = "password 필수입니다.")
    @Size(min = 8, message = "password는 최소 8자 이상입니다.")
    String password
) {

}
