package com.sentinel.core.service;

import com.sentinel.core.entity.Asset;
import com.sentinel.core.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthMonitorService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AlertService alertService;

    private static final double CPU_CRITICAL_THRESHOLD = 90.0;
    private static final double MEMORY_WARNING_THRESHOLD = 80.0;

    @Scheduled(fixedRate = 60000) // runs every 60 seconds
    public void checkAssetHealth() {
        List<Asset> assets = assetRepository.findAll();

        for (Asset asset : assets) {
            if (asset.getCpuUsage() >= CPU_CRITICAL_THRESHOLD) {
                asset.setStatus("CRITICAL");
                alertService.createAlert(
                        asset.getId(),
                        "CRITICAL",
                        "CPU usage critical: " + asset.getCpuUsage() + "%"
                );
            } else if (asset.getMemoryUsage() >= MEMORY_WARNING_THRESHOLD) {
                asset.setStatus("WARNING");
                alertService.createAlert(
                        asset.getId(),
                        "MEDIUM",
                        "Memory usage high: " + asset.getMemoryUsage() + "%"
                );
            } else {
                asset.setStatus("ONLINE");
            }
            assetRepository.save(asset);
        }
    }
}