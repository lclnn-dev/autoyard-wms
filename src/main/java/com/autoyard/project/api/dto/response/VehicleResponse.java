package com.autoyard.project.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {
    private UUID id;
    private Integer customCode;
    private String vinCode;
    private String make;
    private String model;
    private String year;
}