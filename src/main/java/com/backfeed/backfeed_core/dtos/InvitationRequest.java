package com.backfeed.backfeed_core.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class InvitationRequest {
    @Email(message = "Email must be valid.")
    private String email;
    @NotNull(message = "Firstname cannot be null.")
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
