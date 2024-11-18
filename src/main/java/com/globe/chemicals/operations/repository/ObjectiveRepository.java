package com.globe.chemicals.operations.repository;

import com.globe.chemicals.operations.entity.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective, Long> {
    Optional<Objective> findByTitleNameIgnoreCase(String titleName);
}
