package com.sentinel.core.config;

import com.sentinel.core.entity.Asset;
import com.sentinel.core.entity.Role;
import com.sentinel.core.entity.User;
import com.sentinel.core.repository.AssetRepository;
import com.sentinel.core.repository.RoleRepository;
import com.sentinel.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        Role adminRole = getOrCreateRole("ADMIN");
        Role userRole = getOrCreateRole("USER");

        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@sentinel.local")
                    .roles(Set.of(adminRole, userRole))
                    .enabled(true)
                    .build();
            userRepository.save(admin);
        }

        if (!userRepository.existsByUsername("viewer")) {
            User viewer = User.builder()
                    .username("viewer")
                    .password(passwordEncoder.encode("viewer123"))
                    .email("viewer@sentinel.local")
                    .roles(Set.of(userRole))
                    .enabled(true)
                    .build();
            userRepository.save(viewer);
        }

        if (assetRepository.count() == 0) {
            assetRepository.save(Asset.builder()
                    .assetName("Web-Production-01")
                    .assetType("SERVER")
                    .ipAddress("192.168.1.101")
                    .cpuUsage(42.5)
                    .memoryUsage(68.0)
                    .diskUsage(55.2)
                    .networkUsage(12.4)
                    .status(Asset.AssetStatus.ONLINE)
                    .owner("DevOps Team")
                    .createDate(LocalDateTime.now().minusDays(5))
                    .build());

            assetRepository.save(Asset.builder()
                    .assetName("Database-Primary")
                    .assetType("DATABASE")
                    .ipAddress("192.168.1.102")
                    .cpuUsage(88.0)
                    .memoryUsage(91.5)
                    .diskUsage(84.1)
                    .networkUsage(45.0)
                    .status(Asset.AssetStatus.WARNING)
                    .owner("DBA Team")
                    .createDate(LocalDateTime.now().minusDays(10))
                    .build());

            assetRepository.save(Asset.builder()
                    .assetName("Auth-Gateway")
                    .assetType("GATEWAY")
                    .ipAddress("192.168.1.103")
                    .cpuUsage(15.0)
                    .memoryUsage(32.0)
                    .diskUsage(20.0)
                    .networkUsage(5.0)
                    .status(Asset.AssetStatus.ONLINE)
                    .owner("Security Team")
                    .createDate(LocalDateTime.now().minusDays(2))
                    .build());
        }
    }

    private Role getOrCreateRole(String roleName) {
        Optional<Role> roleOpt = roleRepository.findByName(roleName);
        return roleOpt.orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}