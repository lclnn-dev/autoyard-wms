package com.autoyard.project.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractorRequest {

    @NotBlank
    @Size(max = 150)
    private String name;

    @NotNull
    @Size(max = 300)
    private String fullName;

    @NotNull
    @Size(max = 10)
    private String edrpou;
}
