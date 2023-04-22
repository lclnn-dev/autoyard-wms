package com.autoyard.project.api.exception.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiValidatorErrorResponse implements ApiSubErrorResponse {

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidatorErrorResponse(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
