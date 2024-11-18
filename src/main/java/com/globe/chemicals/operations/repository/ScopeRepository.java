package com.globe.chemicals.operations.repository;

import com.globe.chemicals.operations.entity.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScopeRepository extends JpaRepository<Scope, Long> {
    Optional<Scope> findByTitleNameIgnoreCase(String titleName);
}
