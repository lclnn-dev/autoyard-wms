package com.autoyard.project.repository;

import com.autoyard.project.domain.entity.VehicleTurnovers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VehicleTurnoversRepository extends JpaRepository<VehicleTurnovers, UUID> {
    VehicleTurnovers findByRecorderId(UUID recorderId);

    void deleteByRecorderId(UUID recorderId);
}
