package com.autoyard.project.repository;

import com.autoyard.project.domain.entity.CurrentStockBalance;
import com.autoyard.project.domain.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CurrentStockBalanceRepository extends JpaRepository<CurrentStockBalance, UUID> {

    void deleteByVehicle(Vehicle vehicle);

    CurrentStockBalance findByVehicle(Vehicle vehicle);

    void deleteByRecorderId(UUID recorderId);

    CurrentStockBalance findByRecorderId(UUID recorderId);
}
