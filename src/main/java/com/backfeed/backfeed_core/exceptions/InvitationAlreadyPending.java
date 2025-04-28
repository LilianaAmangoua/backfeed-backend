package com.backfeed.backfeed_core.exceptions;

public class InvitationAlreadyPending extends RuntimeException {
    public InvitationAlreadyPending(String message) {
        super(message);
    }
}
