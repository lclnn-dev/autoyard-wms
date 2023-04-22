package com.autoyard.project.api.exception;


import com.autoyard.project.api.exception.error.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        errorResponse.setDebugMessage(ex.getMessage());

        return buildErrorResponse(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getPropertyName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        errorResponse.setDebugMessage(ex.getMessage());

        return buildErrorResponse(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage("Validation error. Check 'errors' field for details.");
        errorResponse.addValidationErrors(ex.getBindingResult().getFieldErrors());
        errorResponse.addValidationError(ex.getBindingResult().getGlobalErrors());

        return buildErrorResponse(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex);
        errorResponse.setMessage(builder.substring(0, builder.length() - 2));

        return buildErrorResponse(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex);
        errorResponse.setMessage(ex.getParameterName() + " parameter is missing");

        return buildErrorResponse(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex);
        errorResponse.setMessage("Malformed JSON request");
        errorResponse.setDebugMessage(String.format("{%s} to {%s}. %s",
                servletWebRequest.getHttpMethod(),
                servletWebRequest.getRequest().getServletPath(),
                ex.getMessage()));

        return buildErrorResponse(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        errorResponse.setMessage("Error writing JSON output");

        return buildErrorResponse(errorResponse);
    }

    @ExceptionHandler(CustomEntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(CustomEntityNotFoundException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND);
        errorResponse.setMessage(ex.getMessage());

        return buildErrorResponse(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {

        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildErrorResponse(new ApiErrorResponse(HttpStatus.CONFLICT, "Database error", ex.getCause()));
        }

        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponse.setMessage("Violation of an integrity constraint. " + ex.getMostSpecificCause().getMessage());
        errorResponse.setDebugMessage(String.format("%s. %s. %s", ex, ex.getCause(), ex.getMostSpecificCause()));

        return buildErrorResponse(errorResponse);
    }

    @ExceptionHandler({ConstraintViolationException.class, TransactionSystemException.class})
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {

        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage("Validation failed. Check 'errors' field for details.");
        errorResponse.addValidationErrors(ex.getConstraintViolations());

        return buildErrorResponse(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllUncaughtCustomException(Exception ex) {

        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        errorResponse.setMessage((String.format("%s : %s", ex.getClass().getSimpleName(), errorResponse.getMessage())));

        return buildErrorResponse(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.valueOf(statusCode.value()), ex);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDebugMessage(ex.toString());

        return buildErrorResponse(errorResponse);
    }

    private ResponseEntity<Object> buildErrorResponse(ApiErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
