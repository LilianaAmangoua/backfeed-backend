package com.backfeed.backfeed_core.controllers;

import com.backfeed.backfeed_core.dtos.InvitationRequest;
import com.backfeed.backfeed_core.exceptions.responses.SuccessResponse;
import com.backfeed.backfeed_core.services.InvitationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/invitation")
public class InvitationController {
    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping("/")
    public ResponseEntity<SuccessResponse<Void>> register(@Valid @RequestBody InvitationRequest request){
        invitationService.sendInvitation(request);
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.CREATED, "Invitation successfully sent."));
    }
}
