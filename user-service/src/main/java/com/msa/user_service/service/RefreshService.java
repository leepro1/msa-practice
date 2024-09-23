package com.msa.user_service.service;

public interface RefreshService {

    void saveRefreshToken(String token, String userId);

    String getUserIdFromToken(String token);

    void deleteRefreshToken(String token);

    boolean isTokenExists(String token);
}
