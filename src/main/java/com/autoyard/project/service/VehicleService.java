package com.autoyard.project.service;

import com.autoyard.project.domain.entity.Vehicle;

import java.util.List;
import java.util.UUID;

public interface VehicleService {

    List<Vehicle> findAll();

    Vehicle findById(UUID id);

    Vehicle findFirstByVinCode(String vinCode);

    List<Vehicle> findAllByVinCode(String vinCode);

    Vehicle add(Vehicle vehicles);

    Vehicle update(Vehicle vehicles);

    void deleteById(UUID id);
}
