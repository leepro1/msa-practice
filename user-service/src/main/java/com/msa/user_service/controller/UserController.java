package com.msa.user_service.controller;

import com.msa.user_service.data.Greeting;
import com.msa.user_service.data.dto.request.UserCreateRequest;
import com.msa.user_service.data.dto.response.UserCreateResponse;
import com.msa.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final Greeting greeting;
    private final UserService userService;

    @GetMapping("health-check")
    public String status() {
        return "It's Working in User Service";
    }

    @GetMapping("welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<UserCreateResponse> createUser(@RequestBody UserCreateRequest request) {

        UserCreateResponse data = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

}
