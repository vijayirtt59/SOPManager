package com.globe.chemicals.operations.repository;

import com.globe.chemicals.operations.entity.Development;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DevelopmentRepository   extends JpaRepository<Development, Long> {
    Optional<Development> findByActivity(String activity);
}
