package com.sentinel.core.dto;

public class DashboardSummaryDTO {

    private Long totalAssets;
    private Double uptimePercentage;
    private Long onlineAssets;
    private Long offlineAssets;
    private Long criticalAlerts;
    private Double avgCpuUsage;
    private Double avgMemoryUsage;
    private Double avgDiskUsage;
    private Double avgNetworkUsage;
    private Long warningCount;

    public DashboardSummaryDTO() {
    }

    public DashboardSummaryDTO(Long totalAssets, Double uptimePercentage, Long onlineAssets,
                               Long offlineAssets, Long criticalAlerts, Double avgCpuUsage,
                               Double avgMemoryUsage, Double avgDiskUsage, Double avgNetworkUsage,
                               Long warningCount) {
        this.totalAssets = totalAssets;
        this.uptimePercentage = uptimePercentage;
        this.onlineAssets = onlineAssets;
        this.offlineAssets = offlineAssets;
        this.criticalAlerts = criticalAlerts;
        this.avgCpuUsage = avgCpuUsage;
        this.avgMemoryUsage = avgMemoryUsage;
        this.avgDiskUsage = avgDiskUsage;
        this.avgNetworkUsage = avgNetworkUsage;
        this.warningCount = warningCount;
    }

    public Long getTotalAssets() { return totalAssets; }
    public void setTotalAssets(Long totalAssets) { this.totalAssets = totalAssets; }

    public Double getUptimePercentage() { return uptimePercentage; }
    public void setUptimePercentage(Double uptimePercentage) { this.uptimePercentage = uptimePercentage; }

    public Long getOnlineAssets() { return onlineAssets; }
    public void setOnlineAssets(Long onlineAssets) { this.onlineAssets = onlineAssets; }

    public Long getOfflineAssets() { return offlineAssets; }
    public void setOfflineAssets(Long offlineAssets) { this.offlineAssets = offlineAssets; }

    public Long getCriticalAlerts() { return criticalAlerts; }
    public void setCriticalAlerts(Long criticalAlerts) { this.criticalAlerts = criticalAlerts; }

    public Double getAvgCpuUsage() { return avgCpuUsage; }
    public void setAvgCpuUsage(Double avgCpuUsage) { this.avgCpuUsage = avgCpuUsage; }

    public Double getAvgMemoryUsage() { return avgMemoryUsage; }
    public void setAvgMemoryUsage(Double avgMemoryUsage) { this.avgMemoryUsage = avgMemoryUsage; }

    public Double getAvgDiskUsage() { return avgDiskUsage; }
    public void setAvgDiskUsage(Double avgDiskUsage) { this.avgDiskUsage = avgDiskUsage; }

    public Double getAvgNetworkUsage() { return avgNetworkUsage; }
    public void setAvgNetworkUsage(Double avgNetworkUsage) { this.avgNetworkUsage = avgNetworkUsage; }

    public Long getWarningCount() { return warningCount; }
    public void setWarningCount(Long warningCount) { this.warningCount = warningCount; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long totalAssets;
        private Double uptimePercentage;
        private Long onlineAssets;
        private Long offlineAssets;
        private Long criticalAlerts;
        private Double avgCpuUsage;
        private Double avgMemoryUsage;
        private Double avgDiskUsage;
        private Double avgNetworkUsage;
        private Long warningCount;

        public Builder totalAssets(Long totalAssets) { this.totalAssets = totalAssets; return this; }
        public Builder uptimePercentage(Double uptimePercentage) { this.uptimePercentage = uptimePercentage; return this; }
        public Builder onlineAssets(Long onlineAssets) { this.onlineAssets = onlineAssets; return this; }
        public Builder offlineAssets(Long offlineAssets) { this.offlineAssets = offlineAssets; return this; }
        public Builder criticalAlerts(Long criticalAlerts) { this.criticalAlerts = criticalAlerts; return this; }
        public Builder avgCpuUsage(Double avgCpuUsage) { this.avgCpuUsage = avgCpuUsage; return this; }
        public Builder avgMemoryUsage(Double avgMemoryUsage) { this.avgMemoryUsage = avgMemoryUsage; return this; }
        public Builder avgDiskUsage(Double avgDiskUsage) { this.avgDiskUsage = avgDiskUsage; return this; }
        public Builder networkUsage(Double avgNetworkUsage) { this.avgNetworkUsage = avgNetworkUsage; return this; }
        public Builder warningCount(Long warningCount) { this.warningCount = warningCount; return this; }

        public DashboardSummaryDTO build() {
            return new DashboardSummaryDTO(totalAssets, uptimePercentage, onlineAssets,
                    offlineAssets, criticalAlerts, avgCpuUsage, avgMemoryUsage, avgDiskUsage,
                    avgNetworkUsage, warningCount);
        }
    }
}