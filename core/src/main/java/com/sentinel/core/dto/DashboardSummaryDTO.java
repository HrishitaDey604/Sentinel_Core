package com.sentinel.core.dto;

public class DashboardSummaryDTO {

    private long totalAssets;
    private double avgCpuUsage;
    private double avgMemoryUsage;
    private double avgDiskUsage;
    private double avgNetworkUsage;
    private long onlineCount;
    private long warningCount;
    private long criticalCount;

    public DashboardSummaryDTO() {
    }

    public DashboardSummaryDTO(long totalAssets, double avgCpuUsage, double avgMemoryUsage,
                               double avgDiskUsage, double avgNetworkUsage,
                               long onlineCount, long warningCount, long criticalCount) {
        this.totalAssets = totalAssets;
        this.avgCpuUsage = avgCpuUsage;
        this.avgMemoryUsage = avgMemoryUsage;
        this.avgDiskUsage = avgDiskUsage;
        this.avgNetworkUsage = avgNetworkUsage;
        this.onlineCount = onlineCount;
        this.warningCount = warningCount;
        this.criticalCount = criticalCount;
    }

    public long getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(long totalAssets) {
        this.totalAssets = totalAssets;
    }

    public double getAvgCpuUsage() {
        return avgCpuUsage;
    }

    public void setAvgCpuUsage(double avgCpuUsage) {
        this.avgCpuUsage = avgCpuUsage;
    }

    public double getAvgMemoryUsage() {
        return avgMemoryUsage;
    }

    public void setAvgMemoryUsage(double avgMemoryUsage) {
        this.avgMemoryUsage = avgMemoryUsage;
    }

    public double getAvgDiskUsage() {
        return avgDiskUsage;
    }

    public void setAvgDiskUsage(double avgDiskUsage) {
        this.avgDiskUsage = avgDiskUsage;
    }

    public double getAvgNetworkUsage() {
        return avgNetworkUsage;
    }

    public void setAvgNetworkUsage(double avgNetworkUsage) {
        this.avgNetworkUsage = avgNetworkUsage;
    }

    public long getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(long onlineCount) {
        this.onlineCount = onlineCount;
    }

    public long getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(long warningCount) {
        this.warningCount = warningCount;
    }

    public long getCriticalCount() {
        return criticalCount;
    }

    public void setCriticalCount(long criticalCount) {
        this.criticalCount = criticalCount;
    }
}