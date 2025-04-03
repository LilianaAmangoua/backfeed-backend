package com.backfeed.backfeed_core.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtToken {
    private String token;

    public JwtToken() {
    }

    public JwtToken(String token) {
        this.token = token;
    }
}
