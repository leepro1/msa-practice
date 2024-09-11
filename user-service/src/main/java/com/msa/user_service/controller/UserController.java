package com.msa.user_service.controller;

import com.msa.user_service.dto.request.UserCreateRequest;
import com.msa.user_service.dto.response.UserCreateResponse;
import com.msa.user_service.dto.response.UserResponse;
import com.msa.user_service.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserController {

    private final Environment env;
    private final UserService userService;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in User Service On PORT %s",
            env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.welcome");
    }

    @PostMapping("/users")
    public ResponseEntity<UserCreateResponse> createUser(@RequestBody UserCreateRequest request) {

        UserCreateResponse data = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers() {

        List<UserResponse> data = userService.getUserByAll();

        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") String userId) {

        UserResponse data = userService.getUserByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

}
