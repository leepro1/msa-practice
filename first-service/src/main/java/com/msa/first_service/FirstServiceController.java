package com.msa.first_service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-service")
@RequiredArgsConstructor
@Slf4j
public class FirstServiceController {

    final Environment env;

    @GetMapping("/welcome")
    public String welcome(@RequestHeader("first-request") String header) {
        log.info(header);
        return "Welcome first service";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server port={}", request.getServerPort());

        return String.format("Check first service on PORT %s",
            env.getProperty("local.server.port"));
    }

}
