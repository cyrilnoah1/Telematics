package com.example.rapid.telematics.server;

/**
 * Server constants for all the required keys in requests and responses.
 */
public interface ServerConstants {
    String AUTH_TOKEN_KEY = "authToken";
    String REFRESH_TOKEN_KEY = "refreshToken";

    String ACCESS_TOKEN_KEY = "accessToken";
    String EXPRIRES_IN_KEY = "expiresIn";
    String SCOPE_KEY = "scope";
    String TOKEN_TYPE_KEY = "tokenType";
}
