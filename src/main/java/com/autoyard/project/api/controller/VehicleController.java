package com.autoyard.project.api.controller;

import com.autoyard.project.api.dto.request.VehicleRequest;
import com.autoyard.project.api.dto.response.VehicleResponse;
import com.autoyard.project.domain.entity.Vehicle;
import com.autoyard.project.mapper.VehicleMapper;
import com.autoyard.project.service.VehicleService;
import com.autoyard.project.validation.VehicleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleResponse> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.findAll();
        return vehicleMapper.toVehicleResponseList(vehicles);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleResponse getVehicleById(@PathVariable("id") UUID id) {
        Vehicle vehicle = vehicleService.findById(id);
        return vehicleMapper.toVehicleResponse(vehicle);
    }

    @GetMapping("/vincode/{vinCode}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleResponse getFirstVehicleByVinCode(@PathVariable("vinCode") String vinCode) {
        Vehicle vehicle = vehicleService.findFirstByVinCode(vinCode);
        return vehicleMapper.toVehicleResponse(vehicle);
    }

    @GetMapping("/vincodes/{vinCode}")
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleResponse> getAllVehiclesByVinCode(@PathVariable("vinCode") String vinCode) {
        List<Vehicle> vehicles = vehicleService.findAllByVinCode(vinCode);
        return vehicleMapper.toVehicleResponseList(vehicles);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse createVinCode(@RequestBody VehicleRequest vehicleRequest) {
        VehicleValidator.validateRequest(vehicleRequest);
        Vehicle vehicleEntity = vehicleMapper.toVehicleEntity(vehicleRequest);
        Vehicle savedVehicleEntity = vehicleService.add(vehicleEntity);

        return vehicleMapper.toVehicleResponse(savedVehicleEntity);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleResponse updateVinCode(@PathVariable("id") UUID id, @RequestBody VehicleRequest vehicleRequest) {
        VehicleValidator.validateRequest(vehicleRequest);
        Vehicle vehicleEntity = vehicleMapper.toVehicleEntity(vehicleRequest);
        vehicleEntity.setId(id);
        Vehicle updatedVehicleEntity = vehicleService.update(vehicleEntity);

        return vehicleMapper.toVehicleResponse(updatedVehicleEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVinCode(@PathVariable("id") UUID id) {
        vehicleService.deleteById(id);
    }
}
