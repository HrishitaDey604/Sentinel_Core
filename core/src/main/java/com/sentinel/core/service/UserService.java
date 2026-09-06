package com.sentinel.core.service;

import com.sentinel.core.entity.Role;
import com.sentinel.core.entity.User;
import com.sentinel.core.repository.RoleRepository;
import com.sentinel.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String email) {
        Role viewerRole = roleRepository.findByName("ROLE_VIEWER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_VIEWER")));

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRoles(Set.of(viewerRole));
        user.setEnabled(true);

        return userRepository.save(user);
    }
}