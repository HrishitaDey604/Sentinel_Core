package com.sentinel.core.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_details")
public class SystemDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @Column(name = "disk_usage")
    private double diskUsage;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "ip_address")

    private String ipAddress;

    @Column(name = "cpu_usage")
    private double cpuUsage;

    @Column(name = "int_usage")
    private double intUsage;

    public SystemDetails() {
    }

    public SystemDetails(String name, String type, double diskUsage, LocalDateTime createDate,
                         String ipAddress, double cpuUsage, double intUsage) {
        this.name = name;
        this.type = type;
        this.diskUsage = diskUsage;
        this.createDate = createDate;
        this.ipAddress = ipAddress;
        this.cpuUsage = cpuUsage;
        this.intUsage = intUsage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(double diskUsage) {
        this.diskUsage = diskUsage;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getIntUsage() {
        return intUsage;
    }

    public void setIntUsage(double intUsage) {
        this.intUsage = intUsage;
    }
}