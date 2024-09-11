package com.msa.user_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(

    @NotNull(message = "이메일은 필수 입력 사항입니다.")
    @Email
    String email,

    @NotNull(message = "이름은 필수 입력 사항입니다.")

    String name,

    @NotNull(message = "비밀번호는 필수 입력 사항입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8글자입니다.")
    String password

) {

}