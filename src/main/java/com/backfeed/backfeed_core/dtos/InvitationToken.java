package com.backfeed.backfeed_core.dtos;

import java.util.Date;

public class InvitationToken {
    private String token;
    private Date expirationDate;


    public InvitationToken() {
    }

    public InvitationToken(String token, Date expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
