package com.msa.user_service.service;

import com.msa.user_service.dto.request.UserCreateRequest;
import com.msa.user_service.dto.response.UserCreateResponse;

public interface UserService {

    UserCreateResponse createUser(UserCreateRequest userCreateRequest);

}
