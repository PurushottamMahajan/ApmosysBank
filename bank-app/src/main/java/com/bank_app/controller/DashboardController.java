package com.bank_app.controller;

import com.bank_app.dto.DashboardResponseDTO;
import com.bank_app.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/{accountNumber}")
    public DashboardResponseDTO getDashboard(
            @PathVariable String accountNumber) {

        return dashboardService.getDashboard(accountNumber);
    }
}
