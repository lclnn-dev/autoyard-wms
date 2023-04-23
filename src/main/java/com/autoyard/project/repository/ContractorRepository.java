package com.autoyard.project.repository;

import com.autoyard.project.domain.entity.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContractorRepository extends JpaRepository<Contractor, UUID> {
    List<Contractor> findAllByName(String name);
}
