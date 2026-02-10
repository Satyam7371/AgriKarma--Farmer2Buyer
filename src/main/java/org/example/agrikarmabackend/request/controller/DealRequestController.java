package org.example.agrikarmabackend.request.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.agrikarmabackend.request.dto.CreateDealRequest;
import org.example.agrikarmabackend.request.dto.DealActionRequest;
import org.example.agrikarmabackend.request.service.DealRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class DealRequestController {

    private final DealRequestService dealRequestService;

    // BUYER creates request
    @PostMapping
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<?> createRequest(
            @Valid @RequestBody CreateDealRequest request,
            Authentication authentication
    ) {
        dealRequestService.createRequest(request, authentication.getName());
        return ResponseEntity.ok("Request submitted successfully");
    }

    // FARMER accepts or rejects request
    @PostMapping("/{id}/action")
    @PreAuthorize("hasRole('FARMER')")
    public ResponseEntity<?> actOnRequest(
            @PathVariable Long id,
            @Valid @RequestBody DealActionRequest request,
            Authentication authentication
    ) {
        dealRequestService.actOnRequest(id, authentication.getName(), request.action());
        return ResponseEntity.ok("Request updated successfully");
    }





    // this is to view the requests
    // BUYER dashboard - view own requests
    @GetMapping("/my")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<?> getMyRequests(Authentication authentication) {

        return ResponseEntity.ok(
                dealRequestService.getRequestsByBuyer(authentication.getName())
        );
    }

    // FARMER dashboard - view incoming requests
    @GetMapping("/incoming")
    @PreAuthorize("hasRole('FARMER')")
    public ResponseEntity<?> getIncomingRequests(Authentication authentication) {

        return ResponseEntity.ok(
                dealRequestService.getRequestsForFarmer(authentication.getName())
        );
    }

}

