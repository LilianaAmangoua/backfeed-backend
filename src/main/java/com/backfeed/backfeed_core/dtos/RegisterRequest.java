package com.backfeed.backfeed_core.dtos;

import com.backfeed.backfeed_core.entities.User;

public class RegisterRequest {
    private User user;
    private String token;


    public RegisterRequest() {
    }

    public RegisterRequest(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
