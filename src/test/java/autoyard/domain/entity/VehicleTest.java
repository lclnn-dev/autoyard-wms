package autoyard.domain.entity;

import com.autoyard.project.domain.entity.Vehicle;
import com.autoyard.project.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
class VehicleTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle();
        vehicle.setVinCode("VINCODE123456789");
        vehicle.setMake("Make");
        vehicle.setModel("Model");
        vehicle.setYear("2022");
    }

    @Test
    void shouldReturnNotNull_afterSaveVinCode() {
        Vehicle savedVehicles = vehicleRepository.save(vehicle);
        assertThat(savedVehicles).isNotNull();
        assertThat(savedVehicles.getId()).isNotNull();
    }

    @Test
    void shouldReturnFoundVinCodeById_afterSaveVinCode() {
        Vehicle savedVehicles = vehicleRepository.save(vehicle);

        Vehicle foundVehicles = vehicleRepository.findById(savedVehicles.getId()).get();

        assertThat(foundVehicles).isNotNull();
        assertThat(foundVehicles.getVinCode()).isEqualTo(vehicle.getVinCode());
        assertThat(foundVehicles.getMake()).isEqualTo(vehicle.getMake());
        assertThat(foundVehicles.getModel()).isEqualTo(vehicle.getModel());
        assertThat(foundVehicles.getYear()).isEqualTo(vehicle.getYear());
    }
}
