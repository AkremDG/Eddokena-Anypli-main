package com.digid.eddokenaCM.Models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("expiresIn")
    private Long expiresIn;

    @SerializedName("user")
    private LoginResponseUser user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public LoginResponseUser getUser() {
        return user;
    }

    public void setUser(LoginResponseUser user) {
        this.user = user;
    }
}
