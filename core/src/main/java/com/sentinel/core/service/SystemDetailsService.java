package com.sentinel.core.service;

import com.sentinel.core.dto.SystemDetailsDTO;
import com.sentinel.core.entity.SystemDetails;
import com.sentinel.core.repository.SystemDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemDetailsService {

    @Autowired
    private SystemDetailsRepository systemDetailsRepository;

    public List<SystemDetailsDTO> getAll() {
        return systemDetailsRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SystemDetailsDTO getById(Long id) {
        SystemDetails systemDetails = systemDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SystemDetails not found with id: " + id));
        return convertToDTO(systemDetails);
    }

    public SystemDetailsDTO save(SystemDetailsDTO dto) {
        SystemDetails entity = new SystemDetails(
                dto.getName(),
                dto.getType(),
                dto.getDiskUsage(),
                dto.getCreateDate(),
                dto.getIpAddress(),
                dto.getCpuUsage(),
                dto.getIntUsage()
        );
        SystemDetails saved = systemDetailsRepository.save(entity);
        return convertToDTO(saved);
    }

    private SystemDetailsDTO convertToDTO(SystemDetails entity) {
        return new SystemDetailsDTO(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                entity.getDiskUsage(),
                entity.getCreateDate(),
                entity.getIpAddress(),
                entity.getCpuUsage(),
                entity.getIntUsage()
        );
    }
}