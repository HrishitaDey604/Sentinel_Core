package com.sentinel.core.controller;

import com.sentinel.core.dto.SystemDetailsDTO;
import com.sentinel.core.service.SystemDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system-details")
public class SystemDetailsController {

    @Autowired
    private SystemDetailsService systemDetailsService;

    @GetMapping
    public List<SystemDetailsDTO> getAll() {
        return systemDetailsService.getAll();
    }

    @GetMapping("/{id}")
    public SystemDetailsDTO getById(@PathVariable Long id) {
        return systemDetailsService.getById(id);
    }

    @PostMapping
    public SystemDetailsDTO create(@RequestBody SystemDetailsDTO dto) {
        return systemDetailsService.save(dto);
    }
}