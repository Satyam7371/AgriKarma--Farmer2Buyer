package org.example.agrikarmabackend.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.example.agrikarmabackend.dashboard.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/farmer")
    @PreAuthorize("hasRole('FARMER')")
    public ResponseEntity<?> getFarmerDashboard(Authentication authentication) {

        return ResponseEntity.ok(
                dashboardService.getFarmerDashboard(authentication.getName())
        );
    }
}
