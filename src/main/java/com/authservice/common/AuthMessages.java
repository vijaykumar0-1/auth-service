package com.authservice.common;

public final class AuthMessages {
    public static final String LOGIN_SUCCESS = "Login successful";
    public static final String LOGOUT_SUCCESS = "Logout successful";
    public static final String INVALID_REFRESH_TOKEN = "Refresh token not found";
    public static final String REFRESH_TOKEN_EXPIRED = "Refresh token expired. Please login again.";

    private AuthMessages() {}
}
