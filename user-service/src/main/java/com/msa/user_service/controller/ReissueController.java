package com.msa.user_service.controller;


import static com.msa.user_service.constant.JWT_SET.*;

import com.msa.user_service.exception.InvalidRefreshTokenException;
import com.msa.user_service.exception.RefreshTokenExpiredException;
import com.msa.user_service.exception.RefreshTokenNullException;
import com.msa.user_service.service.RefreshService;
import com.msa.user_service.util.CookieUtil;
import com.msa.user_service.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueController {

    private final JWTUtil jwtUtil;
    private final RefreshService refreshService;

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        String refresh = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    refresh = cookie.getValue();
                    break;
                }
            }
        }

        if (refresh == null) {
            throw new RefreshTokenNullException("refresh token이 존재하지 않습니다.");
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            throw new RefreshTokenExpiredException("refresh token가 만료되었습니다.");
        }

        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            throw new InvalidRefreshTokenException("refresh token이 아닙니다.");
        }

        if (!refreshService.isTokenExists(refresh)) {
            throw new InvalidRefreshTokenException("refresh token이 DB에 존재하지 않습니다.");
        }

        String userId = jwtUtil.getUserId(refresh);
        String username = jwtUtil.getUsername(refresh);

        String newAccess = jwtUtil.createJwt("access", userId, username, ACCESS_TOKEN_EXPIRATION);
        String newRefresh = jwtUtil.createJwt("refresh", userId, username,
            REFRESH_TOKEN_EXPIRATION);

        refreshService.deleteRefreshToken(refresh);
        refreshService.saveRefreshToken(newRefresh, userId);

        response.setHeader("access", newAccess);

        Cookie cookie = CookieUtil.createCookie("refresh", newRefresh);
        CookieUtil.addSameSiteCookieAttribute(response, cookie);

        return ResponseEntity.ok().build();
    }
}
