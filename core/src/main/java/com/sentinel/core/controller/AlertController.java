package com.sentinel.core.controller;

import com.sentinel.core.dto.AlertDTO;
import com.sentinel.core.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @GetMapping("/open")
    public List<AlertDTO> getOpenAlerts() {
        return alertService.getOpenAlerts();
    }

    @PostMapping
    public AlertDTO createAlert(@RequestParam Long assetId,
                                @RequestParam String severity,
                                @RequestParam String message) {
        return alertService.createAlert(assetId, severity, message);
    }

    @PutMapping("/{id}/resolve")
    public AlertDTO resolveAlert(@PathVariable Long id) {
        return alertService.resolveAlert(id);
    }
}