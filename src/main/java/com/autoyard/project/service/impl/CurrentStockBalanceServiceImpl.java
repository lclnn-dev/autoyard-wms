package com.autoyard.project.service.impl;

import com.autoyard.project.api.exception.CustomEntityNotFoundException;
import com.autoyard.project.domain.entity.CurrentStockBalance;
import com.autoyard.project.domain.entity.Vehicle;
import com.autoyard.project.repository.CurrentStockBalanceRepository;
import com.autoyard.project.service.CurrentStockBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class CurrentStockBalanceServiceImpl implements CurrentStockBalanceService {

    private final CurrentStockBalanceRepository recordBalanceRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CurrentStockBalance> getAll() {
        return recordBalanceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CurrentStockBalance findById(UUID id) {
        return recordBalanceRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(CurrentStockBalance.class, "id", id.toString()));
    }

    @Override
    @Transactional(readOnly = true)
    public CurrentStockBalance findByVehicle(Vehicle vehicle) {
        return recordBalanceRepository.findByVehicle(vehicle);
    }

    @Override
    @Transactional(readOnly = true)
    public CurrentStockBalance findByRecorderId(UUID recorderId) {
        return recordBalanceRepository.findByRecorderId(recorderId);
    }

    @Override
    public CurrentStockBalance add(CurrentStockBalance docRecord) {
        return recordBalanceRepository.save(docRecord);
    }

    @Override
    public void deleteById(UUID id) {
        recordBalanceRepository.findById(id).ifPresentOrElse(
                doc -> recordBalanceRepository.deleteById(id),
                () -> {
                    throw new CustomEntityNotFoundException(CurrentStockBalance.class, "id", id.toString());
                }
        );
    }

    @Override
    public void deleteByVehicle(Vehicle vehicle) {
        recordBalanceRepository.deleteByVehicle(vehicle);
    }

    @Override
    public void deleteByRecorderId(UUID recorderId) {
        recordBalanceRepository.deleteByRecorderId(recorderId);
    }
}
