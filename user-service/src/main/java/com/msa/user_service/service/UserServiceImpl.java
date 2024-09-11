package com.msa.user_service.service;

import com.msa.user_service.dto.request.UserCreateRequest;
import com.msa.user_service.dto.response.UserCreateResponse;
import com.msa.user_service.entity.User;
import com.msa.user_service.repository.UserRepository;
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


}
