package com.autoyard.project.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ContractorResponse {
    private UUID id;
    private Integer customCode;
    private String name;
    private String fullName;
    private String edrpou;
}
