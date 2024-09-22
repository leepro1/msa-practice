package com.msa.user_service.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.user_service.dto.CustomUserDetails;
import com.msa.user_service.dto.request.LoginRequest;
import com.msa.user_service.dto.response.CommonResponse;
import com.msa.user_service.dto.response.LoginUserResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        LoginRequest loginRequest;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            loginRequest = objectMapper.readValue(messageBody, LoginRequest.class);
        } catch (IOException e) {
            try {
                handleException(response, e, "Failed to parse login request");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return null;
        }

        try {
            String email = loginRequest.email();
            String password = loginRequest.password();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                email, password, null);

            return authenticationManager.authenticate(authToken);
        } catch (AuthenticationException e) {
            try {
                handleException(response, e, "Authentication failed");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authentication)
        throws IOException {

        try {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long id = userDetails.getId();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            LoginUserResponse loginUserResponse = LoginUserResponse.of(userDetails);
            CommonResponse<LoginUserResponse> responseBody = CommonResponse.ok(
                "Authentication successful", loginUserResponse);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
        } catch (Exception e) {
            handleException(response, e, "An error occurred during authentication");
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed) throws IOException {
        CommonResponse<String> errorResponse = CommonResponse.unauthorized("Authentication failed");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }

    private void handleException(HttpServletResponse response, Exception e, String message)
        throws IOException {
        CommonResponse<String> errorResponse = CommonResponse.badRequest(
            message + ": " + e.getMessage());

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}