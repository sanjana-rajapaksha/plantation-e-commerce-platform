package com.harithasewana.identity.dto;

public class LoginResponse {

    private String accessToken;  //The generated JWT
    private String tokenType;  //Indicates that we use Bearer token authentication
    private long expiresIn;  //Token lifetime in seconds

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
