package autoyard.api.controller;

import com.autoyard.project.api.controller.VehicleController;
import com.autoyard.project.api.dto.request.VehicleRequest;
import com.autoyard.project.api.dto.response.VehicleResponse;
import com.autoyard.project.domain.entity.Vehicle;
import com.autoyard.project.mapper.VehicleMapper;
import com.autoyard.project.service.impl.VehicleServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VehicleControllerIT {

    @LocalServerPort
    private int port;
    private static final String BASE_URL = "/vehicles";

    private String getBaseUrl() {
        return "http://localhost:" + port + BASE_URL;
    }

    private MockMvc mockMvc;

    @MockBean
    private VehicleServiceImpl vinCodeService;

    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private VehicleController vehicleController;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
    }

    @Test
    void shouldReturnListVehicleResponse_StatusOk_WhenFindAllVehicles() throws Exception {
        List<Vehicle> expectedVehicles = Arrays.asList(
                new Vehicle(1, "VIN1234567890", "Audi", "A4", "2018"),
                new Vehicle(2, "VIN0987654321", "BMW", "X5", "2019"),
                new Vehicle(3, "VIN4567890123", "Mercedes-Benz", "S-Class", "2020")
        );
        expectedVehicles.forEach(vc -> vc.setId(UUID.randomUUID()));

        when(vinCodeService.findAll()).thenReturn(expectedVehicles);

        List<VehicleResponse> expectedResponses = vehicleMapper.toVehicleResponseList(expectedVehicles);

        mockMvc.perform(get(getBaseUrl()))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponses)))
                .andExpect(jsonPath("$.length()").value(expectedResponses.size()));
    }

    @Test
    void shouldReturnVehicleResponse_StatusOk_WhenVehicleFindById() throws Exception {
        UUID id = UUID.randomUUID();
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);

        when(vinCodeService.findById(id)).thenReturn(vehicle);

        VehicleResponse expectedResponse = vehicleMapper.toVehicleResponse(vehicle);

        mockMvc.perform(get(getBaseUrl() + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void shouldReturnVehicleResponse_StatusOk_WhenVehicleFindFirstByVinCode() throws Exception {
        String vinCode = "VINCODE123456789";
        Vehicle vehicle = new Vehicle();
        vehicle.setVinCode(vinCode);

        when(vinCodeService.findFirstByVinCode(vinCode)).thenReturn(vehicle);

        VehicleResponse expectedResponse = vehicleMapper.toVehicleResponse(vehicle);

        mockMvc.perform(get(getBaseUrl() + "/vincode/" + vinCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vinCode").value(vehicle.getVinCode()))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void shouldReturnListVehicleResponse_StatusOk_WhenFindAllVehiclesByVinCode() throws Exception {
        String vinCode = "VINCODE123456789";

        List<Vehicle> expectedVehicles = Arrays.asList(
                new Vehicle(1, vinCode, "Make", "Model", "2022"),
                new Vehicle(2, vinCode, "Make", "Model", "2022")
        );

        when(vinCodeService.findAllByVinCode(vinCode)).thenReturn(expectedVehicles);

        List<VehicleResponse> expectedResponse = vehicleMapper.toVehicleResponseList(expectedVehicles);

        mockMvc.perform(get(getBaseUrl() + "/vincodes/" + vinCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedVehicles.size()))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void shouldReturnVehicleResponse_StatusCreated_AfterSaveVehicle() throws Exception {
        VehicleRequest vehicleRequest = new VehicleRequest();
        vehicleRequest.setVinCode("VINCODE123456789");
        vehicleRequest.setMake("Make");
        vehicleRequest.setModel("Model");
        vehicleRequest.setYear("2022");

        UUID id = UUID.randomUUID();
        Vehicle vehicleEntity = vehicleMapper.toVehicleEntity(vehicleRequest);
        vehicleEntity.setId(id);
        vehicleEntity.setCustomCode(1);

        when(vinCodeService.add(any(Vehicle.class))).thenReturn(vehicleEntity);

        VehicleResponse vehicleResponse = vehicleMapper.toVehicleResponse(vehicleEntity);

        mockMvc.perform(post(getBaseUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(vehicleRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(vehicleResponse)));

    }

    @Test
    @Transactional
    @Rollback
    void shouldReturnVehicleResponse_StatusOk_AfterUpdateVehicle() throws Exception {
        VehicleRequest vehicleRequest = new VehicleRequest();
        vehicleRequest.setVinCode("VINCODE123456789");
        vehicleRequest.setMake("Make");
        vehicleRequest.setModel("Model");
        vehicleRequest.setYear("2022");

        UUID id = UUID.randomUUID();
        Vehicle vehicleEntity = vehicleMapper.toVehicleEntity(vehicleRequest);
        vehicleEntity.setId(id);
        vehicleEntity.setCustomCode(1);

        when(vinCodeService.update(any(Vehicle.class))).thenReturn(vehicleEntity);

        mockMvc.perform(put(getBaseUrl() + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(vehicleRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(vehicleEntity)));
    }

    @Test
    void should_StatusNoContent_AfterDeleteVehicleById() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete(getBaseUrl() + "/" + id))
                .andExpect(status().isNoContent());

        verify(vinCodeService, times(1)).deleteById(id);
    }
}
