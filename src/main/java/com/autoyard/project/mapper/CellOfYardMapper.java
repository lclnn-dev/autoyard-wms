package com.autoyard.project.mapper;

import com.autoyard.project.api.dto.request.CellOfYardRequest;
import com.autoyard.project.api.dto.response.CellOfYardResponse;
import com.autoyard.project.domain.entity.CellOfYard;
import com.autoyard.project.service.CellOfYardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CellOfYardMapper {

    private final CellOfYardService cellOfYardService;

    public CellOfYardResponse toCellResponse(CellOfYard cell) {
        CellOfYardResponse response = new CellOfYardResponse();
        response.setId(cell.getId());
        response.setName(cell.getName());
        response.setFolder(cell.isFolder());
        if (cell.getParent() != null) {
            response.setParentId(cell.getParent().getId());
        }

        return response;
    }

    public List<CellOfYardResponse> toCellResponseList(List<CellOfYard> cells) {
        List<CellOfYardResponse> cellsResponse = new ArrayList<>();
        for (CellOfYard cell : cells) {
            cellsResponse.add(toCellResponse(cell));
        }

        return cellsResponse;
    }

    public CellOfYard toCellEntity(CellOfYardRequest request) {
        CellOfYard entity = new CellOfYard();
        entity.setName(request.getName());
        entity.setFolder(request.isFolder());
        if (request.getParentId() != null) {
            entity.setParent(cellOfYardService.findById(request.getParentId()));
        }

        return entity;
    }

    public CellOfYard toCellEntity(CellOfYardRequest request, UUID id) {
        CellOfYard entity = cellOfYardService.findById(id);
        entity.setName(request.getName());
        entity.setFolder(request.isFolder());
        if (request.getParentId() != null) {
            entity.setParent(cellOfYardService.findById(request.getParentId()));
        }

        return entity;
    }
}
