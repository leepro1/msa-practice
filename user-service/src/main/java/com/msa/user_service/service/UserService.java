package com.msa.user_service.service;

import com.msa.user_service.data.dto.request.UserCreateRequest;
import com.msa.user_service.data.dto.response.UserCreateResponse;

public interface UserService {

    UserCreateResponse createUser(UserCreateRequest userCreateRequest);

}
