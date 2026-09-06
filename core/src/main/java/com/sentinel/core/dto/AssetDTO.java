package com.sentinel.core.dto;

import java.time.LocalDateTime;

public class AssetDTO {

    private Long id;
    private String assetName;
    private String assetType;
    private String ipAddress;
    private Double cpuUsage;
    private Double memoryUsage;
    private Double diskUsage;
    private Double networkUsage;
    private String status;
    private String owner;
    private LocalDateTime createDate;

    public AssetDTO() {
    }

    public AssetDTO(Long id, String assetName, String assetType, String ipAddress, Double cpuUsage,
                    Double memoryUsage, Double diskUsage, Double networkUsage, String status,
                    String owner, LocalDateTime createDate) {
        this.id = id;
        this.assetName = assetName;
        this.assetType = assetType;
        this.ipAddress = ipAddress;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.diskUsage = diskUsage;
        this.networkUsage = networkUsage;
        this.status = status;
        this.owner = owner;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(Double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public Double getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(Double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public Double getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(Double diskUsage) {
        this.diskUsage = diskUsage;
    }

    public Double getNetworkUsage() {
        return networkUsage;
    }

    public void setNetworkUsage(Double networkUsage) {
        this.networkUsage = networkUsage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    // Manual Builder to match AssetService builder syntax
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String assetName;
        private String assetType;
        private String ipAddress;
        private Double cpuUsage;
        private Double memoryUsage;
        private Double diskUsage;
        private Double networkUsage;
        private String status;
        private String owner;
        private LocalDateTime createDate;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder assetName(String assetName) { this.assetName = assetName; return this; }
        public Builder assetType(String assetType) { this.assetType = assetType; return this; }
        public Builder ipAddress(String ipAddress) { this.ipAddress = ipAddress; return this; }
        public Builder cpuUsage(Double cpuUsage) { this.cpuUsage = cpuUsage; return this; }
        public Builder memoryUsage(Double memoryUsage) { this.memoryUsage = memoryUsage; return this; }
        public Builder diskUsage(Double diskUsage) { this.diskUsage = diskUsage; return this; }
        public Builder networkUsage(Double networkUsage) { this.networkUsage = networkUsage; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder owner(String owner) { this.owner = owner; return this; }
        public Builder createDate(LocalDateTime createDate) { this.createDate = createDate; return this; }

        public AssetDTO build() {
            return new AssetDTO(id, assetName, assetType, ipAddress, cpuUsage,
                    memoryUsage, diskUsage, networkUsage, status, owner, createDate);
        }
    }
}