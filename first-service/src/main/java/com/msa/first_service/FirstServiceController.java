package com.msa.first_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-service")
@Slf4j
public class FirstServiceController {

    @GetMapping("/welcome")
    public String welcome(@RequestHeader("first-request") String header) {
        log.info(header);
        return "Welcome first service";
    }

    @GetMapping("/check")
    public String check() {
        return "Check first service";
    }

}
