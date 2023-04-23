package com.autoyard.project.repository;

import com.autoyard.project.domain.entity.CellOfYard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface CellOfYardRepository extends JpaRepository<CellOfYard, UUID> {

}
