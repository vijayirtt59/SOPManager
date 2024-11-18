package com.globe.chemicals.operations.repository;

import com.globe.chemicals.operations.entity.Definition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefinitionRepository  extends JpaRepository<Definition, Long> {
    Definition findByName(String name);
}
