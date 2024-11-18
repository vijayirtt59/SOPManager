package com.globe.chemicals.operations.repository;

import com.globe.chemicals.operations.entity.Objective;
import com.globe.chemicals.operations.entity.Responsibility;
import com.globe.chemicals.operations.entity.TitleResponsibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResponsibilityRepository extends JpaRepository<Responsibility, Long> {

    Optional<Responsibility> findByPositionContainingIgnoreCase(String position);

}
