package com.autoyard.project.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequest {

    @NotBlank
    @Size(max = 17)
    private String vinCode;

    @NotNull
    private String make;

    @NotNull
    private String model;

    @Pattern(regexp = "^$|^(19|20)\\d{2}$", message = "Value must contain exactly 4 digits between 1900 and 2099 or be empty.")
    private String year;
}
