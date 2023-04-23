package com.autoyard.project.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CurrentStockBalanceResponse {

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime dateArrival;

    private UUID recorderId;
    private UUID vehicleId;
    private UUID cellId;
    private BigDecimal grossWeight;
}
