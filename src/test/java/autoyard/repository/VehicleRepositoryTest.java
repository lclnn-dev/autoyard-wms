package autoyard.repository;

import com.autoyard.project.domain.entity.Vehicle;
import com.autoyard.project.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
class VehicleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    void shouldReturnVinCode_WhenFindByName() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVinCode("VINCODE123456789");
        vehicle.setMake("Make");
        vehicle.setModel("Model");
        vehicle.setYear("2022");
        entityManager.persist(vehicle);
        entityManager.flush();

        List<Vehicle> found = vehicleRepository.findAllByVinCode(vehicle.getVinCode());

        assertThat(found.isEmpty()).isFalse();
        assertThat(found.get(0).getVinCode()).isEqualTo(vehicle.getVinCode());
    }

    @Test
    void shouldReturnEmpty_WhenFindByName() {
        String nonExistingName = "nonExistingVinCode";
        List<Vehicle> found = vehicleRepository.findAllByVinCode(nonExistingName);

        assertThat(found.isEmpty()).isTrue();
    }
}
