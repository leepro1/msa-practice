package com.msa.user_service.service;

import com.msa.user_service.dto.request.UserCreateRequest;
import com.msa.user_service.dto.response.UserCreateResponse;
import com.msa.user_service.dto.response.UserResponse;
import java.util.List;

public interface UserService {

    UserCreateResponse createUser(UserCreateRequest userCreateRequest);

    UserResponse getUserByUserId(String userId);

    List<UserResponse> getUserByAll();

}
