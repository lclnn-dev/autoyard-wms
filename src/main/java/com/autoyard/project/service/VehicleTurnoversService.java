package com.autoyard.project.service;

import com.autoyard.project.domain.entity.VehicleTurnovers;

import java.util.List;
import java.util.UUID;

public interface VehicleTurnoversService {
    List<VehicleTurnovers> getAll();

    VehicleTurnovers add(VehicleTurnovers docRecord);

    VehicleTurnovers findByRecorderId(UUID recorderId);

    void deleteByRecorderId(UUID recorderId);
}
