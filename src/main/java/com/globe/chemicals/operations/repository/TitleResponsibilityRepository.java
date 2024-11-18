package com.globe.chemicals.operations.repository;

import com.globe.chemicals.operations.entity.TitleResponsibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleResponsibilityRepository extends JpaRepository<TitleResponsibility, Long> {

    List<TitleResponsibility> findByTitle_Id(Long titleId);
    List<TitleResponsibility> findByTitleId(Long titleId);
}
