package com.sentinel.core.service;

import com.sentinel.core.dto.AssetDTO;
import com.sentinel.core.dto.DashboardSummaryDTO;
import com.sentinel.core.entity.Asset;
import com.sentinel.core.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    public List<AssetDTO> getAll() {
        return assetRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AssetDTO getById(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found with id: " + id));
        return convertToDTO(asset);
    }

    public AssetDTO save(AssetDTO dto) {
        Asset entity = new Asset(
                dto.getAssetName(),
                dto.getAssetType(),
                dto.getIpAddress(),
                dto.getCpuUsage(),
                dto.getMemoryUsage(),
                dto.getDiskUsage(),
                dto.getNetworkUsage(),
                dto.getStatus(),
                dto.getOwner(),
                dto.getCreateDate()
        );
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
        existing.setStatus(dto.getStatus());
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
        double avgCpu = assets.stream().mapToDouble(Asset::getCpuUsage).average().orElse(0);
        double avgMemory = assets.stream().mapToDouble(Asset::getMemoryUsage).average().orElse(0);
        double avgDisk = assets.stream().mapToDouble(Asset::getDiskUsage).average().orElse(0);
        double avgNetwork = assets.stream().mapToDouble(Asset::getNetworkUsage).average().orElse(0);

        long online = assets.stream().filter(a -> "ONLINE".equalsIgnoreCase(a.getStatus())).count();
        long warning = assets.stream().filter(a -> "WARNING".equalsIgnoreCase(a.getStatus())).count();
        long critical = assets.stream().filter(a -> "CRITICAL".equalsIgnoreCase(a.getStatus())).count();

        return new DashboardSummaryDTO(total, avgCpu, avgMemory, avgDisk, avgNetwork, online, warning, critical);
    }

    private AssetDTO convertToDTO(Asset entity) {
        return new AssetDTO(
                entity.getId(),
                entity.getAssetName(),
                entity.getAssetType(),
                entity.getIpAddress(),
                entity.getCpuUsage(),
                entity.getMemoryUsage(),
                entity.getDiskUsage(),
                entity.getNetworkUsage(),
                entity.getStatus(),
                entity.getOwner(),
                entity.getCreateDate()
        );
    }
}