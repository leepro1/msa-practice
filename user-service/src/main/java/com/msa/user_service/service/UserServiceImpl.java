package com.msa.user_service.service;

import com.msa.user_service.dto.request.UserCreateRequest;
import com.msa.user_service.dto.response.OrderResponse;
import com.msa.user_service.dto.response.UserCreateResponse;
import com.msa.user_service.dto.response.UserResponse;
import com.msa.user_service.entity.User;
import com.msa.user_service.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserCreateResponse createUser(UserCreateRequest request) {

        User user = User.builder()
            .userId(UUID.randomUUID().toString())
            .name(request.name())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .build();

        User savedUser = userRepository.save(user);
        return UserCreateResponse.of(savedUser);

    }

    @Override
    public UserResponse getUserByUserId(String userId) {

        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderResponse> orders = new ArrayList<>();

        return UserResponse.of(user, orders);
    }

    @Override
    public List<UserResponse> getUserByAll() {

        List<User> data = userRepository.findAll();
        if (data.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        List<OrderResponse> orders = new ArrayList<>();

        return data.stream()
            .map(i -> UserResponse.of(i, orders))
            .toList();

    }


}
