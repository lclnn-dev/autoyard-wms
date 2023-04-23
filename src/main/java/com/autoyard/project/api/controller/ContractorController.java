package com.autoyard.project.api.controller;

import com.autoyard.project.api.dto.request.ContractorRequest;
import com.autoyard.project.api.dto.response.ContractorResponse;
import com.autoyard.project.domain.entity.Contractor;
import com.autoyard.project.mapper.ContractorMapper;
import com.autoyard.project.service.ContractorService;
import com.autoyard.project.validation.ContractorValidator;
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
@RequestMapping("/contractors")
@RequiredArgsConstructor
public class ContractorController {

    private final ContractorService contractorService;
    private final ContractorMapper contractorMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ContractorResponse> getAllContractors() {
        List<Contractor> contractors = contractorService.findAll();
        return contractorMapper.toContractorResponseList(contractors);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractorResponse getContractorById(@PathVariable("id") UUID id) {
        Contractor contractor = contractorService.findById(id);
        return contractorMapper.toContractorResponse(contractor);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContractorResponse createContractor(@RequestBody ContractorRequest contractorRequest) {
        ContractorValidator.validateRequest(contractorRequest);
        Contractor contractorEntity = contractorMapper.toContractorEntity(contractorRequest);
        Contractor savedContractorEntity = contractorService.add(contractorEntity);

        return contractorMapper.toContractorResponse(savedContractorEntity);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractorResponse updateContractor(@PathVariable("id") UUID id, @RequestBody ContractorRequest contractorRequest) {
        ContractorValidator.validateRequest(contractorRequest);
        Contractor contractorEntity = contractorMapper.toContractorEntity(contractorRequest, id);
        Contractor updatedContractorEntity = contractorService.update(contractorEntity);

        return contractorMapper.toContractorResponse(updatedContractorEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContractor(@PathVariable("id") UUID id) {
        contractorService.deleteById(id);
    }
}
