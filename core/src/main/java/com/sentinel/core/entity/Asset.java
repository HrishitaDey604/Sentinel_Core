package com.sentinel.core.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "asset_name", nullable = false)
    private String assetName;

    @Column(name = "asset_type", nullable = false)
    private String assetType;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "cpu_usage")
    private Double cpuUsage;

    @Column(name = "memory_usage")
    private Double memoryUsage;

    @Column(name = "disk_usage")
    private Double diskUsage;

    @Column(name = "network_usage")
    private Double networkUsage;

    @Enumerated(EnumType.STRING)
    private AssetStatus status;

    private String owner;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    public enum AssetStatus {
        ONLINE, OFFLINE, WARNING, CRITICAL
    }

    public Asset() {
    }

    public Asset(Long id, String assetName, String assetType, String ipAddress,
                 Double cpuUsage, Double memoryUsage, Double diskUsage, Double networkUsage,
                 AssetStatus status, String owner, LocalDateTime createDate) {
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

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }

    public String getAssetType() { return assetType; }
    public void setAssetType(String assetType) { this.assetType = assetType; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public Double getCpuUsage() { return cpuUsage; }
    public void setCpuUsage(Double cpuUsage) { this.cpuUsage = cpuUsage; }

    public Double getMemoryUsage() { return memoryUsage; }
    public void setMemoryUsage(Double memoryUsage) { this.memoryUsage = memoryUsage; }

    public Double getDiskUsage() { return diskUsage; }
    public void setDiskUsage(Double diskUsage) { this.diskUsage = diskUsage; }

    public Double getNetworkUsage() { return networkUsage; }
    public void setNetworkUsage(Double networkUsage) { this.networkUsage = networkUsage; }

    public AssetStatus getStatus() { return status; }
    public void setStatus(AssetStatus status) { this.status = status; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }

    // Manual Builder to keep AssetService.java compatible
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
        private AssetStatus status;
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
        public Builder status(AssetStatus status) { this.status = status; return this; }
        public Builder owner(String owner) { this.owner = owner; return this; }
        public Builder createDate(LocalDateTime createDate) { this.createDate = createDate; return this; }

        public Asset build() {
            return new Asset(id, assetName, assetType, ipAddress, cpuUsage,
                    memoryUsage, diskUsage, networkUsage, status, owner, createDate);
        }
    }
}