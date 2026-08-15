package com.sentinel.core.controller;

import com.sentinel.core.dto.AssetDTO;
import com.sentinel.core.dto.DashboardSummaryDTO;
import com.sentinel.core.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping
    public List<AssetDTO> getAll() {
        return assetService.getAll();
    }

    @GetMapping("/{id}")
    public AssetDTO getById(@PathVariable Long id) {
        return assetService.getById(id);
    }

    @PostMapping
    public AssetDTO create(@RequestBody AssetDTO dto) {
        return assetService.save(dto);
    }

    @PutMapping("/{id}")
    public AssetDTO update(@PathVariable Long id, @RequestBody AssetDTO dto) {
        return assetService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        assetService.delete(id);
        return "Deleted successfully";
    }

    @GetMapping("/dashboard/summary")
    public DashboardSummaryDTO getDashboardSummary() {
        return assetService.getDashboardSummary();
    }
}