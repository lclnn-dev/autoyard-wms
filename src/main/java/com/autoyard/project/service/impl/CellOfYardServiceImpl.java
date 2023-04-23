package com.autoyard.project.service.impl;

import com.autoyard.project.api.exception.CustomEntityNotFoundException;
import com.autoyard.project.domain.entity.CellOfYard;
import com.autoyard.project.repository.CellOfYardRepository;
import com.autoyard.project.service.CellOfYardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CellOfYardServiceImpl implements CellOfYardService {

    private final CellOfYardRepository cellOfYardRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CellOfYard> findAll() {
        return cellOfYardRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CellOfYard findById(UUID id) {
        return cellOfYardRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(CellOfYard.class, "id", id.toString()));
    }

    @Override
    public CellOfYard add(CellOfYard cell) {
        return cellOfYardRepository.save(cell);
    }

    @Override
    public CellOfYard update(CellOfYard cell) {
        UUID id = cell.getId();
        CellOfYard foundCell = cellOfYardRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(CellOfYard.class, "id", id.toString()));
        foundCell.setName(cell.getName());
        foundCell.setFolder(cell.isFolder());
        foundCell.setParent(cell.getParent());

        return cellOfYardRepository.save(foundCell);
    }

    @Override
    public void deleteById(UUID id) {
        cellOfYardRepository.findById(id).ifPresentOrElse(
                vinCode -> cellOfYardRepository.deleteById(id),
                () -> {
                    throw new CustomEntityNotFoundException(CellOfYard.class, "id", id.toString());
                }
        );
    }
}
