package com.autoyard.project.service;

import com.autoyard.project.domain.entity.Contractor;

import java.util.List;
import java.util.UUID;

public interface ContractorService {
    List<Contractor> findAll();

    Contractor findById(UUID id);

    Contractor add(Contractor contractor);

    Contractor update(Contractor contractor);

    void deleteById(UUID id);
}
