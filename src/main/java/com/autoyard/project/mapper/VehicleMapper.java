package com.autoyard.project.mapper;

import com.autoyard.project.api.dto.request.VehicleRequest;
import com.autoyard.project.api.dto.response.VehicleResponse;
import com.autoyard.project.domain.entity.Vehicle;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleResponse toVehicleResponse(Vehicle vehicle);

    List<VehicleResponse> toVehicleResponseList(List<Vehicle> vehicles);

    Vehicle toVehicleEntity(VehicleRequest request);
}
