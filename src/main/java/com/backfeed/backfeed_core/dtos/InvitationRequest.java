package com.backfeed.backfeed_core.dtos;

public class InvitationRequest {
    private String email;
    private String firstName;

    public InvitationRequest() {
    }

    public InvitationRequest(String email, String firstName) {
        this.email = email;
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
