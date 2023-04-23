package com.autoyard.project.api.controller;

import com.autoyard.project.api.dto.request.CellOfYardRequest;
import com.autoyard.project.api.dto.response.CellOfYardResponse;
import com.autoyard.project.domain.entity.CellOfYard;
import com.autoyard.project.mapper.CellOfYardMapper;
import com.autoyard.project.service.CellOfYardService;
import com.autoyard.project.validation.CellOfYardValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cells")
@RequiredArgsConstructor
public class CellOfYardController {

    private final CellOfYardService cellOfYardService;
    private final CellOfYardMapper cellOfYardMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CellOfYardResponse> getAllCells() {
        List<CellOfYard> cells = cellOfYardService.findAll();
        return cellOfYardMapper.toCellResponseList(cells);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CellOfYardResponse getVinCodeById(@PathVariable("id") UUID id) {
        CellOfYard vinCode = cellOfYardService.findById(id);
        return cellOfYardMapper.toCellResponse(vinCode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CellOfYardResponse createCellOfYard(@RequestBody CellOfYardRequest cellRequest) {
        CellOfYardValidator.validateRequest(cellRequest);
        CellOfYard cellEntity = cellOfYardMapper.toCellEntity(cellRequest);
        CellOfYard savedCellEntity = cellOfYardService.add(cellEntity);

        return cellOfYardMapper.toCellResponse(savedCellEntity);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CellOfYardResponse updateVinCode(@PathVariable("id") UUID id, @RequestBody CellOfYardRequest cellRequest) {
        CellOfYardValidator.validateRequest(cellRequest);
        CellOfYard cellEntity = cellOfYardMapper.toCellEntity(cellRequest, id);
        CellOfYard updatedCellEntity = cellOfYardService.update(cellEntity);

        return cellOfYardMapper.toCellResponse(updatedCellEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVinCode(@PathVariable(value = "id") UUID id) {
        cellOfYardService.deleteById(id);
    }
}
