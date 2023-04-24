package autoyard.validate;

import com.autoyard.project.api.dto.request.VehicleRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VehicleValidateTest {


    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldHaveNoViolations_WhenVinCodeValidateVinCodeRequest() {
        VehicleRequest vehicleRequest = new VehicleRequest();
        vehicleRequest.setVinCode("12345678901234567");
        vehicleRequest.setMake("Ford");
        vehicleRequest.setModel("Mustang");
        vehicleRequest.setYear("2022");

        Set<ConstraintViolation<VehicleRequest>> violations = validator.validate(vehicleRequest);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldHaveViolationOnName_vinCodeVO_WhenNameToLong_MoreThen17Characters() {
        VehicleRequest vehicleRequest = new VehicleRequest();
        vehicleRequest.setVinCode("123456789012345678");
        vehicleRequest.setMake("Ford");
        vehicleRequest.setModel("Mustang");
        vehicleRequest.setYear("2022");

        Set<ConstraintViolation<VehicleRequest>> violations = validator.validate(vehicleRequest);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("vinCode", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void shouldHaveViolationOnYear_vinCodeVO_WhenYearToShort() {
        VehicleRequest vehicleRequest = new VehicleRequest();
        vehicleRequest.setVinCode("12345678901234567");
        vehicleRequest.setMake("Ford");
        vehicleRequest.setModel("Mustang");
        vehicleRequest.setYear("22"); // too short, should be 4 digits or empty string

        Set<ConstraintViolation<VehicleRequest>> violations = validator.validate(vehicleRequest);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("year", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void shouldHaveNoViolationOnYearFormat_vinCodeVO_WhenYearEmptyString() {
        VehicleRequest vehicleRequest = new VehicleRequest();
        vehicleRequest.setVinCode("12345678901234567");
        vehicleRequest.setMake("Ford");
        vehicleRequest.setModel("Mustang");
        vehicleRequest.setYear(""); // should be 4 digits or empty string

        Set<ConstraintViolation<VehicleRequest>> violations = validator.validate(vehicleRequest);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldHaveViolationOnYearFormat_vinCodeVO_WhenYearHaveChar() {
        VehicleRequest vehicleRequest = new VehicleRequest();
        vehicleRequest.setVinCode("12345678901234567");
        vehicleRequest.setMake("Ford");
        vehicleRequest.setModel("Mustang");
        vehicleRequest.setYear("abc1"); // should be 4 digits or empty string

        Set<ConstraintViolation<VehicleRequest>> violations = validator.validate(vehicleRequest);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("year", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void shouldHaveViolationOnBrand_vinCodeVO_WhenBrandNull() {
        VehicleRequest vehicleRequest = new VehicleRequest();
        vehicleRequest.setVinCode("12345678901234567");
        vehicleRequest.setMake(null);
        vehicleRequest.setModel("Mustang");
        vehicleRequest.setYear("2022");

        Set<ConstraintViolation<VehicleRequest>> violations = validator.validate(vehicleRequest);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("make", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void shouldHaveViolationOnModel_vinCodeVO_WhenModelNull() {
        VehicleRequest vehicleRequest = new VehicleRequest();
        vehicleRequest.setVinCode("12345678901234567");
        vehicleRequest.setMake("brand");
        vehicleRequest.setModel(null);
        vehicleRequest.setYear("2023");

        Set<ConstraintViolation<VehicleRequest>> violations = validator.validate(vehicleRequest);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<VehicleRequest> violation = violations.iterator().next();
        assertEquals("model", violation.getPropertyPath().toString());
        // assertEquals("must not be null", violation.getMessage());   // Actual   :не должно равняться null
    }
}
