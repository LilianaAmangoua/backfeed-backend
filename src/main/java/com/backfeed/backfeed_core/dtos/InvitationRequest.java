package com.backfeed.backfeed_core.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class InvitationRequest {
    @Email(message = "Email must be valid.")
    @NotBlank(message = "Email cannot be blank.")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email must contain a valid domain."
    )
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
