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

    @Scheduled(fixedRate = 60000)
    public void checkAssetHealth() {
        List<Asset> assets = assetRepository.findAll();

        for (Asset asset : assets) {
            Double cpu = asset.getCpuUsage();
            Double memory = asset.getMemoryUsage();
            Asset.AssetStatus previousStatus = asset.getStatus();

            if (cpu != null && cpu >= CPU_CRITICAL_THRESHOLD) {
                asset.setStatus(Asset.AssetStatus.CRITICAL);
                if (previousStatus != Asset.AssetStatus.CRITICAL) {
                    alertService.createAlert(
                            asset.getId(),
                            "CRITICAL",
                            "CPU usage critical: " + cpu + "%"
                    );
                }
            } else if (memory != null && memory >= MEMORY_WARNING_THRESHOLD) {
                asset.setStatus(Asset.AssetStatus.WARNING);
                if (previousStatus != Asset.AssetStatus.WARNING) {
                    alertService.createAlert(
                            asset.getId(),
                            "WARNING",
                            "Memory usage high: " + memory + "%"
                    );
                }
            } else {
                asset.setStatus(Asset.AssetStatus.ONLINE);
            }

            assetRepository.save(asset);
        }
    }
}