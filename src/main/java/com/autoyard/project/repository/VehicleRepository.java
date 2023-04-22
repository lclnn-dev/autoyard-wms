package com.autoyard.project.repository;

import com.autoyard.project.domain.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    Optional<Vehicle> findFirstByVinCode(String vinCode);

    List<Vehicle> findAllByVinCode(String vinCode);
}
