package com.autoyard.project.service.impl;

import com.autoyard.project.domain.entity.VehicleTurnovers;
import com.autoyard.project.repository.VehicleTurnoversRepository;
import com.autoyard.project.service.VehicleTurnoversService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleTurnoversServiceImpl implements VehicleTurnoversService {

    private final VehicleTurnoversRepository recordTurnoversRepository;

    @Override
    @Transactional(readOnly = true)
    public List<VehicleTurnovers> getAll() {
        return recordTurnoversRepository.findAll();
    }

    @Override
    public VehicleTurnovers add(VehicleTurnovers docRecord) {
        return recordTurnoversRepository.save(docRecord);
    }

    @Override
    public VehicleTurnovers findByRecorderId(UUID recorderId) {
        return recordTurnoversRepository.findByRecorderId(recorderId);
    }

    @Override
    public void deleteByRecorderId(UUID recorderId) {
        recordTurnoversRepository.deleteByRecorderId(recorderId);
    }


}
