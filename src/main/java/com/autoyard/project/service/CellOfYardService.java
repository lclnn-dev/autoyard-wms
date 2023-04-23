package com.autoyard.project.service;

import com.autoyard.project.domain.entity.CellOfYard;

import java.util.List;
import java.util.UUID;


public interface CellOfYardService {
    List<CellOfYard> findAll();

    CellOfYard findById(UUID id);

    CellOfYard add(CellOfYard cell);

    CellOfYard update(CellOfYard cell);

    void deleteById(UUID id);
}
