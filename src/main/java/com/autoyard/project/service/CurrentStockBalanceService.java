package com.autoyard.project.service;

import com.autoyard.project.domain.entity.CurrentStockBalance;
import com.autoyard.project.domain.entity.Vehicle;

import java.util.List;
import java.util.UUID;

public interface CurrentStockBalanceService {

    List<CurrentStockBalance> getAll();

    CurrentStockBalance findById(UUID id);

    CurrentStockBalance findByVehicle(Vehicle vehicle);

    CurrentStockBalance findByRecorderId(UUID id);

    CurrentStockBalance add(CurrentStockBalance docRecord);

    void deleteById(UUID id);

    void deleteByVehicle(Vehicle vehicle);

    void deleteByRecorderId(UUID id);

}
