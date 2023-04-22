package com.autoyard.project.service.impl;

import com.autoyard.project.api.exception.CustomEntityNotFoundException;
import com.autoyard.project.domain.entity.Vehicle;
import com.autoyard.project.repository.VehicleRepository;
import com.autoyard.project.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicle findById(UUID id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(Vehicle.class, "id", id.toString()));
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicle findFirstByVinCode(String vinCode) {
        return vehicleRepository.findFirstByVinCode(vinCode)
                .orElseThrow(() -> new CustomEntityNotFoundException(Vehicle.class, "vinCode", vinCode));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> findAllByVinCode(String vinCode) {
        return vehicleRepository.findAllByVinCode(vinCode);
    }

    @Override
    public Vehicle add(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        UUID id = vehicle.getId();
        Vehicle foundVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(Vehicle.class, "id", id.toString()));

        foundVehicle.setVinCode(vehicle.getVinCode());
        foundVehicle.setMake(vehicle.getMake());
        foundVehicle.setModel(vehicle.getModel());
        foundVehicle.setYear(vehicle.getYear());

        return vehicleRepository.save(foundVehicle);
    }

    @Override
    public void deleteById(UUID id) {
        vehicleRepository.findById(id).ifPresentOrElse(
                vinCode -> vehicleRepository.deleteById(id),
                () -> {
                    throw new CustomEntityNotFoundException(Vehicle.class, "id", id.toString());
                }
        );
    }
}
