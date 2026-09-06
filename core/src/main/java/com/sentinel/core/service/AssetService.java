package com.sentinel.core.service;

import com.sentinel.core.dto.AssetDTO;
import com.sentinel.core.dto.DashboardSummaryDTO;
import com.sentinel.core.entity.Asset;
import com.sentinel.core.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sentinel.core.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    public List<AssetDTO> getAllAssets() {
        return assetRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AssetDTO getAssetById(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
        return convertToDTO(asset);
    }

    public AssetDTO createAsset(AssetDTO dto) {
        Asset entity = Asset.builder()
                .assetName(dto.getAssetName())
                .assetType(dto.getAssetType())
                .ipAddress(dto.getIpAddress())
                .cpuUsage(dto.getCpuUsage())
                .memoryUsage(dto.getMemoryUsage())
                .diskUsage(dto.getDiskUsage())
                .networkUsage(dto.getNetworkUsage())
                .status(dto.getStatus() != null ? Asset.AssetStatus.valueOf(dto.getStatus().toUpperCase()) : Asset.AssetStatus.ONLINE)
                .owner(dto.getOwner())
                .createDate(dto.getCreateDate() != null ? dto.getCreateDate() : LocalDateTime.now())
                .build();

        Asset saved = assetRepository.save(entity);
        return convertToDTO(saved);
    }

    public AssetDTO update(Long id, AssetDTO dto) {
        Asset existing = assetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found with id: " + id));

        existing.setAssetName(dto.getAssetName());
        existing.setAssetType(dto.getAssetType());
        existing.setIpAddress(dto.getIpAddress());
        existing.setCpuUsage(dto.getCpuUsage());
        existing.setMemoryUsage(dto.getMemoryUsage());
        existing.setDiskUsage(dto.getDiskUsage());
        existing.setNetworkUsage(dto.getNetworkUsage());
        if (dto.getStatus() != null) {
            existing.setStatus(Asset.AssetStatus.valueOf(dto.getStatus().toUpperCase()));
        }
        existing.setOwner(dto.getOwner());
        existing.setCreateDate(dto.getCreateDate());

        Asset updated = assetRepository.save(existing);
        return convertToDTO(updated);
    }

    public void delete(Long id) {
        if (!assetRepository.existsById(id)) {
            throw new RuntimeException("Asset not found with id: " + id);
        }
        assetRepository.deleteById(id);
    }

    public DashboardSummaryDTO getDashboardSummary() {
        List<Asset> assets = assetRepository.findAll();

        long total = assets.size();
        long online = assets.stream().filter(a -> a.getStatus() == Asset.AssetStatus.ONLINE).count();
        long offline = assets.stream().filter(a -> a.getStatus() == Asset.AssetStatus.OFFLINE).count();
        long warning = assets.stream().filter(a -> a.getStatus() == Asset.AssetStatus.WARNING).count();
        long critical = assets.stream().filter(a -> a.getStatus() == Asset.AssetStatus.CRITICAL).count();

        double avgCpu = assets.stream()
                .map(Asset::getCpuUsage)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        double avgMemory = assets.stream()
                .map(Asset::getMemoryUsage)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        double avgDisk = assets.stream()
                .map(Asset::getDiskUsage)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        double avgNetwork = assets.stream()
                .map(Asset::getNetworkUsage)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        double uptime = (total == 0) ? 0.0 : ((double) online / total) * 100.0;

        return DashboardSummaryDTO.builder()
                .totalAssets(total)
                .uptimePercentage(uptime)
                .onlineAssets(online)
                .offlineAssets(offline)
                .criticalAlerts(critical)
                .avgCpuUsage(avgCpu)
                .avgMemoryUsage(avgMemory)
                .avgDiskUsage(avgDisk)
                .networkUsage(avgNetwork)
                .warningCount(warning)
                .build();
    }

    private AssetDTO convertToDTO(Asset entity) {
        return AssetDTO.builder()
                .id(entity.getId())
                .assetName(entity.getAssetName())
                .assetType(entity.getAssetType())
                .ipAddress(entity.getIpAddress())
                .cpuUsage(entity.getCpuUsage())
                .memoryUsage(entity.getMemoryUsage())
                .diskUsage(entity.getDiskUsage())
                .networkUsage(entity.getNetworkUsage())
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .owner(entity.getOwner())
                .createDate(entity.getCreateDate())
                .build();
    }
}