package com.autoyard.project.mapper;

import com.autoyard.project.api.dto.request.CellOfYardRequest;
import com.autoyard.project.api.dto.response.CellOfYardResponse;
import com.autoyard.project.domain.entity.CellOfYard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CellOfYardMapper {

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
            CellOfYard parent = new CellOfYard();
            parent.setId(request.getParentId());
            entity.setParent(parent);
        }

        return entity;
    }
}
