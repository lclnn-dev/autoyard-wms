package com.autoyard.project.mapper;

import com.autoyard.project.api.dto.request.ContractorRequest;
import com.autoyard.project.api.dto.response.ContractorResponse;
import com.autoyard.project.domain.entity.Contractor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractorMapper {
    ContractorResponse toContractorResponse(Contractor contractor);

    List<ContractorResponse> toContractorResponseList(List<Contractor> contractors);

    Contractor toContractorEntity(ContractorRequest request);
}
