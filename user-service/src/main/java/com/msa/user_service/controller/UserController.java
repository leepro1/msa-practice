package com.msa.user_service.controller;

import com.msa.user_service.data.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final Greeting greeting;

    @GetMapping("health-check")
    public String status() {
        return "It's Working in User Service";
    }

    @GetMapping("welcome")
    public String welcome() {
        return greeting.getMessage();
    }

}
