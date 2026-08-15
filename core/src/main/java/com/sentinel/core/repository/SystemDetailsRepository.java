package com.sentinel.core.repository;

import com.sentinel.core.entity.SystemDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemDetailsRepository extends JpaRepository<SystemDetails, Long> {
}