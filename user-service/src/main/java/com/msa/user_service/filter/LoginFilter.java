package com.msa.user_service.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.user_service.dto.CustomUserDetails;
import com.msa.user_service.dto.request.LoginRequest;
import com.msa.user_service.dto.response.CommonResponse;
import com.msa.user_service.dto.response.LoginUserResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = parseLoginRequest(request);
            return authenticateUser(loginRequest);
        } catch (IOException | AuthenticationException e) {
            try {
                handleException(response, e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        LoginUserResponse loginUserResponse = LoginUserResponse.of(userDetails);
        CommonResponse<LoginUserResponse> responseBody = CommonResponse.ok("Authentication successful", loginUserResponse);

        sendResponse(response, HttpStatus.OK, responseBody);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        CommonResponse<String> errorResponse = CommonResponse.unauthorized("Authentication failed");
        sendResponse(response, HttpStatus.UNAUTHORIZED, errorResponse);
    }

    private LoginRequest parseLoginRequest(HttpServletRequest request) throws IOException {
        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        return objectMapper.readValue(messageBody, LoginRequest.class);
    }

    private Authentication authenticateUser(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        return authenticationManager.authenticate(authToken);
    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        CommonResponse<String> errorResponse = CommonResponse.badRequest(
            "Authentication failed" + ": " + e.getMessage());
        sendResponse(response, HttpStatus.BAD_REQUEST, errorResponse);
    }

    private <T> void sendResponse(HttpServletResponse response, HttpStatus status, CommonResponse<T> body) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}