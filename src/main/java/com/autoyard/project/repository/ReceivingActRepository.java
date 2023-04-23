package com.autoyard.project.repository;

import com.autoyard.project.domain.entity.ReceivingAct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReceivingActRepository extends JpaRepository<ReceivingAct, UUID> {
    List<ReceivingAct> findAllByDocNumber(Integer docNumber);
}
