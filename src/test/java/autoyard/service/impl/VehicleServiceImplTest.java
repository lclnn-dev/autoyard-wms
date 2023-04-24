package autoyard.service.impl;

import com.autoyard.project.api.exception.CustomEntityNotFoundException;
import com.autoyard.project.domain.entity.Vehicle;
import com.autoyard.project.repository.VehicleRepository;
import com.autoyard.project.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    public VehicleServiceImplTest() {
    }

    @Test
    void shouldReturnAllVinCodes() {
        List<Vehicle> vehicles = new ArrayList<>();

        Vehicle vehicles1 = new Vehicle();
        vehicles1.setId(UUID.randomUUID());
        vehicles1.setVinCode("VIN12345678901234");
        vehicles1.setMake("Brand1");
        vehicles1.setModel("Model1");
        vehicles1.setYear("2022");
        vehicles.add(vehicles1);

        Vehicle vehicles2 = new Vehicle();
        vehicles2.setId(UUID.randomUUID());
        vehicles2.setVinCode("VIN23456789012345");
        vehicles2.setMake("Brand2");
        vehicles2.setModel("Model2");
        vehicles2.setYear("2023");
        vehicles.add(vehicles2);

        when(vehicleRepository.findAll()).thenReturn(vehicles);

        List<Vehicle> result = vehicleService.findAll();

        assertEquals(2, result.size());
        assertEquals(vehicles1, result.get(0));
        assertEquals(vehicles2, result.get(1));
    }

    @Test
    void shouldReturnVinCode_whenFindById() {
        UUID id = UUID.randomUUID();

        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        vehicle.setVinCode("VIN12345678901234");
        vehicle.setMake("Brand");
        vehicle.setModel("Model");
        vehicle.setYear("2022");

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));

        Vehicle foundVehicles = vehicleService.findById(id);

        assertNotNull(foundVehicles);
        assertEquals(vehicle.getId(), foundVehicles.getId());
        assertEquals(vehicle.getVinCode(), foundVehicles.getVinCode());
        assertEquals(vehicle.getMake(), foundVehicles.getMake());
        assertEquals(vehicle.getModel(), foundVehicles.getModel());
        assertEquals(vehicle.getYear(), foundVehicles.getYear());

        verify(vehicleRepository, times(1)).findById(id);
    }

    @Test
    void shouldReturnVinCode_whenFindFirstByVinCode() {
        String vinCode = "VIN12345678901234";
        Vehicle vehicle = new Vehicle();
        vehicle.setId(UUID.randomUUID());
        vehicle.setVinCode(vinCode);

        when(vehicleRepository.findFirstByVinCode(vinCode)).thenReturn(Optional.of(vehicle));

        Vehicle result = vehicleService.findFirstByVinCode(vinCode);
        assertEquals(vehicle, result);
    }

    @Test
    void shouldThrowCustomEntityNotFoundException_whenVinCodeDoesNotExist() {
        String vinCodeName = "VinCodeNotExist";
        given(vehicleRepository.findFirstByVinCode(vinCodeName)).willReturn(Optional.empty());

        assertThrows(CustomEntityNotFoundException.class, () -> vehicleService.findFirstByVinCode(vinCodeName));
    }

    @Test
    void shouldSaveNewVinCodeAndReturnIt() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(UUID.randomUUID());
        vehicle.setVinCode("VIN12345678901234");

        given(vehicleRepository.save(vehicle)).willReturn(vehicle);

        Vehicle addedVehicle = vehicleService.add(vehicle);

        assertNotNull(addedVehicle);
        verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
    void shouldUpdateVinCodeAndReturnIt() {
        UUID id = UUID.randomUUID();

        Vehicle existingVehicle = new Vehicle(
                1, "VIN12345678901234", "Make", "Model", "2022");
        existingVehicle.setId(id);

        Vehicle updatedVehicle = new Vehicle(
                1, "VIN11111111111111", "Make1", "Model", "2022");
        updatedVehicle.setId(id);

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(existingVehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(updatedVehicle);

        Vehicle result = vehicleService.update(updatedVehicle);

        assertNotNull(result);
        assertEquals(updatedVehicle, result);
    }

    @Test
    void shouldDeleteVinCode_whenDeleteById() {
        UUID id = UUID.randomUUID();
        Vehicle vinCode = new Vehicle();
        vinCode.setId(id);
        vinCode.setVinCode("VIN12345678901234");

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vinCode));

        vehicleService.deleteById(id);

        assertFalse(vehicleRepository.existsById(id));
    }

    @Test
    void shouldThrowCustomEntityNotFoundException_whenVinCodeNotExists_deleteById() {
        UUID id = UUID.randomUUID();

        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomEntityNotFoundException.class, () -> {
            vehicleService.deleteById(id);
        });

        verify(vehicleRepository, never()).delete(any());
    }
}
