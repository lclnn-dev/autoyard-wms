package com.autoyard.project.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ReceivingActRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss[.SSS][.SS][.S]")
    private LocalDateTime docDateTime;

    @NotNull
    private UUID contractorId;

    @NotNull
    private UUID vehicleId;

    @NotNull
    private UUID cellId;

    @NotNull
    private BigDecimal grossWeight;
}
