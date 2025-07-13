package ru.itis.vhsroni.config.security;

public class SecurityEndpoints {

    public static final String[] PUBLIC_ENDPOINTS = {
            "/api/v1/auth/email_check",
            "/api/v1/auth/refresh",
            "/api/v1/dentists/**",
            "/api/v1/timetable/**"
    };

    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/api/v1/comments/**",
            "/api/v1/photos/avatars/**",
            "/api/v1/procedures/**"
    };

    public static final String[] AUTHENTICATED_ENDPOINTS = {
            "/api/v1/admin/**",
            "/api/v1/appointments/**",
            "/api/v1/auth/logout",
            "/api/v1/profile/**"
    };

    public static final String[] AUTHENTICATED_POST_ENDPOINTS = {
            "/api/v1/comments/**",
            "/api/v1/photos/avatars/**",
            "/api/v1/procedures"
    };

    public static final String[] AUTHENTICATED_DELETE_ENDPOINTS = {
            "/api/v1/comments/**",
            "/api/v1/procedures/**"
    };

    public static final String[] AUTHENTICATED_PATCH_ENDPOINTS = {
            "/api/v1/procedures/**"
    };

    public static final String[] ALL_OPTIONS = {
            "/**"
    };

    public static final String[] SWAGGER_ENDPOINTS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html"
    };

    public static final String[] ANONYMOUS_ENDPOINTS = {
            "/api/v1/auth/register/**",
            "/api/v1/auth/verify/**",
            "/api/v1/auth/login"
    };

    public static final String OAUTH2_AUTHORIZATION_BASE_URI = "/oauth2/authorization";

    public static final String OAUTH2_REDIRECTION_BASE_URI = "/login/oauth2/code/*";

    public static final String OAUTH_SUCCESS_REDIRECT_URI = "/oauth/success";

    public static final String CSRF_PROTECTED_ENDPOINT = "/api/v1/auth/refresh";
}
