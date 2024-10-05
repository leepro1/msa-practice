package com.msa.user_service.constant;

public final class JWT_SET {

    public static final Long ACCESS_TOKEN_EXPIRATION = (long) (1000 * 60 * 60 * 4); // 엑세스 토큰 유효시간 : 240분
    public static final Long REFRESH_TOKEN_EXPIRATION = (long) (1000 * 60 * 60 * 24); // 리프레시 토큰 유효시간 : 24시간

    private JWT_SET() {
    }
}