package com.autoyard.project.mapper;

import com.autoyard.project.api.dto.request.ReceivingActRequest;
import com.autoyard.project.api.dto.response.ReceivingActResponse;
import com.autoyard.project.domain.entity.ReceivingAct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReceivingActMapper {
    List<ReceivingActResponse> toReceivingActResponseList(List<ReceivingAct> docs);

    @Mapping(source = "contractor.id", target = "contractorId")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "cell.id", target = "cellId")
    ReceivingActResponse toReceivingActResponse(ReceivingAct doc);

    @Mapping(source = "contractorId", target = "contractor.id")
    @Mapping(source = "vehicleId", target = "vehicle.id")
    @Mapping(source = "cellId", target = "cell.id")
    ReceivingAct toReceivingActEntity(ReceivingActRequest docRequest);

}
