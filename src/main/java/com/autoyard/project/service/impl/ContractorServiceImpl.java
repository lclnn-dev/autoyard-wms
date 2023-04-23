package com.autoyard.project.service.impl;

import com.autoyard.project.api.exception.CustomEntityNotFoundException;
import com.autoyard.project.domain.entity.Contractor;
import com.autoyard.project.repository.ContractorRepository;
import com.autoyard.project.service.ContractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository contractorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Contractor> findAll() {
        return contractorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Contractor findById(UUID id) {
        return contractorRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(Contractor.class, "id", id.toString()));
    }

    @Override
    public Contractor add(Contractor contractor) {
        return contractorRepository.save(contractor);
    }

    @Override
    public Contractor update(Contractor contractor) {
        UUID id = contractor.getId();
        Contractor foundContractor = contractorRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(Contractor.class, "id", id.toString()));
        foundContractor.setName(contractor.getName());
        foundContractor.setFullName(contractor.getFullName());
        foundContractor.setEdrpou(contractor.getEdrpou());

        return foundContractor;
    }

    @Override
    public void deleteById(UUID id) {
        contractorRepository.findById(id).ifPresentOrElse(
                vinCode -> contractorRepository.deleteById(id),
                () -> {
                    throw new CustomEntityNotFoundException(Contractor.class, "id", id.toString());
                }
        );
    }
}
