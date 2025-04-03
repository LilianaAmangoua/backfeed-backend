package com.backfeed.backfeed_core.exceptions;

public class JwtValidationException extends RuntimeException {
  public JwtValidationException(String message) {
    super(message);
  }
}
