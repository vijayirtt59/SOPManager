package com.globe.chemicals.operations.repository;

import com.globe.chemicals.operations.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {

    @Query("SELECT t FROM Title t LEFT JOIN FETCH t.responsibilities WHERE t.id = :id")
    Optional<Title> findByIdWithResponsibilities(Long id);
}
