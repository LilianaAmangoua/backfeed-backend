package com.backfeed.backfeed_core.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequest(

        @Email
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Please provide a valid email address."
        )
        String email,

        @NotBlank(message = "Password cannot be blank.")
        String password
) {}
