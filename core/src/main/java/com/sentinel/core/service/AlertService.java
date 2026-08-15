package com.sentinel.core.service;

import com.sentinel.core.dto.AlertDTO;
import com.sentinel.core.entity.Alert;
import com.sentinel.core.entity.Asset;
import com.sentinel.core.repository.AlertRepository;
import com.sentinel.core.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private AssetRepository assetRepository;

    public AlertDTO createAlert(Long assetId, String severity, String message) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found: " + assetId));

        Alert alert = new Alert();
        alert.setAsset(asset);
        alert.setSeverity(severity);
        alert.setMessage(message);
        alert.setStatus("OPEN");
        alert.setCreatedAt(LocalDateTime.now());

        return toDTO(alertRepository.save(alert));
    }

    public AlertDTO resolveAlert(Long alertId) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found: " + alertId));

        alert.setStatus("RESOLVED");
        alert.setResolvedAt(LocalDateTime.now());

        return toDTO(alertRepository.save(alert));
    }

    public List<AlertDTO> getOpenAlerts() {
        return alertRepository.findByStatus("OPEN")
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private AlertDTO toDTO(Alert alert) {
        return new AlertDTO(
                alert.getId(),
                alert.getAsset().getId(),
                alert.getAsset().getAssetName(),
                alert.getSeverity(),
                alert.getMessage(),
                alert.getStatus(),
                alert.getCreatedAt(),
                alert.getResolvedAt()
        );
    }
}