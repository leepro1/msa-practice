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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final Environment env;

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

        String orderURL = String.format(env.getProperty("order-service.url"),
            userId); // 이것도 구성파일로 별도로 두자

        ResponseEntity<List<OrderResponse>> orderResponseList = restTemplate.exchange(orderURL,
            HttpMethod.GET, null,
            new ParameterizedTypeReference<List<OrderResponse>>() {
            });

        List<OrderResponse> orders = orderResponseList.getBody();

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
