package com.autoyard.project.validation;

import com.autoyard.project.api.dto.request.CellOfYardRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;


public class CellOfYardValidator {

    private CellOfYardValidator() {
    }

    public static void validateRequest(CellOfYardRequest request) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<CellOfYardRequest>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}