package com.msa.user_service.constant;

public final class SECURITY_SET {

    public static final String[] PERMITALL_URL_PATTERNS = {

        "/h2-console/**",
        "/users/**",

        "/actuator/**"

    };

    public static final String[] NEED_LOGIN_URL_PATTERNS = {

    };

    public static final String[] NEED_ADMIN_ROLE_URL_PATTERNS = {

    };

    private SECURITY_SET() {
    }
}
