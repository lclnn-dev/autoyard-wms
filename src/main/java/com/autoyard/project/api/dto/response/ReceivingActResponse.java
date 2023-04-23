package com.autoyard.project.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ReceivingActResponse {
    private UUID id;
    private Integer docNumber;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime docDateTime;

    private UUID contractorId;
    private UUID vehicleId;
    private UUID cellId;
    private BigDecimal grossWeight;
}
